package chunks;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import cube.Block;

public class Chunk {
	
	public Chunk(List<Block> blocks, Vector3f origin) {
		this.blocks = blocks;
		this.origin = origin;
	}


	public List<Block> blocks;
	public Vector3f origin;
}
