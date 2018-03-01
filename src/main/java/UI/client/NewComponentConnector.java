package UI.client;

import UI.NewComponent;
import com.google.gwt.user.client.ui.Label;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

@Connect(NewComponent.class)
public class NewComponentConnector extends AbstractComponentConnector {
    
    public NewComponentConnector() {
    }
    
    @Override
    public Label getWidget() {
        return (Label) super.getWidget();
    }
}
