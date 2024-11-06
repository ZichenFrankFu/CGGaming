package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;


class SceneSwitcherExample extends SimpleApplication {

    private SceneSwitchingManager sceneManager;

    public static void main(String[] args) {
        SceneSwitcherExample app = new SceneSwitcherExample();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        // Register the assets folder to ensure models can be loaded properly
        assetManager.registerLocator("assets/", FileLocator.class);

        // Initialize Scene Manager
        sceneManager = new SceneSwitchingManager(this);
        stateManager.attach(sceneManager);

        // Load the classroom scene
        Node classroomScene = new Node("Classroom Scene");
        Spatial classroom = assetManager.loadModel("Models/Anime_class_room/scene.gltf");
        classroomScene.attachChild(classroom);
        sceneManager.addScene(classroomScene);

        // Load the blackhole scene
        Node blackholeScene = new Node("Blackhole Scene");
        Spatial blackhole = assetManager.loadModel("Models/Blackhole/scene.j3o");
        blackholeScene.attachChild(blackhole);
        sceneManager.addScene(blackholeScene);

        // Load the initial scene
        sceneManager.switchToNextScene();

        // Add input mapping for switching scenes
        inputManager.addMapping("SwitchScene", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(switchSceneListener, "SwitchScene");
    }

    private final ActionListener switchSceneListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("SwitchScene") && isPressed) {
                sceneManager.switchToNextScene();
            }
        }
    };
}
