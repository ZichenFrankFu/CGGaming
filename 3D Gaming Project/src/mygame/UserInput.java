package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class UserInput extends SimpleApplication {
    
    private final Trigger TRIGGER_SHIFT = new KeyTrigger(KeyInput.KEY_LSHIFT);
    private final String MAPPING_SPEED_UP = "SpeedUp";
    
    private final float normalSpeed = 5.0f;   // Default movement speed
    private final float fastSpeed = 10.0f;    // Increased movement speed
    private boolean isSpeedBoost = false;

    public static void main(String[] args) {
        UserInput app = new UserInput ();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        // Map the Shift key to increase movement speed
        inputManager.addMapping(MAPPING_SPEED_UP, TRIGGER_SHIFT);
        inputManager.addListener(actionListener, MAPPING_SPEED_UP);
    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals(MAPPING_SPEED_UP)) {
                isSpeedBoost = isPressed;
            }
        }
    };
    
    @Override
    public void simpleUpdate(float tpf) {
        // Handle movement and adjust speed when Shift is pressed
        Vector3f moveDirection = new Vector3f();
        
        if (isSpeedBoost) {
            // Move faster when Shift is held down
            moveDirection.multLocal(fastSpeed);
        } else {
            // Default movement speed
            moveDirection.multLocal(normalSpeed);
        }
        
        // Apply movement logic here, for example:
        // player.setWalkDirection(moveDirection);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
}
