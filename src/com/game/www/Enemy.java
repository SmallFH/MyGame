package com.game.www;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Enemy {
	Image enemy = new ImageIcon("src/images/Enemy.png").getImage();
	int enemy_X, enemy_Y;
	final int SPEED = 1, ENEMY_WIDTH = 100, ENEMY_HEIGHT = 100;
	Game game;

	public Enemy(int enemy_X, int enemy_Y) {
		this.enemy_X = enemy_X;
		this.enemy_Y = enemy_Y;
	}
	public void drawEnemy(Graphics g) {
		enemy_Y += SPEED;
		g.drawImage(enemy, enemy_X, enemy_Y, ENEMY_WIDTH, ENEMY_HEIGHT, game);
	}
	public Rectangle getRectEnemy() {
		return new Rectangle(enemy_X, enemy_Y, ENEMY_WIDTH - 10, ENEMY_HEIGHT - 10);
	}
}