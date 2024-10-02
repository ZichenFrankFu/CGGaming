/**
 * Authors: Jitong Xian, Xinming Shen, Zichen Fu
 * Game state class of game. 
 */

package mygame;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.input.InputManager;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class GameState extends AbstractAppState {

     private Camera cam;
    private InputManager inputManager;

    private boolean forward = false, backward = false, left = false, right = false, speedBoost = false;
    private float normalSpeed = 5.0f;  // Normal movement speed
    private float fastSpeed = 10.0f;   // Speed when Shift is held down
    private float currentSpeed = normalSpeed; // The current movement speed
    private float cameraHeight = 1.75f;  // Fixed camera height for walking

    public GameState(Camera cam, InputManager inputManager) {
        this.cam = cam;
        this.inputManager = inputManager;
    }

    @Override
    public void update(float tpf) {
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

    public void setMovementKeys(boolean forward, boolean backward, boolean left, boolean right, boolean speedBoost) {
        this.forward = forward;
        this.backward = backward;
        this.left = left;
        this.right = right;
        this.speedBoost = speedBoost;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        // Initialization code here
    }

    @Override
    public void cleanup() {
        // Cleanup code here
    }
    
}
