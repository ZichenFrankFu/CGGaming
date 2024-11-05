/**
 * Authors: Jitong Xian, Xinming Shen, Zichen Fu
 * Load model class of game. 
 */

package mygame;
import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingVolume;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
//import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.material.RenderState;
import com.jme3.ui.Picture;


/**
 * This is the Main Class of the Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class LoadModel extends SimpleApplication {
    
    private UserInput userInput;
    private BitmapText notificationText;
    private BitmapText crosshair;
    private GameState gameState;

    public static void main(String[] args) {
        LoadModel app = new LoadModel();
        app.start();
    }
    
    /**
     * Initializes the game scene including models, lights, input handling, and interaction. 
     */                            
    @Override
    public void simpleInitApp() {
    
    
    //Spatial test_classroom = assetManager.loadModel("Models/anime_class_room/scene.gltf");
    //rootNode.attachChild(test_classroom);

    
    // Create a node for the classroom
    Node classroom = new Node("Classroom");

    // Load the classroom demo scene model
    Spatial classroomDemo = assetManager.loadModel("Models/Classroom/classroom_demoscene.j3o");
    
    // Attach the classroom demo model to the classroom node
    classroom.attachChild(classroomDemo);
    
    // Load the walls and floor model
    Spatial wallsfloor = assetManager.loadModel("Models/Classroom/CAFETERIAwallfloor.j3o");
    
    // Load the walldoor model
    Spatial walldoor = assetManager.loadModel("Models/Door/walldoor_001.j3o");
    classroom.attachChild(walldoor);
    Node walldoorNode = (Node) walldoor;
    
    // Load the door model
    Spatial door = assetManager.loadModel("Models/Door/door.j3o");
    
    // Load Elevator Model
    Spatial elevator = assetManager.loadModel("Models/Elevator/Elevator.j3o");
    Node elevatorNode = (Node) elevator;
    Spatial elevatorDoor = assetManager.loadModel("Models/Elevator/ElevatorDoors.j3o");
    
    // Load Poop
    Spatial poop = assetManager.loadModel("Models/Items/toiletpoop.j3o");
    poop.setName("Poop");
    ObjectControl poopControl = new ObjectControl(3.0f);
    poop.addControl(poopControl);
    
    // Load Cake
    Spatial cake = assetManager.loadModel("Models/Items/CAFETERIAcake.j3o");
    cake.setName("Cake");
    ObjectControl cakeControl = new ObjectControl(1.0f);
    cake.addControl(cakeControl);
    
    // load Angel
    Spatial angel = assetManager.loadModel("Models/Angel/scene.gltf");
    classroom.attachChild(angel);
    angel.setLocalScale(100f); 
    angel.setName("angel");
    ObjectControl angelControl = new ObjectControl(1.0f);
    angel.addControl(angelControl);

    // Attach the walls and floor model to the classroom node
    classroom.attachChild(door);
    classroom.attachChild(wallsfloor);
    classroom.attachChild(walldoor);
    classroom.attachChild(poop);
    classroom.attachChild(cake);
    walldoorNode.attachChild(door);
    walldoorNode.attachChild(elevator);
    walldoorNode.attachChild(elevatorDoor);
    
    // Now attach the entire classroom node (with both models) to the rootNode
    rootNode.attachChild(classroom);
    
    // Move or rotate the wallsfloor & door
    wallsfloor.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.PI / 2, Vector3f.UNIT_Y)); 
    wallsfloor.setLocalTranslation(-1.2f, 0, 0); // Adjust position if needed
    walldoor.setLocalTranslation(-2, 1.1f, -4.7f);
    door.setLocalTranslation(0, -0.25f, 0);
    walldoorNode.setLocalScale(1.1f);
    elevatorNode.setLocalTranslation(0, 0, -3);
    elevatorDoor.setLocalTranslation(0, -3, -3);
    cake.setLocalTranslation(0, 0.1f, -1);
    cake.setLocalScale(2.0f);
    
    // Initialize the HUD text for showing item notifications
    notificationText = new BitmapText(guiFont, false);
    notificationText.setSize(guiFont.getCharSet().getRenderedSize());
    notificationText.setText("Press F to interact");
    notificationText.setColor(ColorRGBA.Red);
    guiNode.attachChild(notificationText);
    
    gameState = new GameState(cam, inputManager, notificationText);
    stateManager.attach(gameState);
    
    // Initialize the UserInput class with the notification text
    userInput = new UserInput(cam, inputManager, gameState, notificationText);

    // Add items to the pickable list
    gameState.addPickableItem(poop);
    gameState.addPickableItem(cake);
    
    
    // Create and add a crosshair (point) in the center of the screen
    createCrosshair();
    
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
    classroomLight.setColor(ColorRGBA.Gray.mult(0.8f));
    classroom.addLight(classroomLight);
    
    DirectionalLight classroomLight2 = new DirectionalLight();
    classroomLight2.setDirection(new Vector3f(-1, -1, 0));
    classroomLight2.setColor(ColorRGBA.Gray.mult(0.8f));
    classroom.addLight(classroomLight2);
   
    DirectionalLight classroomLight3 = new DirectionalLight();
    classroomLight3.setDirection(new Vector3f(0, -1, 1));
    classroomLight3.setColor(ColorRGBA.Gray.mult(0.8f));
    classroom.addLight(classroomLight3);
    
    /*
    // Elevator Model Material for test
    Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
    mat.setBoolean("UseMaterialColors", true);
    mat.setColor("Diffuse", ColorRGBA.White);
    mat.setColor("Specular", ColorRGBA.White);
    mat.setFloat("Shininess", 15f);  // Adjust shininess for reflective surfaces
    elevator.setMaterial(mat);
    */
    
    // Constrain the camera to walk in the room
    // Disable the default fly camera to implement custom walking
    flyCam.setEnabled(true);
    // Set the initial camera position and direction
    cam.setLocation(new Vector3f(0, 1.75f, 0)); // Set initial position
    cam.lookAtDirection(new Vector3f(0, 0, -1), Vector3f.UNIT_Y); // Set initial direction

    // Enable FlyCam for rotation
    // flyCam.setMoveSpeed(0);  // Disable FlyCam movement, we'll handle custom movement

    // UI icons
    // To load the save/load icon in top left corner
    Picture frame = new Picture("User interface frame");
    // Enable alpha blending for frame (save icon)
    frame.setImage(assetManager, "Interface/save.png", false); 
    float iconWidth = 52; // this is arbitary
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
}

    /**
     * Method to create a point-based crosshair and adds it to the GUI node. 
     */
    private void createCrosshair() {
        crosshair = new BitmapText(guiFont, false);
        crosshair.setSize(guiFont.getCharSet().getRenderedSize()); // Set size to the font size
        crosshair.setText("+"); // This is the crosshair (you can use "." for a dot as well)
        crosshair.setColor(ColorRGBA.White); // Set the crosshair color to white or another color

        // Calculate the center of the screen and adjust for the crosshair size
        float x = (cam.getWidth() / 2) - (crosshair.getLineWidth() / 2);
        float y = (cam.getHeight() / 2) + (crosshair.getLineHeight() / 2);
        crosshair.setLocalTranslation(x, y, 0); // Set the position of the crosshair

        // Attach the crosshair to the GUI node so it stays in the HUD
        guiNode.attachChild(crosshair);
    }
    
}
