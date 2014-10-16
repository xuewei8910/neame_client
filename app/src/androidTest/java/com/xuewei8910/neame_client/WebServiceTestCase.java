package com.xuewei8910.neame_client;

import com.xuewei8910.neame_client.models.Event;
import com.xuewei8910.neame_client.models.User;
import com.xuewei8910.neame_client.web.SecuredClient;
import com.xuewei8910.neame_client.web.SecuredException;
import com.xuewei8910.neame_client.web.WebServiceApi;

import junit.framework.TestCase;

import retrofit.RestAdapter;

/**
 * Created by Wei on 2014/10/15.
 */
public class WebServiceTestCase extends TestCase {
    private final String clientId = "1111111";
    private final String clientSecret = "2222222";
    private final String testEndpoint = "http://www.xuewei8910.com";
    private final String authEndpoint = "https://www.xuewei8910.com/oauth";

    WebServiceApi webService = new RestAdapter.Builder().setClient(new SecuredClient()
            .setClientId(clientId).setClientSecret(clientSecret).setAuthEndpoint(authEndpoint))
            .setEndpoint(testEndpoint)
            .build().create(WebServiceApi.class);

    private User testUser = new User("test");
    private Event testEvent = new Event(testUser,"test",0,0);

    public void testAddEventList(){
        try {
            Event e = webService.addEvent(testEvent);
            assertNotNull(e);
            assertTrue(e.getId() > 0);
            assertEquals(testEvent.getAuthor().getUsername(), e.getAuthor().getUsername());
            assertEquals(testEvent.getContent(), e.getContent());
        }catch (SecuredException e){
            assertEquals(e.getErrorCode(),SecuredException.AUTHENTICATE_FAILED);
        }
    }

}
