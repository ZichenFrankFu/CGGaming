package mygame;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.math.Vector3f;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import com.jme3.system.AppSettings;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class CubeChaserControl extends AbstractControl {
    
    private Ray ray = new Ray();
    private final Camera cam;
    private final Node rootNode;
    
    public CubeChaserControl(Camera cam, Node rootNode) {
        this.cam = cam;
        this.rootNode = rootNode;
    }
    
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
    public String hello(){
        return "Hello, my name is "+spatial.getName();
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        spatial.rotate(tpf, tpf, tpf);
    }
}
