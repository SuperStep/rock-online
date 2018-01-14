
package UI;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.vaadin.ui.MenuBar;


public class MainMenu extends ResponsiveRow  {
    
    MenuBar barmenu = new MenuBar();
    ResponsiveColumn menu1 = new ResponsiveColumn(0,0,12,12);
    ResponsiveColumn menu2 = new ResponsiveColumn(0,0,12,12);
    ResponsiveColumn menu3 = new ResponsiveColumn(0,0,12,12);
    
    ResponsiveColumn menuCol = new ResponsiveColumn(12,12,12,12);
     
     
    
     MainMenu(){
         addColumn(menuCol);
         menuCol.setAlignment(ResponsiveColumn.ColumnComponentAlignment.CENTER);
         menuCol.setContent(barmenu);
         
         InitMenu();
         //WIDE MENU
//        menu.addColumn(menu1);
//        menu.addColumn(menu2);
//        menu.addColumn(menu3);     
//        menu1.setComponent(new Button("Афиша"));
//        menu2.setComponent(new Button("Текущий исполнитель"));
//        menu3.setComponent(new Button("Наша команда"));
//        menu1.setVisibilityRules(false, false, true, true);
//        menu2.setVisibilityRules(false, false, true, true);
//        menu3.setVisibilityRules(false, false, true, true);   
//        menu1.setSizeFull();
//        menu2.setSizeFull();
//        menu3.setSizeFull();
//        
//        //THIN MENU
//        menu.addColumn(thinMenu);
//        thinMenu.setVisibilityRules(true, true, false, false);
//        thinMenu.setContent(barmenu);
//        barmenu.setSizeFull();
//
//        menu.setHorizontalSpacing(true);
//        menu.setVerticalSpacing(true);
//        menu.setMargin(true);
         
         
     }
     
    private void InitMenu(){
         
        MenuBar.Command mycommand = new MenuBar.Command() {
        public void menuSelected(MenuBar.MenuItem selectedItem) {
                //showTrack("Selected " +selectedItem.getText() +" from menu.");
            }
        };       
        
        MenuBar.MenuItem m1 = barmenu.addItem("Афиша", null, mycommand);
        MenuBar.MenuItem m2 = barmenu.addItem("Текущий исполнитель", null, mycommand);
        MenuBar.MenuItem m3 = barmenu.addItem("Наша команда", null, mycommand);


    }
}
