package com.game.www;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Ammo {
	Image ammo = new ImageIcon("src/images/Ammo.png").getImage();
	int ammo_X, ammo_Y;
	final int  SPEED = 1, AMMO_WIDTH = 30, AMMO_HEIGHT = 70;
	Game game;

	public Ammo(int ME_X, int ME_Y) {
		this.ammo_X = ME_X;
		this.ammo_Y = ME_Y;
	}
	public void move() {
		ammo_Y -= SPEED;
	}
	public void drawAmmo(Graphics g) {
		g.drawImage(ammo, ammo_X, ammo_Y, AMMO_WIDTH, AMMO_HEIGHT, game);
	}
	public Rectangle getRectAmmo() {
		return new Rectangle(ammo_X, ammo_Y, AMMO_WIDTH-10, AMMO_HEIGHT-10);
	}
}