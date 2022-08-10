package shaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public abstract class ShaderProgram {
	
	int programID;
	int vertexShaderID, fragmentShaderID;
	
	FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	public ShaderProgram(String vertexFile, String fragmentFile) {
		programID = GL20.glCreateProgram();
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		
		
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		
		getAllUniformLocations();
	}
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(String name, int attribute) {
		GL20.glBindAttribLocation(programID, attribute, name);
	}
	
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String varName) {
		return GL20.glGetUniformLocation(programID, varName);
	}
	
	protected void loadFloat(String varName, float value) {
		GL20.glUniform1f(getUniformLocation(varName), value);
	}
	
	protected void loadVector2f(String varName, Vector2f value) {
		GL20.glUniform2f(getUniformLocation(varName), value.x, value.y);
	}
	
	protected void loadVector3f(String varName, Vector3f value) {
		GL20.glUniform3f(getUniformLocation(varName), value.x, value.y, value.z);
	}
	
	protected void loadVector4f(String varName, Vector4f value) {
		GL20.glUniform4f(getUniformLocation(varName), value.x, value.y, value.z, value.w);
	}
	
	protected void loadMatrix4f(String varName, Matrix4f value) {
		value.store(matrixBuffer);
		matrixBuffer.flip();
		
		GL20.glUniformMatrix4(getUniformLocation(varName), false, matrixBuffer);
	}
	
	protected void loadBoolean(String varName, boolean value) {
		
		int shaderValue = (value == true) ? 1 : 0;
		
		GL20.glUniform1i(getUniformLocation(varName), shaderValue);
	}
	
	public void bind() {
		GL20.glUseProgram(programID);
	}
	
	public void unbind() {
		GL20.glUseProgram(0);
	}
	
	public void cleanUp() {
		unbind();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		
		GL20.glDeleteProgram(programID);
	}
	
	private int loadShader(String file, int type) {
		
		StringBuilder shaderSource = new StringBuilder();
		
		InputStream in = ShaderProgram.class.getResourceAsStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		String line;
		try {
			while((line = reader.readLine()) != null) {
				shaderSource.append(line).append("//\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to load shader file!");
			System.exit(-1);
		}
		
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 1000));
			String shaderNameStr = (type == GL20.GL_VERTEX_SHADER) ? "vertex" : "fragment";
			System.err.print("Failed to compile ");
			System.err.print(shaderNameStr);
			System.err.print(" shader");
			System.exit(-1);
		}
		
		return shaderID;
	}
}
