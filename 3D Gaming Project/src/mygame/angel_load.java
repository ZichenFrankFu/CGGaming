package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.anim.AnimComposer;
import com.jme3.anim.SkinningControl;

public class angel_load extends SimpleApplication {

    private Spatial angel;
    private AnimComposer animComposer;
    private SkinningControl skinningControl;

    public static void main(String[] args) {
        angel_load app = new angel_load();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        angel = assetManager.loadModel("Models/Angel/angel.glb");
        rootNode.attachChild(angel);
        angel.setLocalScale(100f);
        angel.setName("angel");

        // Set up directional light
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, -2, -3).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        // Attempt to retrieve AnimComposer for animations
        animComposer = angel.getControl(AnimComposer.class);
        if (animComposer != null) {
            System.out.println("AnimComposer found. Available Animations: " + animComposer.getAnimClipsNames());

            // Set and play a specific animation (replace "YourAnimationName" with the animation name)
            String animationName = "YourAnimationName"; // replace with actual animation name
            if (animComposer.getAnimClip(animationName) != null) {
                animComposer.setCurrentAction(animationName);
                animComposer.setGlobalSpeed(1.0f);  // Ensure normal playback speed
                System.out.println("Playing animation: " + animationName);
            } else {
                System.out.println("Animation " + animationName + " not found!");
            }

        } else {
            System.out.println("No AnimComposer found for the model! Checking for SkinningControl...");

            // Attempt to retrieve SkinningControl as a fallback
            skinningControl = angel.getControl(SkinningControl.class);
            if (skinningControl != null) {
                System.out.println("SkinningControl found. The model may have skeletal adjustments but no animations.");
            } else {
                System.out.println("No AnimComposer or SkinningControl found. Animation data might be missing.");
            }
        }
    }
}
