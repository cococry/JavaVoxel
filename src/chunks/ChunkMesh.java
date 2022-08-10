package chunks;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import cube.Block;
import cube.Vertex;
import models.CubeModel;

public class ChunkMesh {

	private List<Vertex> vertices;
	private List<Float> positionsList;
	private List<Float> uvsList;
	private List<Float> normalsList;
	
	public float[] positions, uvs, normals;
	
	public Chunk chunk;
	
	public ChunkMesh(Chunk chunk) {
		this.chunk = chunk;
		
		vertices = new ArrayList<Vertex>();
		positionsList = new ArrayList<Float>();
		uvsList = new ArrayList<Float>();
		normalsList = new ArrayList<Float>();
		
		buildMesh();
		populateLists();
	}
	
	public void update(Chunk chunk) {
		this.chunk = chunk;
		
		buildMesh();
		populateLists();
		
	}
	
	private void buildMesh() {
		
		for(int i = 0; i < chunk.blocks.size(); i++) {
			Block blockI = chunk.blocks.get(i);
			
			boolean px = false, nx = false, py = false, ny = false, pz = false, nz = false;
			
			for(int j = 0; j < chunk.blocks.size(); j++) {
				Block blockJ = chunk.blocks.get(j);
				
				if(((blockI.x + 1) == (blockJ.x)) && ((blockI.y) == (blockJ.y)) && ((blockI.z) == (blockJ.z))) {
					px = true;
				}
				if(((blockI.x - 1) == (blockJ.x)) && ((blockI.y) == (blockJ.y)) && ((blockI.z) == (blockJ.z))) {
					nx = true;
				}
				if(((blockI.x) == (blockJ.x)) && ((blockI.y + 1) == (blockJ.y)) && ((blockI.z) == (blockJ.z))) {
					py = true;
				}
				if(((blockI.x) == (blockJ.x)) && ((blockI.y - 1) == (blockJ.y)) && ((blockI.z) == (blockJ.z))) {
					ny = true;
				}
				if(((blockI.x) == (blockJ.x)) && ((blockI.y) == (blockJ.y)) && ((blockI.z + 1) == (blockJ.z))) {
					pz = true;
				}
				if(((blockI.x) == (blockJ.x)) && ((blockI.y) == (blockJ.y)) && ((blockI.z - 1) == (blockJ.z))) {
					nz = true;
				}
			}
			
			if(!px) {
				for(int k = 0; k < 6; k++) {
					vertices.add(new Vertex(new Vector3f(CubeModel.PX_POS[k].x + blockI.x, CubeModel.PX_POS[k].y + blockI.y, CubeModel.PX_POS[k].z + blockI.z), CubeModel.UV_PX[(blockI.type * 6) + k], CubeModel.NORMALS[k]));
				}
			}
			if(!nx) {
				for(int k = 0; k < 6; k++) {
					vertices.add(new Vertex(new Vector3f(CubeModel.NX_POS[k].x + blockI.x, CubeModel.NX_POS[k].y + blockI.y, CubeModel.NX_POS[k].z + blockI.z), CubeModel.UV_NX[(blockI.type * 6) + k], CubeModel.NORMALS[k]));
				}
			}
			if(!py) {
				for(int k = 0; k < 6; k++) {
					vertices.add(new Vertex(new Vector3f(CubeModel.PY_POS[k].x + blockI.x, CubeModel.PY_POS[k].y + blockI.y, CubeModel.PY_POS[k].z + blockI.z), CubeModel.UV_PY[(blockI.type * 6) + k], CubeModel.NORMALS[k]));
				}
			}
			if(!ny) {
				for(int k = 0; k < 6; k++) {
					vertices.add(new Vertex(new Vector3f(CubeModel.NY_POS[k].x + blockI.x, CubeModel.NY_POS[k].y + blockI.y, CubeModel.NY_POS[k].z + blockI.z), CubeModel.UV_NY[(blockI.type * 6) + k], CubeModel.NORMALS[k]));
				}
			}
			if(!pz) {
				for(int k = 0; k < 6; k++) {
					vertices.add(new Vertex(new Vector3f(CubeModel.PZ_POS[k].x + blockI.x, CubeModel.PZ_POS[k].y + blockI.y, CubeModel.PZ_POS[k].z + blockI.z), CubeModel.UV_PZ[(blockI.type * 6) + k], CubeModel.NORMALS[k]));
				}
			}
			if(!nz) {
				for(int k = 0; k < 6; k++) {
					vertices.add(new Vertex(new Vector3f(CubeModel.NZ_POS[k].x + blockI.x, CubeModel.NZ_POS[k].y + blockI.y, CubeModel.NZ_POS[k].z + blockI.z), CubeModel.UV_NZ[(blockI.type * 6) + k], CubeModel.NORMALS[k]));
				}
			}
			
		}
	}

	private void populateLists() {
		
		for(int i = 0; i < vertices.size(); i++) {
			positionsList.add(vertices.get(i).positions.x);
			positionsList.add(vertices.get(i).positions.y);
			positionsList.add(vertices.get(i).positions.z);
			uvsList.add(vertices.get(i).uvs.x);
			uvsList.add(vertices.get(i).uvs.y);
			normalsList.add(vertices.get(i).normals.x);
			normalsList.add(vertices.get(i).normals.y);
			normalsList.add(vertices.get(i).normals.z);
		}
		
		positions = new float[positionsList.size()];
		uvs = new float[uvsList.size()];
		normals = new float[normalsList.size()];
		
		for(int i = 0; i < positionsList.size(); i++) {
			positions[i] = positionsList.get(i);
		}
		for(int i = 0; i < uvsList.size(); i++) {
			uvs[i] = uvsList.get(i);
		}
		for(int i = 0; i < normalsList.size(); i++) {
			normals[i] = normalsList.get(i);
		}
		
		positionsList.clear();
		uvsList.clear();
		normalsList.clear();
	}
}