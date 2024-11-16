package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.ui.Picture;
import com.jme3.asset.AssetManager;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class SimpleUserInterface extends SimpleApplication {
    
    private float distance=0;
    private BitmapText distanceText;
    Picture logo;
    
    public static void main(String[] args) {
        SimpleUserInterface app = new SimpleUserInterface();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        setDisplayStatView(false);
        setDisplayFps(false);
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        distanceText = new BitmapText(guiFont);
        distanceText.setSize(guiFont.getCharSet().getRenderedSize());
        distanceText.move(
        settings.getWidth()/2, // X
        distanceText.getLineHeight(), // Y
        0); // Z (depth layer)
        guiNode.attachChild(distanceText);
        distanceText.move(50, 20,0);
        
        Picture frame = new Picture("User interface frame");
        frame.setImage(assetManager, "Interface/frame.png", false);
        frame.move(settings.getWidth()/2-265, 0, -2);
        frame.setWidth(530);
        frame.setHeight(10);
        guiNode.attachChild(frame);
        
        logo = new Picture("logo");
        logo.setImage(assetManager, "Interface/Monkey.png", true);
        logo.move(settings.getWidth()/2-47, 2, -1);
        logo.setWidth(95);
        logo.setHeight(75);
        guiNode.attachChild(logo);
        
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        distance = Vector3f.ZERO.distance(cam.getLocation());
        distanceText.setText("Distance: "+distance);
        if (distance < 10f) {
            logo.setImage(assetManager, "Interface/chimpanzee-smile.gif", true);
        } else {
            logo.setImage(assetManager,"Interface/chimpanzee-sad.gif", true);
        }
    }
    

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
