package cube;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Vertex {

	public Vector3f positions, normals;
	public Vector2f uvs;
	
	public Vertex(Vector3f positon, Vector2f uvs, Vector3f normals) {
		this.positions = positon;
		this.uvs = uvs;
		this.normals = normals;
	}
}
