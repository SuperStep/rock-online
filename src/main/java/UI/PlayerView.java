package UI;

import backend.Memcached_client;
import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.util.Timer;
import ru.blizzed.discogsdb.model.release.Release;

@MenuCaption("В эфире") 
@MenuIcon(VaadinIcons.HOME)
public class MainView extends VerticalLayout implements View {
 
    private Release release;
    private final ResponsiveLayout responsiveLayout = new ResponsiveLayout();
    private final ResponsiveRow rootRow = new ResponsiveRow();
    private final Player player = new Player();

    public MainView(){
        addComponent(responsiveLayout);
        responsiveLayout.addRow(player).withAlignment(Alignment.TOP_CENTER);
    }
    
    public void CheckRelease(Memcached_client cache){
        try {
            cache.Connect();  
            Release newRelease= cache.getRelease();
            if(release == null){
                player.setRelease(cache.getRelease()); 
            }else if(release.getId()!= newRelease.getId()){
                player.setRelease(cache.getRelease()); 
            }
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



