
package backend;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.account.UserSettings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


public class VKApi {
  
    TransportClient transportClient;
    VkApiClient vk;
    public UserActor actor;
    
    public String userCode;
    
    public VKApi(){
        transportClient = HttpTransportClient.getInstance(); 
        vk = new VkApiClient(transportClient);    
    }  
    
    
    public void Auth() throws Exception{
        UserAuthResponse authResponse = vk.oauth() 
        .userAuthorizationCodeFlow(6339048, "6G47ehk7b0kkRNnSNA6Y", "https://ro-ui.herokuapp.com/", userCode)
        .execute();      
        actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken()); 
    }
    
//    public String getSessionToken() throws Exception{
//        
//        URL obj = new URL(getCodeUrl());
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//        // optional default is GET
//        con.setRequestMethod("GET");
//
//        //add request header
//        con.setRequestProperty("https://oauth.vk.com/blank.html", USER_AGENT);
//
//        
//        
//    }
    
    public String getCodeUrl() throws Exception{
        String urlString = "https://oauth.vk.com/authorize?"
                + "client_id=6339048"
                + "&display=mobile"
                + "&redirect_uri=https://ro-ui.herokuapp.com/"
                + "&scope=friends"
                + "&response_type=code"
                + "&v=5.71";
       
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setInstanceFollowRedirects(true);
        HttpURLConnection.setFollowRedirects(true);
        con.connect();
        con.getURL();
        
        return null;
        
    }
    
    public String getActorInfo() throws Exception{
        
        UserSettings userSettings = vk.account().getProfileInfo(actor).execute();
        return userSettings.getFirstName();
        
    }
}
