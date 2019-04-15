package com.game.www;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Award {
	Image award = new ImageIcon("src/images/Award.png").getImage();
	int award_X, award_Y;
	final int SPEED = 1, AWARD_WIDTH = 100, AWARD_HEIGHT = 100;
	Game game;

	public Award(int award_X, int award_Y) {
		this.award_X = award_X;
		this.award_Y = award_Y;
	}
	public void drawAward(Graphics g) {
		award_Y += SPEED;
		g.drawImage(award, award_X, award_Y, AWARD_WIDTH, AWARD_HEIGHT, game);
	}
	public Rectangle getRectAward() {
		return new Rectangle(award_X, award_Y, AWARD_WIDTH, AWARD_HEIGHT);
	}
}