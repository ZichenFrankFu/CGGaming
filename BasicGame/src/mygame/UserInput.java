package mygame;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.math.Vector3f;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.scene.Node;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class UserInput extends SimpleApplication {
    
    private final static String MAPPING_COLOR = "Toggle Color";
    private final static String MAPPING_ROTATE = "Rotate";
    private Geometry geom;
    private final static Trigger TRIGGER_COLOR = new KeyTrigger(KeyInput.KEY_SPACE);
    private final static Trigger TRIGGER_ROTATE = new MouseButtonTrigger(MouseInput.BUTTON_LEFT);
    private final static Trigger TRIGGER_COLOR2 = new KeyTrigger(KeyInput.KEY_C);                         
    
    public static void main(String[] args) {
        
    }
    
    @Override
    public void simpleInitApp() {
        inputManager.addMapping(MAPPING_COLOR, TRIGGER_COLOR);
        inputManager.addMapping(MAPPING_ROTATE, TRIGGER_ROTATE);
        inputManager.addMapping(MAPPING_COLOR, TRIGGER_COLOR, TRIGGER_COLOR2);
        
        inputManager.addListener(actionListener,new String[]{MAPPING_COLOR});
        inputManager.addListener(analogListener,new String[]{MAPPING_ROTATE});
        
        inputManager.deleteMapping(INPUT_MAPPING_CAMERA_POS);

        //Starting Code: Create a blue cube
        Box b = new Box(1, 1, 1);
        geom = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);
        
        //Create a 2nd yellow cube
        Vector3f v = new Vector3f(2.0f , 1.0f , -3.0f);
        Box b2 = new Box(1, 1, 1);
        Geometry geom2 = new Geometry("Box", b2);
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Yellow);
        geom2.setMaterial(mat2);
        geom2.setLocalTranslation(v);
        rootNode.attachChild(geom2);
        
        //Scale the cubes
        geom.setLocalScale(0.5f);
        geom2.scale(2.0f);
        
        //Rotate the cubes
        float r = FastMath.DEG_TO_RAD * 45f;
        geom2.rotate(r, 0.0f, 0.0f);
        geom.rotate(0.0f, r, 0.0f);
        
        //Quaternion Rotation
        Quaternion roll045 = new Quaternion();
        roll045.fromAngleAxis(45*FastMath.DEG_TO_RAD , Vector3f.UNIT_X);
        geom2.setLocalRotation(roll045);
        
        //Get Camera Location
        Vector3f loc = cam.getLocation();
        Quaternion rot = cam.getRotation();
        
        //Nodes
        Node pivot = new Node("pivot node");
        pivot.attachChild(geom);
        pivot.attachChild(geom2);
        pivot.rotate(00, 0, FastMath.DEG_TO_RAD * 45);
        rootNode.attachChild(pivot);
        
    }
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf)
        {
            if (name.equals(MAPPING_COLOR) && !isPressed) {
                geom.getMaterial().setColor("Color", ColorRGBA.randomColor());
            }
        }
    };

    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float intensity, float tpf) 
        {
            if (name.equals(MAPPING_ROTATE)) {
                geom.rotate(0, intensity, 0); // rotate around Y axis
            }
        }
    };

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
