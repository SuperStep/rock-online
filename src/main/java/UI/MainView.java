package UI;

import Models.Track;
import backend.Memcached_client;
import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.util.Timer;

@MenuCaption("В эфире") 
@MenuIcon(VaadinIcons.HOME)
public class MainView extends VerticalLayout implements View {
 
    private Track track;
    private final ResponsiveLayout responsiveLayout = new ResponsiveLayout();
    private final ResponsiveRow rootRow = new ResponsiveRow();
    private final Player player = new Player();
    private final Content content = new Content();
    
    Timer timer = new Timer(true);

    private final Memcached_client cache = new Memcached_client();
    
    Runnable updateTask = () -> {
        CheckTrack();
    };       
    
    public MainView(){
        addComponent(responsiveLayout);
        responsiveLayout.addComponent(player);
    }
    
    @Override
    public void enter(ViewChangeEvent event) { 
    }
    
    public void CheckTrack(){
        try {
                cache.Connect();  
                Track newtrack = cache.GetCurrentTrack();
                if(track == null){
                    UpdateTrack();
                }else if(!track.name.equals(newtrack.name)){
                    UpdateTrack();
                }
            } catch (Exception ex) {
               showError(ex);
            }             
    }
    
    private void UpdateTrack(){
        try {
            track = cache.GetCurrentTrack();
            player.artistInfo.setCaption(cache.GetTitle());
            player.setCover(track.coverUrl);
            player.showTrack(track.artist + " - " + track.name);
            content.SetContent("ТУР", track.events);  
        } catch (Exception ex) {
            showError(ex);
        }      
    }
    

    
    private void showError(Exception e){
        Notification errorNotif = new Notification("ERROR:", e.toString());
        errorNotif.setPosition(Position.BOTTOM_RIGHT);
        errorNotif.setDelayMsec(100000);
        errorNotif.show(Page.getCurrent());       
    }
    
    
}



