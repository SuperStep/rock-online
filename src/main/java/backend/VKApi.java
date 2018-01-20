
package backend;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.account.UserSettings;

public class VKApi {
  
    TransportClient transportClient;
    VkApiClient vk;
    UserActor actor;
    
    public String url = "https://oauth.vk.com/authorize?"
                + "client_id=6339048"
                + "&display=popup"
                + "&redirect_uri=https://ro-ui.herokuapp.com/"
                + "&scope=friends"
                + "&response_type=code"
                + "&v=5.71"; 
    
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
    
    public UserSettings getUserInfo() throws Exception{      
        return vk.account().getProfileInfo(actor).execute();           
    }
    

}
