
package UI;

import Models.ArtistEvent;
import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import java.util.ArrayList;

public class Content extends ResponsiveColumn {
    
    ResponsiveRow titleRow = new ResponsiveRow();
    ResponsiveColumn titleCol = new ResponsiveColumn(3);//3
    ResponsiveLayout mainContentLayout = new ResponsiveLayout();
    
    Label contentTitle = new Label();
    ResponsiveRow contentRow = new ResponsiveRow(); 

    Content(){
    	
    	defaultContent();
        setComponent(mainContentLayout);
   
    }
    
    public void defaultContent(){
        contentRow.setHorizontalSpacing(true);
        contentRow.setVerticalSpacing(true);
        contentRow.setMargin(true);       
    }
    
    
    public void SetContent(String title, Object content) {
    	
    	contentRow.removeAllComponents();
    	
        if(content instanceof ArrayList){
            
            EventsPanel eventsPanel = new EventsPanel();
            
            if(((ArrayList) content).isEmpty())
            {
                eventsPanel.showNoData();
            }else{
                
                eventsPanel.SetEvents((ArrayList<ArtistEvent>)content);
            }

            contentRow.addColumn(eventsPanel); 

        }else if(content instanceof ContentPanel){
            
            for (int x = 0; x < 10; x++) {
               contentRow.addColumn((ContentPanel)content);  
            }  
            
        } 
        
        mainContentLayout.addRow(contentRow); 
        contentRow.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);       	
    	
    	
    }
    
    
}
