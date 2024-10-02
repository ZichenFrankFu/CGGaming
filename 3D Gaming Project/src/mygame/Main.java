package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.ui.Picture;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        //Main app = new Main();
        LoadModel app = new LoadModel ();
        app.start(); 
        /*
        // To load the save/load icon in top left corner
        Picture frame = new Picture("User interface frame");
        frame.setImage(assetManager, "Interface/save.png", false); 
        float iconWidth = 52; // this is arbitary
        float iconHeight = 47;
        frame.setWidth(iconWidth);
        frame.setHeight(iconHeight);
        frame.setPosition(5, settings.getHeight() - iconHeight - 9);
        guiNode.attachChild(frame);
        
        Picture frame2 = new Picture("Button 2");
        frame2.setImage(assetManager, "Interface/load.png", false);
        frame2.setWidth(iconWidth);
        frame2.setHeight(iconHeight);
        frame2.setPosition(5 + iconWidth + 5, settings.getHeight() - iconHeight - 5);
        guiNode.attachChild(frame2);
        */
    }

    @Override
    public void simpleInitApp() {
        //LoadModel loadModel = new LoadModel();
        //loadModel.simpleInitApp();
        //UserInput userInput = new UserInput();
        //userInput.simpleInitApp();
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
