package mygame;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.jme3.scene.Node;
import com.jme3.font.BitmapText;
import com.jme3.scene.Geometry;
import com.jme3.collision.CollisionResults;

import java.util.ArrayList;
import java.util.List;

public class UserInput {

    private Camera cam;
    private InputManager inputManager;
    private List<Spatial> pickableItems; // List to store pickable items
    private BitmapText notificationText; // Notification for item pick-up
    private Spatial aimedItem; // The currently aimed item (if any)

    private boolean forward = false, backward = false, left = false, right = false, speedBoost = false;
    private float normalSpeed = 5.0f;  // Normal movement speed
    private float fastSpeed = 10.0f;   // Speed when Shift is held down
    private float currentSpeed = normalSpeed; // The current movement speed
    private float cameraHeight = 1.75f;  // Fixed camera height for walking

    public UserInput(Camera cam, InputManager inputManager, BitmapText notificationText) {
        this.cam = cam;
        this.inputManager = inputManager;
        this.notificationText = notificationText;
        this.pickableItems = new ArrayList<>(); // Initialize list of pickable items
        initializeInput();
    }

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

    // Method to allow adding pickable items to the list
    public void addPickableItem(Spatial item) {
        pickableItems.add(item);
    }

    // Raycasting to check if the player is aiming at a pickable item
    private void checkAimedItem() {
        // Cast a ray from the camera forward
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
        CollisionResults results = new CollisionResults();

        aimedItem = null; // Reset aimed item

        // Check all pickable items for a hit
        for (Spatial item : pickableItems) {
            if (item != null) {
                item.collideWith(ray, results);
                if (results.size() > 0) {
                    aimedItem = item; // If a collision is detected, set it as aimed item
                    break;
                }
            }
        }

        // If an item is aimed at, show the notification
        if (aimedItem != null) {
            notificationText.setText(""); // Clear existing text
            notificationText.setText("Press 'F' to pick up " + aimedItem.getName());
            notificationText.setLocalTranslation(cam.getWidth() / 2 - notificationText.getLineWidth() / 2, cam.getHeight() / 2 + 20, 0);
        } else {
            notificationText.setText(""); // No item aimed, clear notification
        }
    }

    // Method to handle picking up items
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

    // ActionListener to handle input
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
                pickUpItem(); // Pick up the aimed item
            }
        }
    };

    // Method to update the camera position and check for aimed items
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

        // Check if the player is aiming at any pickable item
        checkAimedItem();
    }
    
    
}
