package mygame;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        
        CubeChaser app = new CubeChaser();
        
        //Settings
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My Game");
        settings.setResolution(1920,1080);
        settings.setSamples(2);
        app.setShowSettings(false);
        app.start();
    }
                                
    @Override
    public void simpleInitApp() {
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
