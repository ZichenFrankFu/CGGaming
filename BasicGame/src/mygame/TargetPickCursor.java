package mygame;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
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
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class TargetPickCursor extends SimpleApplication {
    
    private final static String MAPPING_COLOR = "Toggle Color";
    private final static String MAPPING_ROTATE = "Rotate";
    private Geometry geom;
    
    private final static Trigger TRIGGER_COLOR = new KeyTrigger(KeyInput.KEY_SPACE);
    
    private final static Trigger TRIGGER_ROTATE = new MouseButtonTrigger(MouseInput.BUTTON_LEFT);
    
    private final static Trigger TRIGGER_COLOR2 = new KeyTrigger(KeyInput.KEY_C);
    
    private static Box mesh = new Box(Vector3f.ZERO, 1, 1, 1);    

    public static void main(String[] args) {
        
        
    }
    
    public Geometry myBox(String name, Vector3f loc, ColorRGBA color)
    {
        Geometry geom = new Geometry(name, mesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);
        geom.setLocalTranslation(loc);
        return geom;
    }
    
    @Override
    public void simpleInitApp() {
        
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);    
        
        rootNode.attachChild(myBox("Red Cube", new Vector3f(0, 1.5f, 0), ColorRGBA.Red));
        rootNode.attachChild(myBox("Blue Cube", new Vector3f(0, -1.5f, 0), ColorRGBA.Blue));
        
        inputManager.addMapping(MAPPING_ROTATE, TRIGGER_ROTATE);
        inputManager.addListener(analogListener, new String[]{MAPPING_ROTATE});
        
        inputManager.addMapping(MAPPING_COLOR, TRIGGER_COLOR);
        inputManager.addMapping(MAPPING_ROTATE, TRIGGER_ROTATE);
        inputManager.addMapping(MAPPING_COLOR, TRIGGER_COLOR, TRIGGER_COLOR2);
        
        inputManager.addListener(actionListener,new String[]{MAPPING_COLOR});
        inputManager.addListener(analogListener,new String[]{MAPPING_ROTATE});
        
        inputManager.deleteMapping(INPUT_MAPPING_CAMERA_POS);
      
    }
    
    private AnalogListener analogListener = new AnalogListener() {
        
        public void onAnalog(String name, float intensity, float tpf) {

            if (name.equals(MAPPING_ROTATE)) {
                CollisionResults results = new CollisionResults();
                Vector2f click2d = inputManager.getCursorPosition();
                Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.getX(), click2d.getY()), 0f);
                Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.getX(), click2d.getY()), 1f).subtractLocal(click3d);
                Ray ray = new Ray(click3d, dir);
                rootNode.collideWith(ray, results);
                if (results.size() > 0) {
                    Geometry target = results.getClosestCollision().getGeometry();
                    if (target.getName().equals("Red Cube")) {
                        target.rotate(0, -intensity, 0); // rotate left
                    } else if (target.getName().equals("Blue Cube")) {
                        target.rotate(0, intensity, 0); // rotate right
                    }
                } else {
                    System.out.println("Selection: Nothing" );
                }
            }
        }
    };
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf)
        {
            if (name.equals(MAPPING_COLOR) && !isPressed) {
                geom.getMaterial().setColor("Color", ColorRGBA.randomColor());
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
