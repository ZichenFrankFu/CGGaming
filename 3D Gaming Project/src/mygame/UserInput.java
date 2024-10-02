package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.input.InputManager;
import com.jme3.math.FastMath;
import com.jme3.renderer.Camera;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class UserInput{
    
    private Camera cam;
    private InputManager inputManager;

    private boolean forward = false, backward = false, left = false, right = false, speedBoost = false;
    private float normalSpeed = 5.0f;  // Normal movement speed
    private float fastSpeed = 10.0f;   // Speed when Shift is held down
    private float currentSpeed = normalSpeed; // The current movement speed
    private float cameraHeight = 1.75f;  // Fixed camera height for walking

    public UserInput(Camera cam, InputManager inputManager) {
        this.cam = cam;
        this.inputManager = inputManager;
        initializeInput();
    }

    private void initializeInput() {
        // Set up key mappings for movement and speed boost
        inputManager.addMapping("MoveForward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("MoveBackward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("MoveLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("MoveRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("SpeedUp", new KeyTrigger(KeyInput.KEY_LSHIFT));

        // Attach the action listener for the input mappings
        inputManager.addListener(actionListener, "MoveForward", "MoveBackward", "MoveLeft", "MoveRight", "SpeedUp");
    }

private final ActionListener actionListener = new ActionListener() {
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("MoveForward")) {
            forward = isPressed;
        } else if (name.equals("MoveBackward")) {
            backward = isPressed;
        } else if (name.equals("MoveLeft")) {
            left = isPressed;
        } else if (name.equals("MoveRight")) {
            right = isPressed;
        } else if (name.equals("SpeedUp")) {
            speedBoost = isPressed;
        }
    }
};


    // Method to update the camera position based on input
    public void updateCamera(float tpf) {
        // Set the current speed depending on whether Shift is pressed
        currentSpeed = speedBoost ? fastSpeed : normalSpeed;

        // Create a vector for movement
        Vector3f walkDirection = new Vector3f(0, 0, 0);

        // Calculate movement direction based on key inputs and camera's current orientation
        if (forward) {
            walkDirection.addLocal(cam.getDirection().mult(currentSpeed * tpf));
        }
        if (backward) {
            walkDirection.addLocal(cam.getDirection().mult(-currentSpeed * tpf));
        }
        if (left) {
            walkDirection.addLocal(cam.getLeft().mult(currentSpeed * tpf));
        }
        if (right) {
            walkDirection.addLocal(cam.getLeft().mult(-currentSpeed * tpf));
        }

        // Get the current camera position
        Vector3f camPos = cam.getLocation();

        // Apply movement only in the XZ plane and keep the camera height (Y-axis) fixed
        camPos.addLocal(walkDirection.x, 0, walkDirection.z);
        camPos.y = cameraHeight; // Ensure the camera stays at the walking height

        // Set the new camera position
        cam.setLocation(camPos);
    }

    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
}
