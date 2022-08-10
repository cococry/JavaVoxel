package shaders;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import toolbox.Maths;

public class StaticShader extends ShaderProgram {
	private static final String vertexFile = "/shaders/vertexShader.txt";
	private static final String fragmentFile = "/shaders/fragmentShader.txt";

	
	public StaticShader() {
		super(vertexFile, fragmentFile);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute("position", 0);
		super.bindAttribute("uv", 1);
	}

	@Override
	protected void getAllUniformLocations() {

	}
	
	public void loadModelMatrix(Matrix4f matrix) {
		super.loadMatrix4f("uModel", matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix4f("uProjectionMatrix", matrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		super.loadMatrix4f("uViewMatrix", Maths.createViewMatrix(camera));
	}

}
