package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Entity;
import models.TexturedModel;
import shaders.StaticShader;

public class MasterRenderer {
	
	Matrix4f projectionMatrix;
	
	private static final float FOV = 70f;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 10000.0f;

	public StaticShader shader = new StaticShader();
	Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	
	public MasterRenderer() {
		createProjectionMatrix();
		shader.bind();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.unbind();

		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public void prepare() {
		GL11.glClearColor(0.4f, 0.7f, 1.0f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void renderIndexed(Entity entity) {
		EntityRenderer.renderIndexed(entity, shader);
	}
	public void renderArrays(Entity entity) {
		prepare();
		shader.bind();
		EntityRenderer.renderArrays(entity, shader);
		shader.unbind();
		
	}
	
	public void renderEntities(Camera camera) {
		prepare();
		shader.bind();
		shader.loadViewMatrix(camera);
		EntityRenderer.renderEntities(entities);
		shader.unbind();
		
		entities.clear();
		
	}
	
	public void addEntity(Entity entity) {
		TexturedModel model = entity.getModel();
		
		List<Entity> batch = entities.get(model);
		
		if(batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(model, newBatch);
		}
	}
	
public void createProjectionMatrix() {
		
		projectionMatrix = new Matrix4f();
		
		float aspect = (float)Display.getWidth() / (float)Display.getHeight();
		
		float yScale = (float) (1.0f / Math.tan(Math.toRadians(FOV / 2.0f)));
		float xScale = yScale / aspect;
		
		float zp = FAR_PLANE + NEAR_PLANE;
		float zm = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix.m00 = xScale;
		projectionMatrix.m11 = yScale;
		projectionMatrix.m22 = -zp / zm;
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -(2 * FAR_PLANE * NEAR_PLANE) / zm;
		projectionMatrix.m33 = 0;
		
	}
}
