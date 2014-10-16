package com.xuewei8910.neame_client.web;

import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xuewei8910.neame_client.models.Me;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import retrofit.client.Header;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.FormUrlEncodedTypedOutput;

/**
 * Created by Wei on 2014/10/14.
 */
public class SecuredClient extends OkClient {
    private String clientId;
    private String clientSecret;
    private String accessToken;
    private String authEndpoint;
    @Override
    public Response execute(Request request) throws IOException {
        if (accessToken != null) {
            request.getHeaders().add(new Header("Authorization", "Bear " + accessToken));
        }else {
            accessToken = getAccessToken();
        }
        Response response = super.execute(request);

        if (response.getStatus() == 401){
            accessToken = getAccessToken();
            request.getHeaders().add(new Header("Authorization", "Bear " + accessToken));
            response = super.execute(request);
        }
        return response;
    }

    private String getAccessToken() throws IOException{
        FormUrlEncodedTypedOutput body = new FormUrlEncodedTypedOutput();
        body.addField("username", Me.getInstance().getUsername());
        body.addField("password",Me.getInstance().getPassword());
        body.addField("grand_type","password");
        body.addField("client_id",clientId);
        body.addField("client_secret",clientSecret);

        String base64auth = BaseEncoding.base64().encode(
                new String(clientId+":"+clientSecret).getBytes());
        List<Header> headers = new ArrayList<Header>();
        headers.add(new Header("Authorization","Basic "+base64auth));
        Request req = new Request("POST",authEndpoint,headers,body);
        Response res = super.execute(req);

        if (res.getStatus()<200 || res.getStatus()>299){
            throw new SecuredException(SecuredException.AUTHENTICATE_FAILED);
        }else {
            String s = ByteSource.wrap(ByteStreams.toByteArray(res.getBody().in()))
                    .asCharSource(Charset.forName("UTF-8")).read();
            return new Gson().fromJson(s, JsonObject.class).get("access_token").getAsString();
        }

    }

    public SecuredClient setClientId(String clientId){
        this.clientId = clientId;
        return this;
    }

    public SecuredClient setClientSecret(String clientSecret){
        this.clientSecret = clientSecret;
        return this;
    }

    public SecuredClient setAuthEndpoint(String authEndpoint){
        this.authEndpoint = authEndpoint;
        return this;
    }
}
