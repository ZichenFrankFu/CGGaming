package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class MaterialsUnshaded extends SimpleApplication {

    public static void main(String[] args) {
        MaterialsUnshaded app = new MaterialsUnshaded();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Sphere sphereMesh = new Sphere(16, 16, 1);
        Geometry sphereGeo = new Geometry("Unshaded textured sphere",
        sphereMesh);
        sphereGeo.move(-2f, 0f, 0f);
        sphereGeo.rotate(FastMath.DEG_TO_RAD * -90,
        FastMath.DEG_TO_RAD * 120, 0f);
        Material sphereMat = new Material(assetManager,
        "Common/MatDefs/Misc/Unshaded.j3md");
        sphereMat.setTexture("ColorMap",
        assetManager.loadTexture("Interface/Monkey.png"));
        sphereGeo.setMaterial(sphereMat);
        rootNode.attachChild(sphereGeo);
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
