package mygame;

import com.jme3.anim.AnimComposer;
import com.jme3.anim.tween.Tween;
import com.jme3.anim.tween.Tweens;
import com.jme3.anim.tween.action.Action;
import com.jme3.app.SimpleApplication;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.math.FastMath;
import java.io.File;
import java.io.IOException;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class AnimateModel extends SimpleApplication {
    
    private Node player;
    private AnimComposer control;
    private static final String ANI_IDLE = "stand";
    private static final String ANI_WALK = "Walk";
    private static final String MAPPING_WALK = "walk forward";

    public static void main(String[] args) {
        AnimateModel app = new AnimateModel ();
        app.start();
    }
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            
            if (name.equals(MAPPING_WALK) && isPressed) {
                if (!control.getCurrentAction().toString().contains(ANI_WALK)) {
                    control.setCurrentAction(ANI_WALK);
                }
            }
            if (name.equals(MAPPING_WALK) && !isPressed) {
                control.setCurrentAction(ANI_IDLE);
            }
        }
    };
    
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float intensity, float tpf) {
            if (name.equals(MAPPING_WALK) ) {
                player.move(0, 0, tpf);
            }
        }
    };
    
    public void onAnimComplete(String animationName) {
        System.out.println(control.getSpatial().getName() + " completed one " + animationName + " loop.");
    }

    @Override
    public void simpleInitApp() {
        
        inputManager.addMapping(MAPPING_WALK, new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(analogListener, MAPPING_WALK);
        inputManager.addListener(actionListener, MAPPING_WALK);
        
        player = (Node) assetManager.loadModel("Models/TestModels/Oto.j3o");
        player.rotate(0, FastMath.DEG_TO_RAD * 180, 0);
        control = player.getControl(AnimComposer.class);
        rootNode.attachChild(player);
        
        control.setCurrentAction(ANI_IDLE);
        
        // replace walk action with original walk action + doneTween
        Action walk = control.action(ANI_WALK);
        Tween doneTween = Tweens.callMethod(AnimateModel.this, "onAnimComplete", ANI_WALK);
        control.actionSequence(ANI_WALK, walk, doneTween);
        
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.5f, -0.5f, -0.5f ));
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
