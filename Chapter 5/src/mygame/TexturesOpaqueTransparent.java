package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class TexturesOpaqueTransparent extends SimpleApplication {

    public static void main(String[] args) {
        MaterialColorShiny app = new MaterialColorShiny();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Sphere sphereMesh = new Sphere(16, 16, 1);
        Geometry sphereGeo = new Geometry("lit textured sphere",
        sphereMesh);
        Material sphereMat = new Material(assetManager,
        "Common/MatDefs/Light/Lighting.j3md");
        sphereMat.setTexture("DiffuseMap",
        assetManager.loadTexture("Interface/Monkey.png"));
        
        sphereMat.setFloat("AlphaDiscardThreshold", 0.5f);
        sphereGeo.setQueueBucket(Bucket.Transparent);
        
        sphereGeo.setMaterial(sphereMat);
        sphereGeo.move(-2f, 0f, 0f);
        sphereGeo.rotate(FastMath.DEG_TO_RAD * -90,
        FastMath.DEG_TO_RAD * 120, 0f);
        rootNode.attachChild(sphereGeo);
        
        Box windowMesh = new Box(new Vector3f(0f, 0f, 0f),
        1f, 1.4f, 0.01f);
        Geometry windowGeo = new Geometry(
        "stained glass window", windowMesh);
        Material windowMat = new Material(assetManager,
        "Common/MatDefs/Light/Lighting.j3md");
        windowMat.setTexture("DiffuseMap",
        assetManager.loadTexture("Textures/mucha-window.png"));
        windowMat.getAdditionalRenderState().
        setBlendMode(BlendMode.Alpha);
        windowGeo.setMaterial(windowMat);
        windowGeo.setQueueBucket(Bucket.Transparent);
        windowGeo.setMaterial(windowMat);
        windowGeo.move(1f,0f,0f);
        rootNode.attachChild(windowGeo);
        
        // Add Lighting
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(0.3f));  // Adjust intensity with 'mult'
        rootNode.addLight(ambient);

        // Add directional light to give a clear sense of depth and shadows
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
