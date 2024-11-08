/**
 * Authors: Jitong Xian, Xinming Shen, Zichen Fu
 * User input class of game. 
 */

package mygame;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.jme3.font.BitmapText;
import com.jme3.collision.CollisionResults;

import java.util.ArrayList;
import java.util.List;

public class UserInput {

    private Camera cam;
    private InputManager inputManager;
    private GameState gameState;
    private List<Spatial> pickableItems; // List to store pickable items
    private BitmapText notificationText; // Notification for item pick-up
    private Spatial aimedItem; // The currently aimed item (if any)

    private boolean forward = false, backward = false, left = false, right = false, speedBoost = false;
    private float normalSpeed = 5.0f;  // Normal movement speed
    private float fastSpeed = 10.0f;   // Speed when Shift is held down
    private float currentSpeed = normalSpeed; // The current movement speed
    private float cameraHeight = 1.75f;  // Fixed camera height for walking
    
    /**
     * Constructor for initializing UserInput, camera, input manager, 
     * game state, and notification text.
     * 
     * @param cam The camera. 
     * @param inputManager The inputmanager that manages user inputs.
     * @param gameState The game state object that manages movement and item interaction.
     * @param notificationText The text to notify the player about pick-ups.
     */
    public UserInput(Camera cam, InputManager inputManager, GameState gameState, BitmapText notificationText) {
        this.cam = cam;
        this.inputManager = inputManager;
        this.gameState = gameState;
        this.notificationText = notificationText;
        this.pickableItems = new ArrayList<>(); // Initialize list of pickable items
        initializeInput();
    }
    
    /**
     * Initializes the input mappings for player controls, such as movement (WASD keys), 
     * speed boost (Shift key), and item pick-up (F key). 
     */
    private void initializeInput() {
        // Set up key mappings for movement and speed boost
        inputManager.addMapping("MoveForward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("MoveBackward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("MoveLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("MoveRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("SpeedUp", new KeyTrigger(KeyInput.KEY_LSHIFT));

        // Add key mapping for picking up items with the "F" key
        inputManager.addMapping("PickUp", new KeyTrigger(KeyInput.KEY_F));

        // Attach the action listener for all key mappings
        inputManager.addListener(actionListener, "MoveForward", "MoveBackward", "MoveLeft", "MoveRight", "SpeedUp", "PickUp");
    }


    /**
     * Allow players pick up items in the game. If the player is aiming at 
     * an item, this method removes the item from the scene and from the list of pickable items.
     */
    private void pickUpItem() {
        if (aimedItem != null) {
            // Pick up the aimed item (remove from scene and list)
            aimedItem.removeFromParent(); // Remove item from the scene
            pickableItems.remove(aimedItem); // Remove item from the pickable list
            System.out.println("You picked up: " + aimedItem.getName());
            aimedItem = null; // Reset aimed item
            notificationText.setText(""); // Clear notification
        }
    }

    /**
     * ActionListener that listens for key inputs and updates accordingly.
     */
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
            } else if (name.equals("PickUp") && isPressed) {
                gameState.pickUpItem(); // Delegate pick-up action to GameState
            }
            
            // Update the movement keys in GameState
            gameState.setMovementKeys(forward, backward, left, right, speedBoost);
        }
    };

    /**
     * Updates the camera's position based on player input. The camera also stays 
     * at a fixed height to simulate walking on a floor.
     * 
     * @param tpf Time per frame, used to ensure smooth movement regardless of frame rate.
     */
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

        // Apply movement only in the XZ plane and keep the camera height (y-axis) fixed
        camPos.addLocal(walkDirection.x, 0, walkDirection.z);
        camPos.y = cameraHeight; // Ensure the camera stays at the walking height

        // Set the new camera position
        cam.setLocation(camPos);

    }
    
    
}
