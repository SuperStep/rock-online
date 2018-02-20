
package UI;

import Models.ArtistEvent;
import backend.Memcached_client;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;

public class EventsView extends VerticalLayout implements View {
    
    private final Memcached_client cache = new Memcached_client();
    
    Label contentTitle = new Label();
    Grid<ArtistEvent> eventsGrid = new Grid();
    
    EventsView(){
        addComponents(contentTitle, eventsGrid);
        eventsGrid.setSizeFull();
    }
    
    public void SetContent(String title, ArrayList<ArtistEvent> events) {
    	
        contentTitle.setCaption(title);

        eventsGrid.setItems(events);
        eventsGrid.addColumn(ArtistEvent::getName).setCaption("Название");
        eventsGrid.addColumn(ArtistEvent::getCountry).setCaption("Страна");
        eventsGrid.addColumn(ArtistEvent::getCity).setCaption("Город");
        eventsGrid.addColumn(ArtistEvent::getDatetime).setCaption("Дата");

    }
    
    
    public void Update(){
        try {
            cache.Connect();
            SetContent("ТУР", cache.getEvents());  
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
