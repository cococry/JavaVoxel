package blockGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import chunks.Chunk;
import chunks.ChunkMesh;
import cube.Block;
import entities.Camera;
import entities.Entity;
import models.CubeModel;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import textures.ModelTexture;
import toolbox.PerlinNoiseGenerator;

public class GameLoop {

	static List<ChunkMesh> chunks = Collections.synchronizedList(new ArrayList<ChunkMesh>());
	static Vector3f cameraPosition = new Vector3f(0.0f, 0.0f, 0.0f);
	static List<Vector3f> usedPositons = new ArrayList<Vector3f>();
	
	public static final int WORLD_SIZE = 5 * 32;
	
	static List<Entity> entities = new ArrayList<Entity>();
	
	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		MasterRenderer renderer = new MasterRenderer();
		
		Loader loader = new Loader();

		RawModel model = loader.loadToVAO(CubeModel.vertices, CubeModel.uv, CubeModel.indices);
		
		ModelTexture texture = new ModelTexture(loader.loadTexture("DefaultPack"));
		
		
		Camera camera = new Camera(new Vector3f(0.0f, 0.0f, 0.0f), 0, 0, 0);		
		
		PerlinNoiseGenerator generator = new PerlinNoiseGenerator();
		
		new Thread(new Runnable() {
			public void run() {
				while(!Display.isCloseRequested()) {
					for(int x = (int) (cameraPosition.x - WORLD_SIZE) / 32; x < (cameraPosition.x + WORLD_SIZE) / 32; x++) {
						for(int z = (int) (cameraPosition.z - WORLD_SIZE) / 32; z < (cameraPosition.z + WORLD_SIZE) / 32; z++) {
							if(!usedPositons.contains(new Vector3f(x * 32, 0 * 32, z * 32))) {
								
								List<Block> blocks = new ArrayList<Block>();
								
								for(int i = 0; i < 32; i++) {
									for(int j = 0; j < 32; j++) {
										int height = (int) generator.generateHeight(i + (x * 32), j + (z * 32));
										int type = Block.GRASS;
										
										if(height > 8) {
											type = Block.STONE;
										}
										blocks.add(new Block(i,  height, j, type));
									}
								}
								
								Chunk chunk = new Chunk(blocks, new Vector3f(x * 32, 0 * 32, z * 32)); 
								ChunkMesh mesh = new ChunkMesh(chunk);
								chunks.add(mesh);
								usedPositons.add(new Vector3f(x * 32, 0 * 32, z * 32));
							}
						}
					}		
				}
			}
		}).start();
		
		/*List<Block> blocks = new ArrayList<Block>();
		
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++) {
				for(int z = 0; z < 10; z++) {
					
					blocks.add(new Block(x, y, z, Block.TYPE.DIRT));
				}
			}
		}
		
		Chunk chunk = new Chunk(blocks, new Vector3f(0.0f, 0.0f, 0.0f));
		ChunkMesh mesh = new ChunkMesh(chunk);
		
		RawModel chunkModel = loader.loadToVAO(mesh.positions , mesh.uvs);
		TexturedModel chunkTexModel = new TexturedModel(chunkModel, texture);
		Entity entity = new Entity(chunkTexModel, new Vector3f(0.0f, 0.0f, 0.0f), 0.0f, 0.0f, 0.0f, 1.0f);
		
		*/
		
		int index = 0;
		while(!Display.isCloseRequested()) {
			camera.move();
			cameraPosition = camera.getPosition();
			
			if(index < chunks.size()) {
				
				RawModel chunkModel = loader.loadToVAO(chunks.get(index).positions , chunks.get(index).uvs);
				TexturedModel chunkTexModel = new TexturedModel(chunkModel, texture);
				Entity entity = new Entity(chunkTexModel, chunks.get(index).chunk.origin, 0.0f, 0.0f, 0.0f, 1.0f);
				entities.add(entity);
				
				chunks.get(index).positions = null;
				chunks.get(index).normals = null;
				chunks.get(index).uvs = null;
				index++;
				
			}
			for(int i = 0; i < entities.size(); i++) {
				Vector3f origin = entities.get(i).getPosition();
				
				
				int distX = (int) (cameraPosition.x - origin.x);
				int distZ = (int) (cameraPosition.z - origin.z);
				
				if(distX < 0) {
					distX = -distX;
				}
				if(distZ < 0) {
					distZ = -distZ;
				}
				
				if((distX <= WORLD_SIZE * 16) && (distZ <= WORLD_SIZE)) {
					renderer.addEntity(entities.get(i));
				}
			}
			
				
	
			renderer.renderEntities(camera);
			
			
			DisplayManager.updateDisplay();
			
		}
		
		DisplayManager.closeDisplay();
		
		loader.cleanUp();
		renderer.shader.cleanUp();
	}

}
