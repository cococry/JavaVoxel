package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Entity {
	
	TexturedModel model;
	Vector3f position;
	float rotX, rotY, rotZ;
	float scale;
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}

	public TexturedModel getModel() {
		return model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getRotX() {
		return rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public void increasePosition(float dX, float dY, float dZ) {
		this.position.x += dX;
		this.position.y += dY;
		this.position.z += dZ;
		
	}
	
	public void increaseRotation(float dX, float dY, float dZ) {
		this.rotX += dX;
		this.rotY += dY;
		this.rotZ += dZ;
	}
	
	public void increaseScale(float scale) {
		this.scale += scale;
	}
	
}
