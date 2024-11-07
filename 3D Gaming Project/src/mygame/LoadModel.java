package mygame;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.material.RenderState;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;


/**
 * Authors: Jitong Xian, Xinming Shen, Zichen Fu
 * Load model class of game. 
 */
public class LoadModel extends SimpleApplication implements AnimEventListener {

    private BitmapText notificationText;
    private BitmapText crosshair;
    private GameState gameState;
    private SceneSwitchingManager sceneManager;
    
    private Node BloodyMonkey;
    private AnimControl control;
    private AnimChannel channel;

    private static final String ANI_IDLE = "Idle";
    private static final String ANI_WALK = "Walk";
    private static final String MAPPING_WALK = "walk forward";
    
    private float walkDuration = 2.0f;
    private float stopDuration = 1.0f; 
    private float timer = 0.0f; 
    private boolean isWalking = true; 
    
    private Vector3f walkDirection = new Vector3f(0, 0, 1);
    
    private BulletAppState bulletAppState;
    private RigidBodyControl classroomPhy;

    public static void main(String[] args) {
        LoadModel app = new LoadModel();
        app.start();
    }
    
    /**
     * Initializes the game scene including models, lights, input handling, and interaction. 
     */                            
    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        
        // Register the assets folder to ensure models can be loaded properly
        assetManager.registerLocator("assets/", FileLocator.class);

        // Initialize Scene Manager
        sceneManager = new SceneSwitchingManager(this);
        stateManager.attach(sceneManager);
        
        // Load the classroom scene
        Node classroomScene = new Node("Classroom Scene");
        Spatial classroom = assetManager.loadModel("Models/Anime_class_room/scene.j3o");
        classroomScene.attachChild(classroom);
        sceneManager.addScene(classroomScene);
        
        // Load the door model
        Spatial door = assetManager.loadModel("Models/Door/door.j3o");
        
        // Load the walldoor model
        Spatial walldoor = assetManager.loadModel("Models/Door/walldoor_001.j3o");
        classroomScene.attachChild(walldoor);
        Node walldoorNode = (Node) walldoor;
    
        // Load Elevator Model
        Spatial elevator = assetManager.loadModel("Models/Elevator/Elevator.j3o");
        Node elevatorNode = (Node) elevator;
        Spatial elevatorDoor = assetManager.loadModel("Models/Elevator/ElevatorDoors.j3o");
    
// Load Poop
Spatial poop = assetManager.loadModel("Models/Items/toiletpoop.j3o");
poop.setName("Poop");
RigidBodyControl poopControl = new RigidBodyControl(1.0f); // Mass of 1 for realistic movement
poop.addControl(poopControl);
bulletAppState.getPhysicsSpace().add(poopControl); // Add to physics space

// Load Cake
Spatial cake = assetManager.loadModel("Models/Items/CAFETERIAcake.j3o");
cake.setName("Cake");
RigidBodyControl cakeControl = new RigidBodyControl(0.5f); // Lighter mass for a different effect
cake.addControl(cakeControl);
bulletAppState.getPhysicsSpace().add(cakeControl); // Add to physics space


        // Attach the walls and floor model to the classroom node
        classroomScene.attachChild(door);
        classroomScene.attachChild(walldoor);
        classroomScene.attachChild(poop);
        classroomScene.attachChild(cake);
        walldoorNode.attachChild(door);
        walldoorNode.attachChild(elevator);
        walldoorNode.attachChild(elevatorDoor);
    
        // Move or rotate the wallsfloor & door
        walldoor.setLocalTranslation(-2, 1.1f, -4.7f);
        door.setLocalTranslation(0, -0.25f, 0);
        walldoorNode.setLocalScale(1.1f);
        elevatorNode.setLocalTranslation(0, 0, -3);
        elevatorDoor.setLocalTranslation(0, -3, -3);
        cake.setLocalTranslation(0, 0.1f, -1);
        cake.setLocalScale(2.0f);
        
        classroomPhy = new RigidBodyControl(0f); // Static, non-moving
        classroomScene.addControl(classroomPhy);
      bulletAppState.getPhysicsSpace().add(classroomPhy); // Add to physics space

        
        // Set up the lighting
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(0, -1, 0));
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        DirectionalLight newSun = new DirectionalLight();
        newSun.setDirection(new Vector3f(1, 1, 1));
        newSun.setColor(ColorRGBA.White);
        elevatorNode.addLight(newSun);

        DirectionalLight sun3 = new DirectionalLight();
        sun3.setDirection(new Vector3f(-1, -1, -1));
        sun3.setColor(ColorRGBA.White);
        elevatorNode.addLight(sun3);

        DirectionalLight classroomLight = new DirectionalLight();
        classroomLight.setDirection(new Vector3f(1, -1, 0));
        classroomLight.setColor(ColorRGBA.White.mult(1.5f));
        classroom.addLight(classroomLight);

        DirectionalLight classroomLight2 = new DirectionalLight();
        classroomLight2.setDirection(new Vector3f(-1, -1, 0));
        classroomLight2.setColor(ColorRGBA.White.mult(1.5f));
        classroom.addLight(classroomLight2);

        DirectionalLight classroomLight3 = new DirectionalLight();
        classroomLight3.setDirection(new Vector3f(0, -1, 1));
        classroomLight3.setColor(ColorRGBA.White.mult(1.5f));
        classroom.addLight(classroomLight3);
        

        DirectionalLight mainLightClassroom = new DirectionalLight();
        mainLightClassroom.setDirection(new Vector3f(-1, -1, -1).normalizeLocal());
        mainLightClassroom.setColor(ColorRGBA.White.mult(2.0f)); // Brighter white light
        classroom.addLight(mainLightClassroom);

        AmbientLight ambientLightClassroom = new AmbientLight();
        ambientLightClassroom.setColor(ColorRGBA.White.mult(1.0f)); // Bright ambient light to fill shadows
        classroom.addLight(ambientLightClassroom);


        // Initialize the HUD text for showing item notifications
        notificationText = new BitmapText(guiFont, false);
        notificationText.setSize(guiFont.getCharSet().getRenderedSize());
        notificationText.setText("Press F to interact");
        notificationText.setColor(ColorRGBA.Red);
        guiNode.attachChild(notificationText);

        gameState = new GameState(cam, inputManager, notificationText);
        stateManager.attach(gameState);

        // Initialize the UserInput class with the notification text
        new UserInput(cam, inputManager, gameState, notificationText);

        // Add items to the pickable list
        gameState.addPickableItem(poop);
        gameState.addPickableItem(cake);

        // Create and add a crosshair (point) in the center of the screen
        createCrosshair();
    
        // Load the blackhole scene
        Node blackholeScene = new Node("Blackhole Scene");
        Spatial blackhole = assetManager.loadModel("Models/Blackhole/scene.j3o");
        blackhole.setLocalScale(9.0f);
        blackhole.setLocalTranslation(0,0,-40.0f);
        blackholeScene.attachChild(blackhole);
        sceneManager.addScene(blackholeScene);

        
        // load Angel
        Spatial angel = assetManager.loadModel("Models/Angel/scene.gltf");
        blackholeScene.attachChild(angel);
        angel.setLocalTranslation(0,0,-5.0f);
        angel.setLocalScale(90f); 
        angel.setName("angel");
        //ObjectControl angelControl = new ObjectControl(1.0f);
        //angel.addControl(angelControl);
        
        // Add delicate ambient lighting to ensure the blackhole is visible without being harsh
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(1.2f)); // Very soft ambient light to create a subtle background glow
        blackholeScene.addLight(ambient);

        // Add directional light to simulate a subtle atmospheric glow
        DirectionalLight glow = new DirectionalLight();
        glow.setDirection(new Vector3f(-0.5f, -1, -0.5f));
        glow.setColor(new ColorRGBA(0.5f, 0.4f, 0.3f, 1.0f).mult(0.2f)); // Duller light to make the model appear less bright
        blackholeScene.addLight(glow);

        // Add a point light to simulate the glowing effect surrounding the blackhole
        PointLight glowEffect = new PointLight();
        glowEffect.setPosition(new Vector3f(0, 0, -5));
        glowEffect.setColor(new ColorRGBA(0.7f, 0.5f, 0.3f, 1.0f).mult(0.3f)); // Soft, warmer light to reduce brightness
        glowEffect.setRadius(10f); // Adjust radius to control the spread of the glow
        blackholeScene.addLight(glowEffect);

        // Load and scale the BloodyMonkey model
        BloodyMonkey = (Node) assetManager.loadModel("Models/monster_classroom/Jaime.j3o");
        BloodyMonkey.rotate(0, FastMath.DEG_TO_RAD * 180, 0);
        BloodyMonkey.setLocalScale(4f);
        BloodyMonkey.setLocalTranslation(0, -1.75f, 0);
        classroomScene.attachChild(BloodyMonkey);

        control = BloodyMonkey.getControl(AnimControl.class);
        if (control != null) {
            for (String anim : control.getAnimationNames()) {
                System.out.println(anim);  // Print available animations
            }
            control.addListener(this); // Register this as the AnimEventListener
            channel = control.createChannel();
            channel.setAnim(ANI_IDLE);
        }

        flyCam.setEnabled(true);
        cam.setLocation(new Vector3f(0, 1.75f, 0)); 
        cam.lookAtDirection(new Vector3f(0, 0, -1), Vector3f.UNIT_Y);
        
        //Load materials onto BloodyMonkey model
        Material BloodyMonkeyMaterial = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        TextureKey BloodyMonkeyTextureKey = new TextureKey("Textures/blood.png", true); 
        Texture BloodyMonkeyTexture = assetManager.loadTexture(BloodyMonkeyTextureKey);
        BloodyMonkeyMaterial.setTexture("DiffuseMap", BloodyMonkeyTexture);
        BloodyMonkey.setMaterial(BloodyMonkeyMaterial);
        
        //User Interface
        Picture frame = new Picture("User interface frame");
        frame.setImage(assetManager, "Interface/save.png", false); 
        float iconWidth = 52;
        float iconHeight = 47;
        frame.setWidth(iconWidth);
        frame.setHeight(iconHeight);
        frame.setPosition(5, settings.getHeight() - iconHeight - 9);
        guiNode.attachChild(frame);
    
        frame.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        Picture frame2 = new Picture("Button 2");
        frame2.setImage(assetManager, "Interface/load.png", false);
        frame2.setWidth(iconWidth);
        frame2.setHeight(iconHeight);
        frame2.setPosition(iconWidth + 10, settings.getHeight() - iconHeight - 5);
        guiNode.attachChild(frame2);
    
        frame2.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        
        sceneManager.switchToNextScene();

        inputManager.addMapping("SwitchScene", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(switchSceneListener, "SwitchScene");
        inputManager.addMapping(MAPPING_WALK, new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addListener(actionListener, MAPPING_WALK);
        inputManager.addListener(analogListener, MAPPING_WALK);
        
    }

    private final ActionListener switchSceneListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("SwitchScene") && isPressed) {
                sceneManager.switchToNextScene();
            }
        }
    };

    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals(MAPPING_WALK)) {
                if (isPressed) {
                    if (!channel.getAnimationName().equals(ANI_WALK)) {
                        channel.setAnim(ANI_WALK);
                    }
                } else {
                    channel.setAnim(ANI_IDLE);
                }
            }
        }
    };
    
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float intensity, float tpf) {
            if (name.equals(MAPPING_WALK)) {
                BloodyMonkey.move(0, 0, tpf);
            }
        }
    };

    private void createCrosshair() {
        crosshair = new BitmapText(guiFont, false);
        crosshair.setSize(guiFont.getCharSet().getRenderedSize());
        crosshair.setText("+");
        crosshair.setColor(ColorRGBA.White);
        float x = (cam.getWidth() / 2) - (crosshair.getLineWidth() / 2);
        float y = (cam.getHeight() / 2) + (crosshair.getLineHeight() / 2);
        crosshair.setLocalTranslation(x, y, 0);
        guiNode.attachChild(crosshair);
    }
    
    @Override
    public void stop() {
        String userHome = System.getProperty("user.home");
        File file = new File(userHome + "/SavedGames/" + "savedgame.j3o");
        BinaryExporter exporter = BinaryExporter.getInstance();
        try {
            exporter.save(rootNode, file);
        } catch (IOException ex) {
            Logger.getLogger(LoadModel.class.getName()).log(Level.SEVERE, "Error: Failed to save game!", ex);
        }
        super.stop();
    }
    
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if (animName.equals(ANI_WALK)) {
            System.out.println(control.getSpatial().getName() + " completed one walk loop.");
        } else if (animName.equals(ANI_IDLE)) {
            System.out.println(control.getSpatial().getName() + " completed one idle loop.");
        }
    }
    
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        if (animName.equals(ANI_WALK)) {
            System.out.println(control.getSpatial().getName() + " started walking.");
        } else if (animName.equals(ANI_IDLE)) {
            System.out.println(control.getSpatial().getName() + " started being idle.");
        }
    }
    
    
    @Override
    public void simpleUpdate(float tpf) {
        timer += tpf;

        if (isWalking) {
            if (timer >= walkDuration) {
                // Stop walking and turn to face the camera
                timer = 0;
                isWalking = false;
                channel.setAnim(ANI_IDLE);

                // Calculate the direction to the camera
                Vector3f directionToCamera = cam.getLocation().subtract(BloodyMonkey.getWorldTranslation()).normalizeLocal();
                BloodyMonkey.lookAt(cam.getLocation(), Vector3f.UNIT_Y); // Turn to face the camera

                // Update walkDirection to point toward the camera
                walkDirection.set(directionToCamera);
                BloodyMonkey.rotate(0, FastMath.PI, 0);
            } else {
                // Move in the direction the player is facing
                BloodyMonkey.move(walkDirection.mult(tpf));
                if (!channel.getAnimationName().equals(ANI_WALK)) {
                    channel.setAnim(ANI_WALK);
                    channel.setSpeed(10.0f);
                }
            }
        } else {
            if (timer >= stopDuration) {
                timer = 0;
                isWalking = true;
            }
        }
    }
    
}
