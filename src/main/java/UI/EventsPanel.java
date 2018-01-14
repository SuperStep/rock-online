
package UI;

import Models.ArtistEvent;
import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Panel;
import java.util.ArrayList;


public class EventsPanel extends ResponsiveColumn{
    
    Panel panel = new Panel();
    
    Grid<ArtistEvent> eventsGrid = new Grid();
    
    EventsPanel(){
        super(12,12,12,12);
        setComponent(panel);
        panel.setCaption("Концерты исполнителя");
    }
    
    
    public void SetEvents(ArrayList<ArtistEvent> events){
        
        eventsGrid.setItems(events);
        eventsGrid.addColumn(ArtistEvent::getName).setCaption("Название");
        eventsGrid.addColumn(ArtistEvent::getCountry).setCaption("Страна");
        eventsGrid.addColumn(ArtistEvent::getCity).setCaption("Город");
        eventsGrid.addColumn(ArtistEvent::getDatetime).setCaption("Дата");
        
        panel.setContent(eventsGrid);
        eventsGrid.setSizeFull();
        
    }
    
    public void showNoData(){
        setContent(new Panel("Нет данных"));
    }
    
    
}
