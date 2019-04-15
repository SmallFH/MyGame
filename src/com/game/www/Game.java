package com.game.www;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;


/**
 * ��Ϸ�����е�һЩ����
 */
public class Game extends JPanel {
	JFrame jf; 
	Image im = new ImageIcon("src/images/GameFa.png").getImage();
	final int JF_WIDTH = 900, JF_HEIGHT = 700;
	int E = 10, etime = 100, enemy_X, enemy_Y;
	int A = 20, atime = 500;
	int score = 0,gameTime = 0;
	int victoryScore=120,victoryGameTime=100;
	Side sid = new Side();
	public ArrayList<Ammo> ammo = new ArrayList<>();
	Ammo am;
	public ArrayList<Enemy> enemy = new ArrayList<>();
	Enemy en;
	enemyThread ene = new enemyThread();
	public ArrayList<Award> awar = new ArrayList<>();
	Award aw;
	awardThread award = new awardThread();
	timeThread tim = new timeThread();
	boolean pause = false, no = false, gameStart = true, enemytime, awardtime;
	
	/**
	 * ���췽��ʵ�ִ��ڵĳ�ʼ��
	 */
	public Game() {
		jf = new JFrame("��ҫ����ս");
		jf.setSize(JF_WIDTH, JF_HEIGHT);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jf.addWindowListener(new window());
		jf.setResizable(false);
		// ���ڴ�С���ɸı�
		jf.setVisible(true);
		jf.addKeyListener(new myKey());
		jf.add(this);
		award.start();
		ene.start();
		tim.start();
	}

	/**
	 * ����һ���ڲ���ʵ�ִ��ڼ���
	 */
	class window extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			no = !no;
			int close = JOptionPane.showConfirmDialog(jf, "��ʿ������������˳���Ϸ��\n�ǵĻ��͵��yes\n���No�Ļ����Է�����Ϸż��", "�˳�or����",
					JOptionPane.YES_NO_OPTION);
			if (close == JOptionPane.YES_OPTION) {
				gameStart = false;
				jf.dispose();
				new GameFace();
			} else {
				no =! no;
			}
		}
	}

	/**
	 * ����һ���ڲ���ʵ�ּ��̵��¼�����
	 */
	class myKey extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if(!no) {
				if (!pause) {
					sid.player_X -= sid.SPEED;
				}
			}
				}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if(!no) {
				if (!pause) {
					sid.player_X += sid.SPEED;
				}
			}
			}
			
			else if(e.getKeyCode() == KeyEvent.VK_R) {
				no =! no;
				int re = JOptionPane.showConfirmDialog(jf, "��ʿ��������������¿�ʼ��Ϸ��\n�ǵĻ��͵��yes\n�뷵����Ϸ�͵��No��", "���棿",
						JOptionPane.YES_NO_OPTION);
				if (re == JOptionPane.YES_OPTION) {
					gameStart = false;
					jf.dispose();
					new Game();
				} else {
					no =! no;
			}
				}
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(!pause) {
					sid.listen_Left = true;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(!pause) {
					sid.listen_Right = true;
				}
				break;
			}
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if(!no) {
				if (!pause) {
					sid.player_X -= sid.SPEED;
				}
			}
				}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if(!no) {
				if (!pause) {
					sid.player_X += sid.SPEED;
				}
			}
				}
			else if (e.getKeyCode() == KeyEvent.VK_X) { 
				am = new Ammo(sid.player_X + 20, sid.player_Y);
				if(!no) {
				if (!pause) {
					ammo.add(am);
				}
			}
				}
			else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				pause = !pause;
			}
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				sid.listen_Left = false;
				break;
			case KeyEvent.VK_RIGHT:
				sid.listen_Right = false;
				break;
			}
		}
	}
	
	/**
	 * ���ʹ��������ظ�������Ϸ����
	 */
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(im, 0, 0, JF_WIDTH, JF_HEIGHT, this);
		sid.drawImage(g);
		for (int k = 0; k < ammo.size(); k++) {
			ammo.get(k).drawAmmo(g);
			ammo.get(k).move();
		}
		for (int i = 0; i < enemy.size(); i++) {
			enemy.get(i).drawEnemy(g);
		}
		for (int i = 0; i < awar.size(); i++) {
			awar.get(i).drawAward(g);
		}
		if (pause) {
			Font font1 = new Font("����", Font.BOLD, 50);
			Color c = g.getColor();
			g.setColor(Color.LIGHT_GRAY);
			g.setFont(font1);
			g.drawString("��Ϸ��ͣ", 320, 400);
			g.setColor(c);
		}
		drawScore(g);
		hitChick();
		sideMoveList();
		sid.move();
		if(!no) {
		if (!pause) {
			repaint();
		}
	}
		}

	/**
	 * ����Ϸ�������Ѷȡ�����ֵ
	 */
	private void drawScore(Graphics g) {
		int x = 10, y = 50;
		Font font1 = new Font("����", Font.BOLD, 30);
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.setFont(font1);
		g.drawString("��ɱ������Ŀ��" + score, x, y);
		Font font2 = new Font("����", Font.BOLD, 30);
		g.setColor(Color.white);
		g.setFont(font2);
		g.drawString("����" + sid.live, x, 2*y);
		g.setColor(Color.ORANGE);
		
		if (gameTime >= 0 && gameTime < 15) {
			g.drawString("��Ϸ�Ѷȣ�0", 10, 150);
		}
		if (gameTime >= 15 && gameTime < 30) {
			g.drawString("��Ϸ�Ѷȣ�1", 10, 150);
		}
		if (gameTime >= 30 && gameTime < 45) {
			g.drawString("��Ϸ�Ѷȣ�2", 10, 150);
		}
		if (gameTime >= 45 && gameTime < 60) {
			g.drawString("��Ϸ�Ѷȣ�3", 10, 150);
		}
		if (gameTime >= 60 && gameTime < 75) {
			g.drawString("��Ϸ�Ѷȣ�4", 10, 150);
		}
		if (gameTime >= 75) {
			g.drawString("��Ϸ�Ѷȣ�5", 10, 150);
		}
			Font fontGame = new Font("����", Font.BOLD, 30);
			g.setColor(Color.LIGHT_GRAY);
			g.setFont(fontGame);
			g.drawString("��Ϸʱ��:" + gameTime, x, 4*y);
		g.setColor(c);
		if(!no) {
		if (!pause) {
			repaint();
		}
	}
		}
	
	/**
	 * ��ײ���ķ���
	 */
	public void hitChick() {
		Boolean a = false;
		for (int j = 0; j < enemy.size(); j++) {
			for (int i = 0; i < ammo.size(); i++) {
				if (ammo.get(i).getRectAmmo().intersects(enemy.get(j).getRectEnemy())) {
					ammo.remove(i);
					a = true;
					score += 1;
					if (gameTime >= 15 && gameTime < 30) {
						etime = 80;
					}
					if (gameTime >= 30 && gameTime < 45) {
						etime = 60;
					}
					if (gameTime >= 45 && gameTime < 60) {
						etime = 40;
					}
					if (gameTime >= 60 && gameTime < 75) {
						etime = 20;
					}
					if(gameTime >= 75) {
						etime = 10;
					}
					if(!no) {
					if (score == victoryScore && gameTime < victoryGameTime) {
						no = !no;
						int clo = JOptionPane.showConfirmDialog(jf, "You are so ����������\n���������ĺ�ʱΪ��"+gameTime+"\n����������һ�ֺò��ã�\n����һ�־͵�Yes\n���No����������",
								"Congratulation", JOptionPane.YES_NO_OPTION);
						if (clo == JOptionPane.YES_OPTION) {
							jf.dispose();
							new Game();
						} else {
							jf.dispose();
							new GameFace();
						}
					}
					}
				}
			}
			if (a) {
				enemy.remove(j);
				a = false;
			}
		}
		for (int i = 0; i < enemy.size(); i++) {
			if (sid.getRectSide().intersects(enemy.get(i).getRectEnemy())) {
				sid.live--;
				enemy.remove(i);
				if(!no) {
				if (sid.live == 0) {
					no=!no;
					int res = JOptionPane.showConfirmDialog(jf, "��ʿ���ܱ�Ǹ��û�д������Ŀ�ꡣ\n�����ε�����ɱ�����ǣ�" + score + "\n��ʿ�������εĺ�ʱ�ǣ�"+gameTime + "\n�Ϳ���Ŀ�����ٳ���һ�ΰɣ�",
							"GameOver", JOptionPane.YES_NO_OPTION);
					if (res == JOptionPane.YES_OPTION) {
						jf.dispose();
						new Game();
					} else {
						jf.dispose();
						new GameFace();
					}
				}
				}
			}
		}
		for (int i = 0; i < awar.size(); i++) {
			if (awar.get(i).getRectAward().intersects(sid.getRectSide())) {
				sid.live += 1;
				if(sid.live>=5)
					sid.live = 5;
				awar.remove(i);
			}
		}
		for (int i = 0; i < enemy.size(); i++) {
			if (enemy.get(i).enemy_Y > 720) {
				enemy.remove(i);
			}
		}
		for (int i = 0; i < ammo.size(); i++) {
			if (ammo.get(i).ammo_Y < 10) {
				ammo.remove(i);
			}
		}
		for (int i = 0; i < awar.size(); i++) {
			if (awar.get(i).award_Y > 720) {
				awar.remove(i);
			}
		}
		for (int i = 0; i < awar.size(); i++) {
			for (int w = 0; w < enemy.size(); w++) {
				if(awar.get(i).getRectAward().intersects(enemy.get(w).getRectEnemy())) {
					enemy.remove(w);
				}
			}
		}
	}

	/**
	 * �������߳̿��Ʒ���
	 * @author DELL
	 *
	 */
	class awardThread extends Thread{								
		public void run() {
			while (gameStart) {
				if(!no) {
				if(!pause) {
					repaint();
					if(A==20)
						awardtime=true;
					A++;
				}
				}
				int award_Y=0,award_X=(int) (Math.random()*699+1);	
				try {
					sleep(atime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if(awardtime) {
					aw=new Award(award_X, award_Y);
					awar.add(aw);
					A=0;
					awardtime=false;
				}
			}
		}
	}
	
	/**
	 * ���˵��߳̿��Ʒ���
	 * @author DELL
	 *
	 */
	class enemyThread extends Thread{
		public void run() {
			while (gameStart) {
				if(!no) {
				if(!pause) {
					repaint();
					if(E==10)
						enemytime=true;
					E++;
				}
				}
				int enemy_Y=0,enemy_X=(int) (Math.random()*799+1);
				try {
					sleep(etime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(enemytime) {
					en=new Enemy(enemy_X, enemy_Y);
					enemy.add(en);
					E=0;
					enemytime=false;
				}
			}
		}
	}
	
	class timeThread extends Thread{
	public void run() {
		while(gameStart) {
		if(!no) {
			if(!pause) {
				gameTime++;
				repaint();
			}
			}
		if (gameTime == victoryGameTime) {
			no=!no;
			int res = JOptionPane.showConfirmDialog(jf, "��ʿ���ܱ�Ǹ��û�д������Ŀ�ꡣ\n�����ε�����ɱ�����ǣ�" + score + "\n��ʿ�������εĺ�ʱ�ǣ�"+gameTime + "\n�Ϳ���Ŀ�����ٳ���һ�ΰɣ�",
					"GameOver", JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.YES_OPTION) {
				jf.dispose();
				new Game();
			} else {
				jf.dispose();
				new GameFace();
			}
		}
	try {
		sleep(1000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
			}
			}
		}
	
	/**
	 * ��ҵ�Խ������
	 */
	public void sideMoveList() {
		//��һ������
		if(sid.player_X>=JF_WIDTH-sid.ME_WIDTH) {
			sid.player_X=JF_WIDTH-sid.ME_WIDTH;
		}
		if(sid.player_X<=0) {
			sid.player_X=0;
		}
	}
}