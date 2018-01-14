
package UI;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.vaadin.ui.Panel;

public class ContentPanel extends ResponsiveColumn{

    ContentPanel(){
        super(12, 10, 5, 3);
        setComponent(new Panel("Contrary to popular belief, "
                    + "Lorem Ipsum is not simply random text. It has roots "
                    + "in a piece of classical Latin literature from 45 BC, "
                    + "making it over 2000 years old. Richard McClintock, a "
                    + "Latin professor at Hampden-Sydney College in Virginia,"
                    + " looked up one of the more obscure Latin words, consectetur"));        
    }
    
    
}
