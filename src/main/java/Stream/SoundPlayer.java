
package Stream;

import com.vaadin.ui.JavaScript;
import java.net.MalformedURLException;
import java.net.URL;

public class SoundPlayer {
    
   public boolean isPlaying;
   URL url;
    
    public SoundPlayer (){
       try {
           url = new URL("http://skycast.su:2007/rock-online");
       } catch (MalformedURLException ex) {
       }
    }

    public void play(){
        
        
        
        
        String script = "if (document.getElementById('" + url.hashCode() + "') == null) {" +
                "var audio = document.createElement('audio');" +
                "audio.class = 'hidden';" +
                "audio.preload = 'auto';" +
                "audio.id = '" + url.hashCode() + "';" +
                "audio.src = '" + url.toString() + "';" +
                "document.body.appendChild(audio);" +
                "}" +
                "document.getElementById('" + url.hashCode() + "').play();";
        JavaScript.getCurrent().execute(script);
        isPlaying = true;
    }
    
    
    
    
    public void stop() {
        JavaScript.getCurrent().remove();
        isPlaying = false;
    }

    public void play(String httpskycastsu2007rockonline) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
