package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.light.SpotLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.util.TangentBinormalGenerator;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class HoverTank extends SimpleApplication {
    
    private SpotLight spot;
    
    public static void main(String[] args) {
        TexturesOpaqueTransparent app = new TexturesOpaqueTransparent();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Node tank = (Node) assetManager.loadModel(
        "Models/HoverTank/Tank.j3o");
        
        Material mat = new Material(
        assetManager, "Common/MatDefs/Light/Lighting.j3md");
        
        TextureKey tankDiffuse = new TextureKey(
        "Models/HoverTank/tank_diffuse.jpg", false);
        mat.setTexture("DiffuseMap",
        assetManager.loadTexture(tankDiffuse));
        
        TangentBinormalGenerator.generate(tank);
        TextureKey tankNormal = new TextureKey(
        "Models/HoverTank/tank_normals.png", false);
        mat.setTexture("NormalMap",
        assetManager.loadTexture(tankNormal));
        
        TextureKey tankSpecular = new TextureKey(
        "Models/HoverTank/tank_specular.jpg", false);
        mat.setTexture("SpecularMap",
        assetManager.loadTexture(tankSpecular));
        
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        viewPort.addProcessor(fpp);
        BloomFilter bloom =
        new BloomFilter(BloomFilter.GlowMode.SceneAndObjects);
        fpp.addFilter(bloom);
        
        TextureKey tankGlow = new TextureKey(
        "Models/HoverTank/tank_glow_map.jpg", false);
        mat.setTexture("GlowMap",
        assetManager.loadTexture(tankGlow));
        mat.setColor("GlowColor", ColorRGBA.White);
        
        mat = assetManager.loadMaterial("Materials/tank.j3m");
        
        tank.setMaterial(mat);
        rootNode.attachChild(tank);
        
        // Add Lighting
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(5f));
        rootNode.addLight(ambient);
        
        PointLight lamp = new PointLight();
        lamp.setPosition(Vector3f.ZERO);
        lamp.setColor(ColorRGBA.Yellow);
        rootNode.addLight(lamp);
        
        spot = new SpotLight();
        spot.setSpotRange(100);
        spot.setSpotOuterAngle(20 * FastMath.DEG_TO_RAD);
        spot.setSpotInnerAngle(15 * FastMath.DEG_TO_RAD);
        rootNode.addLight(spot);
        
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, -1, -1).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        spot.setDirection(cam.getDirection());
        spot.setPosition(cam.getLocation());
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
