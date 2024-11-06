package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.asset.plugins.FileLocator;
import java.util.concurrent.Callable;
import com.jme3.app.state.AppStateManager;

public class TestBlackholeModelLoading extends SimpleApplication {

    public static void main(String[] args) {
        TestBlackholeModelLoading app = new TestBlackholeModelLoading();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        // Register the assets folder to ensure the model can be loaded properly
        assetManager.registerLocator("assets/", FileLocator.class);

        // Load the blackhole model asynchronously to avoid freezing the main thread
        enqueue(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Spatial blackhole = assetManager.loadModel("Models/Blackhole/scene.j3o");

                if (blackhole == null) {
                    System.out.println("Error: Blackhole model failed to load.");
                    return null;
                }

                // Set position, scale, and rotation to ensure visibility
                blackhole.setLocalTranslation(0, 0, -5); // Position the model in front of the camera
                blackhole.setLocalScale(9.0f); // Set a reasonable scale
                blackhole.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI, Vector3f.UNIT_Y)); // Rotate to face the camera

                // Attach the blackhole model to the rootNode
                rootNode.attachChild(blackhole);

                return null;
            }
        });

        // Add delicate ambient lighting to ensure the blackhole is visible without being harsh
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(0.02f)); // Very soft ambient light to create a subtle background glow
        rootNode.addLight(ambient);

        // Add directional light to simulate a subtle atmospheric glow
        DirectionalLight glow = new DirectionalLight();
        glow.setDirection(new Vector3f(-0.5f, -1, -0.5f));
        glow.setColor(new ColorRGBA(0.5f, 0.4f, 0.3f, 1.0f).mult(0.2f)); // Duller light to make the model appear less bright
        rootNode.addLight(glow);

        // Add a point light to simulate the glowing effect surrounding the blackhole
        PointLight glowEffect = new PointLight();
        glowEffect.setPosition(new Vector3f(0, 0, -5));
        glowEffect.setColor(new ColorRGBA(0.7f, 0.5f, 0.3f, 1.0f).mult(0.3f)); // Soft, warmer light to reduce brightness
        glowEffect.setRadius(10f); // Adjust radius to control the spread of the glow
        rootNode.addLight(glowEffect);

        // Position the camera to look at the blackhole
        cam.setLocation(new Vector3f(0, 2, 10));
        cam.lookAtDirection(new Vector3f(0, 0, -1), Vector3f.UNIT_Y);

        // Enable flying camera for movement
        flyCam.setEnabled(true);
    }
}
