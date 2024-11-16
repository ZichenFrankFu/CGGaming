package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class MaterialColor extends SimpleApplication {

    public static void main(String[] args) {
        MaterialsUnshaded app = new MaterialsUnshaded();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Sphere sphereMesh = new Sphere(32,32, 1f);
        Geometry sphereGeo = new Geometry("Colored lit sphere",
        sphereMesh);
        Material sphereMat = new Material(assetManager,
        "Common/MatDefs/Light/Lighting.j3md");
        sphereMat.setBoolean("UseMaterialColors", true);
        sphereMat.setColor("Diffuse", ColorRGBA.Blue );
        sphereMat.setColor("Ambient", ColorRGBA.Gray );
        sphereGeo.setMaterial(sphereMat);
        rootNode.attachChild(sphereGeo);
        
        //Add Lighting
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(0.3f));  // Adjust intensity with 'mult'
        rootNode.addLight(ambient);

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, -1, -1).normalizeLocal()); // Light direction
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
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
