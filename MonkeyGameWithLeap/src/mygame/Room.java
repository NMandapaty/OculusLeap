package mygame;

import oculusvr.app.OVRApplication;

import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 * test
 * @author normenhansen
 */
public class Room extends OVRApplication {
	
	private static Room app;

    public static void main(String[] args) {
        app = new Room();
        app.configureOVRApp(false, false, true);
        app.start();
    }
    
    //Dimensions
    private static final float ROOM_X= 4f;
    private static final float ROOM_Y= 1f;
    private static final float ROOM_Z= 3f;
    private static final float THIN = 0.1f;
    
    //Geometries
    private static final Box FloorAndCeiling;
    private static final Box LRWalls; //The left and right walls
    private static final Box FBWalls; //The front and back walls
    
    //Materials
    private Material floorMat;
    private Material ceilingMat;
    private Material wallMat;
    
    //Material sources
    private static final String floorTexture= "Textures/Terrain/Pond/Pond.jpg";
    private static final String wallTexture= "Textures/Terrain/BrickWall/BrickWall.jpg";
    private static final String unshaded = "Common/MatDefs/Misc/Unshaded.j3md";
    
    //Initialize constants
    static {
        FloorAndCeiling = new Box(ROOM_X, THIN, ROOM_Z);
        LRWalls = new Box(THIN, ROOM_Y, ROOM_Z);
        FBWalls = new Box(ROOM_X, ROOM_Y, THIN);
    }

    @Override
    public void simpleInitApp() {
    	super.simpleInitApp();
        cam.setLocation(Vector3f.ZERO);
        
        //Oculus Rift
        //Basic HMD Sensing
        Spatial observer = new Node("Observer");
        observer.addControl(app.getOVRAppState().getCameraControl());
        rootNode.attachChild(observer);
        
        //Initialize materials
        floorMat = new Material(assetManager, unshaded);
        ceilingMat = new Material(assetManager, unshaded);
        wallMat = new Material(assetManager, unshaded); 
        
        //Initialize textures
        TextureKey wall_key = new TextureKey(wallTexture);
        wall_key.setGenerateMips(true);
        Texture tex = assetManager.loadTexture(wall_key);
        wallMat.setTexture("ColorMap", tex);

        TextureKey floor_key = new TextureKey(floorTexture);
        floor_key.setGenerateMips(true);
        Texture tex3 = assetManager.loadTexture(floor_key);
        tex3.setWrap(Texture.WrapMode.Repeat);
        floorMat.setTexture("ColorMap", tex3);
        
        ceilingMat.setColor("Color", ColorRGBA.randomColor());
        
        //Initialize geometries
        Geometry right_wall = new Geometry("right wall", LRWalls);
        right_wall.setMaterial(wallMat);
        right_wall.setLocalTranslation(ROOM_X, 0, 0);
        
        Geometry left_wall = new Geometry("left wall", LRWalls);
        left_wall.setMaterial(wallMat);
        left_wall.setLocalTranslation(- ROOM_X, 0, 0);
        
        Geometry front_wall = new Geometry("front wall", FBWalls);
        front_wall.setMaterial(wallMat);
        front_wall.setLocalTranslation(0, 0, ROOM_Z);
        
        Geometry back_wall = new Geometry("back wall", FBWalls);
        back_wall.setMaterial(wallMat);
        back_wall.setLocalTranslation(0, 0, - ROOM_Z);
        
        Geometry floor = new Geometry("floor", FloorAndCeiling);
        floor.setMaterial(floorMat);
        floor.setLocalTranslation(0, - ROOM_Y, 0);
        
        Geometry ceiling = new Geometry("ceiling", FloorAndCeiling);
        ceiling.setMaterial(ceilingMat);
        ceiling.setLocalTranslation(0, ROOM_Y, 0);
        
        //Add all geometries to rootNode
        rootNode.attachChild(right_wall);
        rootNode.attachChild(left_wall);
        rootNode.attachChild(front_wall);
        rootNode.attachChild(back_wall);
        rootNode.attachChild(floor);
        rootNode.attachChild(ceiling);
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
