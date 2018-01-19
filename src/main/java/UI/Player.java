
package UI;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

public class Player extends ResponsiveColumn{
    
    ResponsiveRow playerLayout = new ResponsiveRow();
    Image coverImg = new Image();
    Label artistInfo = new Label();
    Button btnPlayStop = new Button();
    Label PlayerHTMLHolder;
    Boolean isPlaying;
   
    Player(){
        
        super(12, 12, 3, 3);
            
        setContent(playerLayout);
        playerLayout.addComponent(coverImg);
        playerLayout.addComponent(artistInfo);  
        playerLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        
        coverImg.addClickListener(event ->{
            playerAction();
        });
   
        InitPlayer();
        
        
       
    }
    
    public void setCover(String thumbUrl){
        coverImg.setSource(new ExternalResource(thumbUrl));
        coverImg.setSizeFull();
    }
    
    private void playerAction(){       
        if (isPlaying){
            btnPlayStop.setIcon(VaadinIcons.PLAY);
            Pause();    
        }else{
            btnPlayStop.setIcon(VaadinIcons.STOP);
            Play();  
        }
    }
    
    
    private void InitPlayer(){
        
        isPlaying = false;
        btnPlayStop.setIcon(VaadinIcons.PLAY);
        
        String playerHTML =
        "<audio id=\"player\" src=\"http://skycast.su:2007/rock-online\"></audio>\n";
        PlayerHTMLHolder = new Label(playerHTML);
        PlayerHTMLHolder.setContentMode(ContentMode.HTML);
        playerLayout.addComponent(PlayerHTMLHolder);
    }
    
    private void Play(){
        String script = "document.getElementById('player').play();";
        JavaScript.getCurrent().execute(script);
        isPlaying = true;
    }
    
    private void Pause(){
        String script = "document.getElementById('player').pause();";
        JavaScript.getCurrent().execute(script);
        isPlaying = false;
    }   
    public void showTrack(String info){      
        Notification trackNotification = new Notification("Сейчас играет: "+info);
        trackNotification.setPosition(Position.BOTTOM_CENTER);
        trackNotification.setDelayMsec(20000);
        trackNotification.setIcon(VaadinIcons.PLAY_CIRCLE);
        trackNotification.show(Page.getCurrent()); 
    }
}
