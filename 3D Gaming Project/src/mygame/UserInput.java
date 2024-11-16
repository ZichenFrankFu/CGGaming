package mygame;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.font.BitmapText;

public class UserInput {

    private Camera cam;
    private InputManager inputManager;
    private GameState gameState;
    private SoundManager soundManager; // Add SoundManager instance
    private BitmapText notificationText;

    private boolean forward = false, backward = false, left = false, right = false, speedBoost = false;
    private float normalSpeed = 5.0f;
    private float fastSpeed = 10.0f;
    private float currentSpeed = normalSpeed;
    private float cameraHeight = 1.75f;

    private float stepTimer = 0f; // Timer for step sound
    private float stepInterval = 0.5f; // Interval between steps (in seconds)

    public UserInput(Camera cam, InputManager inputManager, GameState gameState, SoundManager soundManager, BitmapText notificationText) {
        this.cam = cam;
        this.inputManager = inputManager;
        this.gameState = gameState;
        this.soundManager = soundManager; // Initialize SoundManager
        this.notificationText = notificationText;
        initializeInput();
    }

    private void initializeInput() {
        inputManager.addMapping("MoveForward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("MoveBackward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("MoveLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("MoveRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("SpeedUp", new KeyTrigger(KeyInput.KEY_LSHIFT));
        inputManager.addMapping("PickUp", new KeyTrigger(KeyInput.KEY_F));

        inputManager.addListener(actionListener, "MoveForward", "MoveBackward", "MoveLeft", "MoveRight", "SpeedUp", "PickUp");
    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("MoveForward")) forward = isPressed;
            if (name.equals("MoveBackward")) backward = isPressed;
            if (name.equals("MoveLeft")) left = isPressed;
            if (name.equals("MoveRight")) right = isPressed;

            if (name.equals("SpeedUp")) {
                speedBoost = isPressed;
                stepInterval = speedBoost ? 0.2f : 0.5f; // Decrease interval for faster steps
            }

            if (name.equals("PickUp") && isPressed) {
                soundManager.playSFX("click"); // Play click sound
                gameState.pickUpItem();
            }

            gameState.setMovementKeys(forward, backward, left, right, speedBoost);
        }
    };

    public void updateCamera(float tpf) {
        currentSpeed = speedBoost ? fastSpeed : normalSpeed;

        Vector3f walkDirection = new Vector3f(0, 0, 0);
        if (forward) walkDirection.addLocal(cam.getDirection().mult(currentSpeed * tpf));
        if (backward) walkDirection.addLocal(cam.getDirection().mult(-currentSpeed * tpf));
        if (left) walkDirection.addLocal(cam.getLeft().mult(currentSpeed * tpf));
        if (right) walkDirection.addLocal(cam.getLeft().mult(-currentSpeed * tpf));

        Vector3f camPos = cam.getLocation();
        camPos.addLocal(walkDirection.x, 0, walkDirection.z);
        camPos.y = cameraHeight;
        cam.setLocation(camPos);

        // Handle step sound
        playStepSound(tpf, walkDirection);
    }

    private void playStepSound(float tpf, Vector3f walkDirection) {
        if (walkDirection.lengthSquared() > 0) { // Only play steps when moving
            stepTimer += tpf;
            if (stepTimer >= stepInterval) {
                soundManager.playSFX("wood_step"); // Play step sound
                stepTimer = 0f; // Reset timer
            }
        } else {
            stepTimer = 0f; // Reset timer if not moving
        }
    }
}
