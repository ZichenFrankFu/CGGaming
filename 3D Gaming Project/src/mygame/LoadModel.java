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

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class LoadModel extends SimpleApplication {

    public static void main(String[] args) {
        LoadModel app = new LoadModel();
        app.start();
    }
                                
    @Override
public void simpleInitApp() {
    //viewPort.setBackgroundColor(ColorRGBA.White);

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
    
    /*
    // Ensure the model materials are configured correctly for lighting
    Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
    mat.setBoolean("UseMaterialColors", true);
    mat.setColor("Diffuse", ColorRGBA.White);
    mat.setColor("Specular", ColorRGBA.White);
    mat.setFloat("Shininess", 15f);  // Adjust shininess for reflective surfaces
    elevator.setMaterial(mat);
    */
    
    // Adjust the camera movement speed
    flyCam.setMoveSpeed(10); // Increase or decrease the value to adjust speed
}

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}