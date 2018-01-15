
package UI;

import Stream.SoundPlayer;
import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class Player extends ResponsiveColumn{
    
    //Panel streamPanel = new Panel();
    VerticalLayout streamPanelContent = new VerticalLayout();
    Image coverImg = new Image();
    Label artistInfo = new Label();
    Button btnPlayStop = new Button();
    
    SoundPlayer player = new SoundPlayer();
   
    Player(){
        
        super(12, 12,12, 12);
        
        //setAlignment(ResponsiveColumn.ColumnComponentAlignment);
        setContent(streamPanelContent);
        streamPanelContent.setSizeUndefined();
//        streamPanel.setCaption("В эфире");
//        streamPanel.setContent(streamPanelContent);
        streamPanelContent.addComponents(coverImg, artistInfo);
        streamPanelContent.setComponentAlignment(coverImg, Alignment.TOP_CENTER);
        coverImg.setSizeFull();
        streamPanelContent.setComponentAlignment(artistInfo, Alignment.MIDDLE_CENTER);
    
        InitPlayer();
        //InitAlternative();
        
        setDefaultImage();
    }
    
    private void InitPlayer(){    
        
        streamPanelContent.addComponent(btnPlayStop);
        streamPanelContent.setComponentAlignment(btnPlayStop, Alignment.BOTTOM_CENTER);   
        btnPlayStop.setSizeFull();        
        btnPlayStop.setIcon(VaadinIcons.PLAY);
        
        btnPlayStop.addClickListener(event ->{
            playerAction();
        });
    }
    
    private void playerAction(){       
        if (player.isPlaying){
            btnPlayStop.setIcon(VaadinIcons.STOP);
            player.stop();    
        }else{
        btnPlayStop.setIcon(VaadinIcons.STOP);
        player.play();  
        }
    }
    
    public void setCover(String thumbUrl){
        coverImg.setSource(new ExternalResource(thumbUrl));
        coverImg.setSizeFull();
    }
    
    private void setDefaultImage() {
        //coverImg.setSource(new ExternalResource("https://media.giphy.com/media/7eygrJEkHFHEY/giphy.gif"));
        //coverImg.setIcon(new ExternalResource("https://media.giphy.com/media/7eygrJEkHFHEY/giphy.gif"));    
    }
    
    private void InitAlternative(){
        String playerHTML = "<object id=\"audioplayer2003\" type=\"application/x-shockwave-flash\" data=\"http://rock-online.ru/uppod.swf\" width=\"300\" height=\"70\"><param name=\"bgcolor\" value=\"#ffffff\"><param name=\"allowScriptAccess\" value=\"always\"><param name=\"movie\" value=\"http://rock-online.ru/uppod.swf\"><param name=\"flashvars\" value=\"comment=Новый&amp;st=http://rock-online.ru/styles/audio34-1103.txt&amp;file=http://skycast.su:2007/rock-online\"></object>";
        Label HTMLText = new Label(playerHTML);
        HTMLText.setContentMode(ContentMode.HTML);
        streamPanelContent.addComponent(HTMLText);
    }
    
    public void showTrack(String info){      
        Notification trackNotification = new Notification("Сейчас играет: "+info);
        trackNotification.setPosition(Position.BOTTOM_CENTER);
        trackNotification.setDelayMsec(20000);
        trackNotification.setIcon(VaadinIcons.PLAY_CIRCLE);
        trackNotification.show(Page.getCurrent()); 
    }
}
