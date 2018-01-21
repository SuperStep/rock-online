
package UI;

import Models.ArtistEvent;
import com.vaadin.navigator.View;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;

public class EventsView extends VerticalLayout implements View {
    
    Label contentTitle = new Label();
    Grid<ArtistEvent> eventsGrid = new Grid();
    
    EventsView(){

    }
    
    public void SetContent(String title, ArrayList<ArtistEvent> events) {
    	
    	removeAllComponents();
        
        eventsGrid.setItems(events);
        eventsGrid.addColumn(ArtistEvent::getName).setCaption("Название");
        eventsGrid.addColumn(ArtistEvent::getCountry).setCaption("Страна");
        eventsGrid.addColumn(ArtistEvent::getCity).setCaption("Город");
        eventsGrid.addColumn(ArtistEvent::getDatetime).setCaption("Дата");
        
        addComponent(eventsGrid);
        eventsGrid.setSizeFull();

    }
    
}
