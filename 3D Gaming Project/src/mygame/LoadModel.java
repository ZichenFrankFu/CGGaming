package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
//import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.ui.Picture;


/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class LoadModel extends SimpleApplication {
    
    // Define room boundaries (adjust based on your room model's dimensions)
    private Vector3f roomMinBound = new Vector3f(-10f, 0f, -10f); // Min corner of the room
    private Vector3f roomMaxBound = new Vector3f(10f, 5f, 10f);   // Max corner of the room


    public static void main(String[] args) {
        LoadModel app = new LoadModel();
        app.start();
    }
                                
    @Override
public void simpleInitApp() {
    
    // Create a node for the classroom
    Node classroom = new Node("Classroom");

    // Load the classroom demo scene model
    Spatial classroomDemo = assetManager.loadModel("Models/classroom_demoscene.j3o");
    
    // Attach the classroom demo model to the classroom node
    classroom.attachChild(classroomDemo);
    
    // Load the walls and floor model
    Spatial wallsfloor = assetManager.loadModel("Models/CAFETERIAwallfloor.j3o");
    
    // Load the walldoor model
    Spatial walldoor = assetManager.loadModel("Models/walldoor_001.j3o");
    classroom.attachChild(walldoor);
    Node walldoorNode = (Node) walldoor;
    
    // Load the door model
    Spatial door = assetManager.loadModel("Models/door.j3o");
    
    // Load Elevator Model
    Spatial elevator = assetManager.loadModel("Models/Elevator.j3o");
    Node elevatorNode = (Node) elevator;
    Spatial elevatorDoor = assetManager.loadModel("Models/ElevatorDoors.j3o");
    
    // Attach the walls and floor model to the classroom node
    classroom.attachChild(door);
    classroom.attachChild(wallsfloor);
    classroom.attachChild(walldoor);
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
    // Ensure the model materials are configured correctly for lighting
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
    cam.setLocation(new Vector3f(0, 1.75f, 0)); // initialize camera position to be in the room
    Vector3f direction = new Vector3f(0, -0.5f, -1);  // Looking forward along the negative Z-axis
    Vector3f up = Vector3f.UNIT_Y;  // Y-axis is up
    
    // Set the camera's look direction
    cam.lookAtDirection(direction, up);


    // Set up custom key mappings for camera movement
    inputManager.addMapping("MoveForward", new KeyTrigger(KeyInput.KEY_W));
    inputManager.addMapping("MoveBackward", new KeyTrigger(KeyInput.KEY_S));
    inputManager.addMapping("MoveLeft", new KeyTrigger(KeyInput.KEY_A));
    inputManager.addMapping("MoveRight", new KeyTrigger(KeyInput.KEY_D));

    // Add listener for key mappings
    inputManager.addListener(actionListener, "MoveForward", "MoveBackward", "MoveLeft", "MoveRight");

    // flyCam.setMoveSpeed(10); // Increase or decrease the value to adjust speed
    
    // UI icons
    // To load the save/load icon in top left corner
        Picture frame = new Picture("User interface frame");
        frame.setImage(assetManager, "Interface/save.png", false); 
        float iconWidth = 52; // this is arbitary
        float iconHeight = 47;
        frame.setWidth(iconWidth);
        frame.setHeight(iconHeight);
        frame.setPosition(5, settings.getHeight() - iconHeight - 9);
        guiNode.attachChild(frame);
        
        Picture frame2 = new Picture("Button 2");
        frame2.setImage(assetManager, "Interface/load.png", false);
        frame2.setWidth(iconWidth);
        frame2.setHeight(iconHeight);
        frame2.setPosition(5 + iconWidth + 5, settings.getHeight() - iconHeight - 5);
        guiNode.attachChild(frame2);
}

private boolean forward = false, backward = false, left = false, right = false;
private float walkSpeed = 5.0f; // Movement speed
private float cameraHeight = 1.75f; // Fixed camera height for walking

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
        }
    }
};


@Override
public void simpleUpdate(float tpf) {
    // Create a vector for movement
    Vector3f walkDirection = new Vector3f(0, 0, 0);

    // Update direction based on the key inputs
    if (forward) {
        walkDirection.addLocal(cam.getDirection().mult(walkSpeed * tpf));
    }
    if (backward) {
        walkDirection.addLocal(cam.getDirection().mult(-walkSpeed * tpf));
    }
    if (left) {
        walkDirection.addLocal(cam.getLeft().mult(walkSpeed * tpf));
    }
    if (right) {
        walkDirection.addLocal(cam.getLeft().mult(-walkSpeed * tpf));
    }

    // Get current camera position
    Vector3f camPos = cam.getLocation();

    // Apply movement only in the XZ plane and keep the camera height (Y-axis) fixed
    camPos.addLocal(walkDirection.x, 0, walkDirection.z);
    camPos.y = cameraHeight; // Ensure the camera stays at the walking height

    // Optionally, clamp movement to room boundaries (if necessary)
    cam.setLocation(camPos);
}


    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
