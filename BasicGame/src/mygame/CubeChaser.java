package mygame;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.math.Vector3f;
import com.jme3.math.Ray;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class CubeChaser extends SimpleApplication {
    
    private static Box mesh = new Box(Vector3f.ZERO, 1, 1, 1);
    private Geometry scaredCube;
    private Ray ray = new Ray();

    public static void main(String[] args) {
        
        TargetPickCursor app = new TargetPickCursor();
    }
                      
    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(100f);
        CubeChaserState state = new CubeChaserState();
        stateManager.attach(state);
    }   
    
    @Override
    public void simpleUpdate(float tpf) {
        System.out.println("Chase counter: " + stateManager.getState(CubeChaserState.class).
        getCounter());
    }
    
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
