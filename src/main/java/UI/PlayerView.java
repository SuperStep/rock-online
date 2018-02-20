package UI;

import backend.Memcached_client;
import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import ru.blizzed.discogsdb.model.release.Release;

@MenuCaption("В эфире") 
@MenuIcon(VaadinIcons.HOME)
public class PlayerView extends VerticalLayout implements View {
 
    private final Memcached_client cache = new Memcached_client();
    
    private Release release;
    private final ResponsiveLayout responsiveLayout = new ResponsiveLayout();
    private final ResponsiveRow rootRow = new ResponsiveRow();
    Image coverImg = new Image();
    Label artistInfo = new Label();

    public PlayerView(){
        addComponent(responsiveLayout);
        responsiveLayout.addRow(rootRow).withAlignment(Alignment.TOP_CENTER);
        rootRow.addComponent(coverImg);
        rootRow.addComponent(artistInfo);

    }
    
    public Image getCover(){
        return coverImg;
    }
    
    public void CheckRelease(){
        try {
            cache.Connect();  
            Release newRelease= cache.getRelease();
            if(release == null){
                setRelease(cache.getRelease()); 
            }else if(release.getId()!= newRelease.getId()){
                setRelease(cache.getRelease()); 
            }
        } catch (Exception ex) {
           showError(ex);
        }             
    }

    public void setRelease(Release _release) throws Exception{
        
        this.release = _release;
        if(release != null){
            String titleText = release.getTitle() + " - " + release.getArtists().get(0).getName() + " (" + release.getYear() + ")";
            //COVER IMAGE
            if(!release.getImages().isEmpty()){
                setCover(release.getImages().get(0).getUri());
            }else{
                setCover(release.getThumb());
            }
            artistInfo.setCaption(titleText);
            //TITLE
            showTrack(titleText);
        }else{
            showTrack("Track is null...");
        }
    }
    
    public void setCover(String thumbUrl){
        coverImg.setSource(new ExternalResource(thumbUrl));
        coverImg.setSizeFull();
    }
    
    private void showError(Exception e){
        Notification errorNotif = new Notification("ERROR:", e.toString());
        errorNotif.setPosition(Position.BOTTOM_RIGHT);
        errorNotif.setDelayMsec(100000);
        errorNotif.show(Page.getCurrent());       
    }
    
    public void showTrack(String info){      
        Notification trackNotification = new Notification("Сейчас играет: "+info);
        trackNotification.setPosition(Position.BOTTOM_CENTER);
        trackNotification.setDelayMsec(20000);
        trackNotification.setIcon(VaadinIcons.PLAY_CIRCLE);
        trackNotification.show(Page.getCurrent()); 
    }
    
}



