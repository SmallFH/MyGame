package com.game.www;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Side extends Thread {
	boolean listen_Left, listen_Right, listen_Space;
	Game game;
	Image side = new ImageIcon("src/images/Side.png").getImage();
	int player_X = 0, player_Y = 563,live = 3;
	final int ME_WIDTH = 70, ME_HEIGHT = 100, SPEED = 3;

	public void drawImage(Graphics g) {
		g.drawImage(side, player_X, player_Y, ME_WIDTH, ME_HEIGHT, game);
	}
	public Rectangle getRectSide() {
		return new Rectangle(player_X, player_Y, ME_WIDTH-10, ME_HEIGHT-10);
	}
	public void run() {
		while (game.gameStart) {
			game.sid.move();
			try {
				sleep(1000);
			} catch (Exception e) {

			}
		}
	}
	public void move() {
		if (listen_Left) {
			player_X -= SPEED;
		} else if (listen_Right) {
			player_X += SPEED;
		}
	}
}