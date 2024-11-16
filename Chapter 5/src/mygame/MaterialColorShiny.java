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
public class MaterialColorShiny extends SimpleApplication {

    public static void main(String[] args) {
        MaterialColor app = new MaterialColor ();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Sphere sphereMesh = new Sphere(32,32, 1f);
        Geometry sphere1Geo = new Geometry("rough sphere", sphereMesh);
        Material sphere1Mat = new Material(assetManager,
        "Common/MatDefs/Light/Lighting.j3md");
        sphere1Mat.setBoolean("UseMaterialColors",true);
        sphere1Mat.setColor("Ambient", ColorRGBA.Gray );
        sphere1Mat.setColor("Diffuse", ColorRGBA.Cyan );
        sphere1Mat.setColor("Specular", ColorRGBA.White );
        sphere1Mat.setFloat("Shininess", 8f); // [1,128]
        sphere1Geo.setMaterial(sphere1Mat);
        sphere1Geo.move(-2.5f, 0, 0);
        rootNode.attachChild(sphere1Geo);
        
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
