package com.app.instruction;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class ThinkBoxStudent {
	private int posX;
	private int posY;
	private int width;
	private int height;
	private int rodWidth;
	private Image image;
	private boolean doWeNeedToDisplayRodNumbers;

public ThinkBoxStudent() {}
	
	public ThinkBoxStudent(int posX, int posY, int width, int height) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
	}
	
	public void drawThink(Graphics g) {
		g.drawImage(image, this.posX, this.posY, this.getWidth(), this.getHeight(), null);
	}
	
	public void highlight(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(posX, posY, width, height);
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getRodWidth() {
		return rodWidth;
	}

	public void setRodWidth(int rodWidth) {
		this.rodWidth = rodWidth;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isDoWeNeedToDisplayRodNumbers() {
		return doWeNeedToDisplayRodNumbers;
	}

	public void setDoWeNeedToDisplayRodNumbers(boolean doWeNeedToDisplayRodNumbers) {
		this.doWeNeedToDisplayRodNumbers = doWeNeedToDisplayRodNumbers;
	}
}
