package UI;

import Models.Track;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import backend.Memcached_client;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.annotation.WebServlet;


@Theme("mytheme")
public class MyUI extends UI {
      
    private Track track;
    private ResponsiveLayout responsiveLayout = new ResponsiveLayout();
    private ResponsiveRow rootRow = new ResponsiveRow();
    
    private MainMenu mainMenu = new MainMenu();
    private SideMenu sideMenu = new SideMenu();
    private Content content = new Content();
    
    Timer updateTimer = new Timer(true);
    
    private Player player;

    private Memcached_client cache = new Memcached_client();
    
    Runnable updateTask = () -> {
        UpdateCurrentTrack();
    };    
    
      
    @Override
    protected void init(VaadinRequest vaadinRequest) {
       
        InitLayout();     
        updateTimer.scheduleAtFixedRate(new UpdateTask(), 0, 10000);

    }
    
    private void InitLayout(){
      
        try{
            setSizeFull();
            setContent(responsiveLayout);
            responsiveLayout.setSizeFull();
            responsiveLayout.setScrollable(true);
            
            responsiveLayout.addRow(mainMenu);
            responsiveLayout.addRow(rootRow);
            
            rootRow.setHeight("100%");  
            rootRow.addColumn(sideMenu.withDisplayRules(12, 12,3, 3));
            rootRow.addColumn(content.withDisplayRules(12,12,9,9));
            
            player = sideMenu.getPlayer();
            
            // The component itself
            PopupView popup = new PopupView("Pop it up", new LoginForm());
            
            
            Button button = new Button("Show details", click ->
            popup.setPopupVisible(true));
            
            
            responsiveLayout.addComponents(button, popup);
            
        }catch(Exception ex){
            showError(ex);
        }
    
    }       
    
    private void UpdateCurrentTrack(){
       
        try {          
            cache.Connect();  
            Track newtrack = cache.GetCurrentTrack();
            if(newtrack != track) {
                track = cache.GetCurrentTrack();
                player.artistInfo.setCaption(cache.GetTitle()); 
                player.setCover(track.coverUrl);
                player.showTrack(track.artist + " - " + track.name);
                content.SetContent("ТУР", track.events);             	
            }         
        } catch (Exception ex) {
            showError(ex);
        }        
    }
    
    private void showError(Exception e){
        Notification errorNotif = new Notification("ERROR", e.getMessage());
        errorNotif.setPosition(Position.BOTTOM_RIGHT);
        errorNotif.setDelayMsec(10000);
        errorNotif.show(Page.getCurrent());       
    }
    
    class UpdateTask extends TimerTask {

            @Override
            public void run() {
            final MyUI ui = MyUI.this;
            ui.access(() -> {
                UpdateCurrentTrack();
            });
        }
    }
    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}

