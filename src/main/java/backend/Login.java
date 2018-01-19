
package backend;




import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.queries.account.AccountGetInfoQuery;


public class Login {
  
    TransportClient transportClient;
    VkApiClient vk;
    
    public String url = "https://oauth.vk.com/authorize?"
                + "client_id=6339048"
                + "&display=popup"
                + "&redirect_uri=https://ro-ui.herokuapp.com/"
                + "&scope=friends"
                + "&response_type=code"
                + "&v=5.71"; 
    
    public String userCode;
    
    public Login(String code){
        


    }  
    
    
    public void Auth(String code) throws Exception{
        transportClient = HttpTransportClient.getInstance(); 
        vk = new VkApiClient(transportClient);    
        UserAuthResponse authResponse = vk.oauth() 
        .userAuthorizationCodeFlow(6339048, "6G47ehk7b0kkRNnSNA6Y", "https://ro-ui.herokuapp.com", code) 
        .execute(); 

        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken()); 
        
        AccountGetInfoQuery getResponse = vk.account().getInfo(actor); 
    }
    

}
