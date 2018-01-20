package UI;

import backend.Login;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.github.appreciated.app.layout.behaviour.AppLayout;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import static com.github.appreciated.app.layout.builder.AppLayoutBuilder.Position.HEADER;
import com.github.appreciated.app.layout.builder.design.AppBarDesign;
import com.github.appreciated.app.layout.builder.entities.DefaultBadgeHolder;
import com.github.appreciated.app.layout.component.MenuHeader;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Viewport;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;


@Push
@Viewport("width=device-width,initial-scale=1.0,user-scalable=no")
@Theme("mytheme")
public class MyUI extends UI {

    MainView mainView = new MainView();
    
    String userCode;
    
    Login vkLogin;
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {

        DefaultBadgeHolder holder = new DefaultBadgeHolder(); 
        holder.increase();   
        //Predef VIEW
        
     
          AppLayout layout = AppLayoutBuilder.get(Behaviour.LEFT_RESPONSIVE_OVERLAY_NO_APP_BAR)
                .withTitle("RockOnline")
                .withDefaultNavigationView(mainView)
                .withDesign(AppBarDesign.DEFAULT)
                .add(new MenuHeader(new ExternalResource("http://bpodryad.ru/data/otcher/RRO-logo.png")), HEADER)
                .add("Эфир", VaadinIcons.PLAY_CIRCLE_O, holder, mainView)
                .add("События", VaadinIcons.LIST, View2.class)
                .add("Расписание", VaadinIcons.SPLINE_CHART, View2.class) 
                .add("О нас", VaadinIcons.GROUP, View2.class)   
                .addClickable("Нажми меня", VaadinIcons.QUESTION, clickEvent -> {/*Click Event*/})
                .build();
        setContent(layout);
        mainView.CheckTrack();
        
        vkLogin.userCode = vaadinRequest.getParameter("code");
 
        try{    
            if( userCode != null){
                showMessage(vkLogin.Auth(userCode));
            }else{          
                vkLogin = new Login();
                getUI().getPage().setLocation(vkLogin.url);             
            }
        }catch (Exception ex){
            showError(ex);
        }          

//        //UI UPDATER
//        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
//        exec.scheduleAtFixedRate(() -> {
//            final MyUI ui = MyUI.this;
//            ui.access(() -> {
//                mainView.CheckTrack();
//                ui.push();
//            });
//        }, 0, 5, TimeUnit.SECONDS);
//        //UI UPDATER

    }
    
    //public static class MainView extends VerticalLayout implements View {}
    public static class View2 extends VerticalLayout implements View {}
    public static class View3 extends VerticalLayout implements View {}
    public static class View4 extends VerticalLayout implements View {}
    public static class View5 extends VerticalLayout implements View {}
    public static class View6 extends VerticalLayout implements View {}   


    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet { 
    }  
       
    
    private void showError(Object e){
        Notification errorNotif = new Notification("ERROR:", e.toString());
        errorNotif.setPosition(Position.BOTTOM_RIGHT);
        errorNotif.setDelayMsec(100000);
        errorNotif.show(Page.getCurrent());       
    }
     
    private void showMessage(String text){
        Notification errorNotif = new Notification("INFO:", text);
        errorNotif.setPosition(Position.BOTTOM_RIGHT);
        errorNotif.setDelayMsec(100000);
        errorNotif.show(Page.getCurrent());       
    }   
    
}

