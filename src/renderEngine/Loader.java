package renderEngine;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.RawModel;

public class Loader {

	static List<Integer> vaos = new ArrayList<Integer>();
	static List<Integer> vbos = new ArrayList<Integer>();
	static List<Integer> textures = new ArrayList<Integer>();
	
	public RawModel loadToVAO(float[] vertices, float[] uv) {
		int vaoID = createVAO();
		storeDataInAttributeList(vertices,  0, 3);
		storeDataInAttributeList(uv,  1, 2);
		GL30.glBindVertexArray(0);
		return new RawModel(vaoID, vertices.length);
	}
	
	public RawModel loadToVAO(float[] vertices, float[] uv, int[] indices) {
		int vaoID = createVAO();
		storeDataInAttributeList(vertices,  0, 3);
		storeDataInAttributeList(uv,  1, 2);
		createIBO(indices);
		GL30.glBindVertexArray(0);
		return new RawModel(vaoID, indices.length);
	}
	
	public int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		vaos.add(vaoID);
		
		return vaoID;
	}
	
	private void createIBO(int[] indices) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = createIntBufferFromArray(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	public void storeDataInAttributeList(float[] data, int attrbuteNumber, int dimension) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = createFloatBufferFromArray(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attrbuteNumber, dimension, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	
	}
	
	public int loadTexture(String fileName) {
		
		
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", Loader.class.getResourceAsStream("/res/" + fileName + ".PNG"));
			texture.setTextureFilter(GL11.GL_NEAREST);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		textures.add(textureID);
		
		return textureID;
	}
	
	private FloatBuffer createFloatBufferFromArray(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	private IntBuffer createIntBufferFromArray(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	public void cleanUp() {
		for(int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		for(int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for(int texture : textures) {
			GL11.glDeleteTextures(texture);
		}
	}

}
