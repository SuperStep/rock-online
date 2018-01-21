package backend;

import Models.ArtistEvent;
import Models.Track;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.utils.AddrUtil;
import ru.blizzed.discogsdb.model.release.Release;

public class Memcached_client {
    
    MemcachedClient client;
    Gson gson = new Gson();  
    
    public Memcached_client() {
                 
    }
    
    public void Connect(){
        
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(
					AddrUtil.getAddresses("memcached-16414.c1.us-west-2-2.ec2.cloud.redislabs.com:16414"));
	// Must use binary protocol                            
	builder.setCommandFactory(new BinaryCommandFactory());
        try {
            client = builder.build();
        } catch (IOException ex) {
            
        }       
    }
    
    public void Disconnect(){
        if(client!=null){
           //client.shutdown();
           client = null;
        }
    }

    public Release getRelease() throws Exception{   
        return gson.fromJson((String)client.get("current_release"), Release.class);
        //track.events = (ArrayList<ArtistEvent>)client.get("currentTrackEvents");
    }
    
    public ArrayList<ArtistEvent> getEvents() throws Exception{
        return (ArrayList<ArtistEvent>)client.get("currentTrackEvents")  ;   
    } 
  

}
  