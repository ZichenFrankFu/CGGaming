package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class SaveModel extends SimpleApplication {

    public static void main(String[] args) {
        SaveModel app = new SaveModel ();
        app.start();
    }
    
    public void simpleInitApp() {
        
        String userHome = System.getProperty("user.home");
        assetManager.registerLocator(userHome, FileLocator.class);
        try {
            Node loadedNode = (Node) assetManager.loadModel("/SavedGames/savedgame.j3o");
            rootNode.attachChild(loadedNode);
        } catch (com.jme3.asset.AssetNotFoundException e) {}

        Spatial mymodel = assetManager.loadModel("Models/TestModels/Jaime.j3o");
        mymodel.move(FastMath.nextRandomFloat()*10-5,FastMath.nextRandomFloat()*10-5, FastMath.nextRandomFloat()*10-5);
        rootNode.attachChild(mymodel);
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)));
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
    }
    
    @Override
    public void stop() {
        String userHome = System.getProperty("user.home");
        File file = new File(
        userHome + "/SavedGames/" + "savedgame.j3o");
        BinaryExporter exporter = BinaryExporter.getInstance();
        try {
            exporter.save(rootNode, file);
        } catch (IOException ex) {
            Logger.getLogger(SaveModel.class.getName()).log(
            Level.SEVERE, "Error: Failed to save game!", ex);
        }
super.stop(); // continue quitting the game
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
