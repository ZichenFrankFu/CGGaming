/**
 * Authors: Jitong Xian, Xinming Shen, Zichen Fu
 * Main class of game. 
 */


package mygame;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.FogFilter;
import com.jme3.post.filters.LightScatteringFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Spatial;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Terrain extends SimpleApplication {
    
    private FilterPostProcessor fpp;
    private FogFilter fogFilter;
    private Vector3f lightDir = new Vector3f(-0.39f, -0.32f, -0.74f);
    private LightScatteringFilter sunLightFilter;

    public static void main(String[] args) {
        Terrain app = new Terrain ();
        app.start(); 
    }

    @Override
    public void simpleInitApp() {
        //Add Terrain
        Spatial terrainGeo = assetManager.loadModel("Scenes/room_3.j3o");
        rootNode.attachChild(terrainGeo);
        
        //Add Trees
        Spatial tree1 = assetManager.loadModel("Models/Tree/Tree.j3o");
        tree1.scale(10); 
        tree1.setQueueBucket(Bucket.Transparent);
        rootNode.attachChild(tree1);
        Vector3f treeLoc1 = new Vector3f(0,0,0);
        treeLoc1.setY( terrainGeo.getLocalTranslation().getY() );
        tree1.setLocalTranslation(treeLoc1);
        
        Spatial tree2 = tree1.clone();
        rootNode.attachChild(tree2);
        Vector3f treeLoc2 = new Vector3f(30,0,30);
        treeLoc1.setY( terrainGeo.getLocalTranslation().getY() );
        tree2.setLocalTranslation(treeLoc2);
        
        //Add Sky
        Spatial mySky = assetManager.loadModel("Scenes/mySky.j3o");
        rootNode.attachChild(mySky);
        
        //Set Camera
        cam.setLocation(new Vector3f(100, 10, 0));
        flyCam.setMoveSpeed(100);
        
        //Add Fog
        fpp = new FilterPostProcessor(assetManager);
        viewPort.addProcessor(fpp);
        
        fogFilter = new FogFilter();
        fogFilter.setFogDistance(200);
        fogFilter.setFogDensity(1.0f);
        fpp.addFilter(fogFilter);
        fogFilter.setFogColor(new ColorRGBA(0.9f, 0.9f, 0.9f, 1.0f));
        
        //Add Sunlight
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(lightDir);
        sun.setColor(ColorRGBA.White.clone().multLocal(3));
        rootNode.addLight(sun);
        sunLightFilter = new LightScatteringFilter(lightDir.mult(-3000));
        fpp.addFilter(sunLightFilter);
        cam.lookAtDirection(lightDir.negate(),Vector3f.UNIT_Y);
    }
    
    @Override
    public void simpleUpdate(float tpf) {

    }

    @Override
    public void simpleRender(RenderManager rm) {

    }
    
    
}
