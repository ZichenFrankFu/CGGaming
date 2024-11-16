package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class LoadModel_test extends SimpleApplication implements AnimEventListener {

    private BitmapText notificationText;
    private BulletAppState bulletAppState;
    private SoundManager soundManager;
    private Node BloodyMonkey;
    private AnimControl control;
    private AnimChannel channel;
    private BetterCharacterControl characterControl;

    private boolean isRunning = false;
    private boolean forward = false, backward = false, left = false, right = false;
    private float stepTimer = 0f;
    private final float WALK_INTERVAL = 0.5f;
    private final float RUN_INTERVAL = 0.3f;

    public static void main(String[] args) {
        LoadModel_test app = new LoadModel_test();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        // Initialize BulletAppState for physics
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        // Register asset folder
        assetManager.registerLocator("assets/", FileLocator.class);

        // Initialize SoundManager
        soundManager = new SoundManager(assetManager);
        soundManager.playBGM("quiet_bgm");

        // Add classroom scene
        Node classroomScene = new Node("Classroom Scene");
        Spatial classroom = assetManager.loadModel("Models/Anime_class_room/scene.j3o");
        classroomScene.attachChild(classroom);
        rootNode.attachChild(classroomScene);

        // Add lighting
        addLightsToClassroom(classroomScene);

        // Initialize notification text
        notificationText = new BitmapText(guiFont, false);
        notificationText.setSize(guiFont.getCharSet().getRenderedSize());
        notificationText.setText("Press F to interact");
        notificationText.setColor(ColorRGBA.Red);
        guiNode.attachChild(notificationText);

        // Load BloodyMonkey model
        BloodyMonkey = (Node) assetManager.loadModel("Models/monster_classroom/Jaime.j3o");
        BloodyMonkey.setLocalScale(1.0f);
        classroomScene.attachChild(BloodyMonkey);

        // Add character control for physics-based movement
        characterControl = new BetterCharacterControl(1.5f, 4f, 50f);
        BloodyMonkey.addControl(characterControl);
        bulletAppState.getPhysicsSpace().add(characterControl);

        // Add animations
        control = BloodyMonkey.getControl(AnimControl.class);
        if (control != null) {
            control.addListener(this);
            channel = control.createChannel();
            channel.setAnim("Idle");
        }

        // Enable flyCam and attach input
        flyCam.setEnabled(true);
        flyCam.setMoveSpeed(10f);

        // Set input mappings
        inputManager.addMapping("MoveForward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("MoveBackward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("MoveLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("MoveRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Run", new KeyTrigger(KeyInput.KEY_LSHIFT));
        inputManager.addMapping("PickUp", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addListener(actionListener, "MoveForward", "MoveBackward", "MoveLeft", "MoveRight", "Run", "PickUp");
    }
private AudioNode currentStepSound; // Track the current playing sound
private boolean playingStepSound = false; // Track if a step sound is playing
private float stepDuration = 0.5f; // Approximate duration of a step sound (in seconds)
private float stepSoundTimer = 0f; // Timer to track when the sound should stop

@Override
public void simpleUpdate(float tpf) {
    // Determine movement direction
    Vector3f direction = new Vector3f(0, 0, 0);
    if (forward) direction.addLocal(cam.getDirection().mult(1.0f));
    if (backward) direction.addLocal(cam.getDirection().mult(-1.0f));
    if (left) direction.addLocal(cam.getLeft().mult(1.0f));
    if (right) direction.addLocal(cam.getLeft().mult(-1.0f));

    float speed = isRunning ? 10.0f : 5.0f;
    direction.y = 0; // Ensure movement is constrained to the ground
    characterControl.setWalkDirection(direction.mult(speed));

    // Handle step sounds
    if (direction.lengthSquared() > 0) { // Check if character is moving
        stepTimer += tpf; // Increment timer
        stepSoundTimer += tpf; // Track how long the sound has been playing
        float stepInterval = isRunning ? RUN_INTERVAL : WALK_INTERVAL;

        if (!playingStepSound && stepTimer >= stepInterval) {
            // Fetch and play a new step sound
            AudioNode stepSound = soundManager.getSFXNodeForStep(isRunning ? "elevator_step" : "step");
            if (stepSound != null) {
                stepSound.setPitch(isRunning ? 0.8f : 1.0f); // Adjust pitch
                stepSound.play(); // Play sound
                playingStepSound = true; // Mark sound as playing
                stepSoundTimer = 0f; // Reset the step sound timer
            }
            stepTimer = 0f; // Reset the step timer
        }

        // Reset playingStepSound flag when the sound is expected to finish
        if (playingStepSound && stepSoundTimer >= stepDuration) {
            playingStepSound = false; // Allow the next sound to play
        }

        // Set walk animation
        if (!channel.getAnimationName().equals("Walk")) {
            channel.setAnim("Walk");
        }
    } else { // When idle
        stepTimer = 0f; // Reset step timer
        playingStepSound = false; // Reset sound playing state
        if (!channel.getAnimationName().equals("Idle")) {
            channel.setAnim("Idle");
        }
    }

    // Keep camera height fixed
    cam.setLocation(new Vector3f(cam.getLocation().x, 1.75f, cam.getLocation().z));
}





    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            switch (name) {
                case "MoveForward" -> forward = isPressed;
                case "MoveBackward" -> backward = isPressed;
                case "MoveLeft" -> left = isPressed;
                case "MoveRight" -> right = isPressed;
                case "Run" -> isRunning = isPressed;
                case "PickUp" -> {
                    if (isPressed) {
                        soundManager.playSFX("click");
                    }
                }
            }
        }
    };

    private void addLightsToClassroom(Node classroomScene) {
        DirectionalLight directionalLight = new DirectionalLight();
        directionalLight.setDirection(new Vector3f(-0.5f, -1f, -0.5f).normalizeLocal());
        directionalLight.setColor(ColorRGBA.White.mult(1.3f));
        classroomScene.addLight(directionalLight);

        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(0.8f));
        classroomScene.addLight(ambientLight);
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if (animName.equals("Walk")) {
            System.out.println("Completed walk cycle.");
        } else if (animName.equals("Idle")) {
            System.out.println("Idle animation cycle complete.");
        }
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        if (animName.equals("Walk")) {
            System.out.println("Started walking animation.");
        } else if (animName.equals("Idle")) {
            System.out.println("Started idle animation.");
        }
    }
}
