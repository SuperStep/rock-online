package backend;

import Models.ArtistEvent;
import Models.Track;
import java.io.IOException;
import java.util.ArrayList;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class Memcached_client {
    
    MemcachedClient client;
    
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

    public void Save(Object data) throws Exception{
        client.set("data", 0, data);
    }
    
    public Object Load() throws Exception{
        return client.get("data");
    }   
    
    public void SaveTrack(Track track) throws Exception{      
        client.set("currentTrack", 0, track); 
        client.set("currentTrackEvents", 0, track.events); 
    }
    
 
    public Track GetCurrentTrack() throws Exception{     
        Track track = (Track)client.get("currentTrack"); 
        track.events = (ArrayList<ArtistEvent>)client.get("currentTrackEvents");
        return track;
    }
    
    public void SaveTitle(String title) throws Exception{
        client.set("currentTitle", 0, title);      
    } 
  
    public String GetTitle() throws Exception{
        return (String)client.get("currentTitle");
    }

}
  