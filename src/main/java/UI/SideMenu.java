
package UI;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveRow;

public class SideMenu extends ResponsiveColumn {
    
    
    Player player = new Player();
    ResponsiveRow menu = new ResponsiveRow();
    
    SideMenu (){
        
        setComponent(menu);
        
        menu.addColumn(player);
        menu.setHorizontalSpacing(true);
        menu.setVerticalSpacing(true);
        menu.setMargin(true);
    }
    

        
    
    public Player getPlayer(){
        return player;
    }
}
