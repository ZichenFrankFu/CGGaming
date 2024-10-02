package mygame;


import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class ObjectControl extends AbstractControl {
    
    private float rotationSpeed = 1.0f;

     // Constructor for setting rotation speed if needed
    public ObjectControl(float speed) {
        this.rotationSpeed = speed;
    }

    @Override
    protected void controlUpdate(float tpf) {
        // Rotate the cake model along the Y-axis
        if (spatial != null) {
            spatial.rotate(0, rotationSpeed * tpf, 0);  // Rotate around Y-axis
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        // No custom rendering logic required for this control
    }

    @Override
    public ObjectControl cloneForSpatial(Spatial spatial) {
        ObjectControl control = new ObjectControl(rotationSpeed);
        control.setSpatial(spatial);
        return control;
    }
    
    public void simpleInitApp() {
        
    }
    
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
}
