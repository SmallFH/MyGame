package com.game.www;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;


/**
 * 游戏界面中的一些功能
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
	 * 构造方法实现窗口的初始化
	 */
	public Game() {
		jf = new JFrame("荣耀大作战");
		jf.setSize(JF_WIDTH, JF_HEIGHT);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jf.addWindowListener(new window());
		jf.setResizable(false);
		// 窗口大小不可改变
		jf.setVisible(true);
		jf.addKeyListener(new myKey());
		jf.add(this);
		award.start();
		ene.start();
		tim.start();
	}

	/**
	 * 创建一个内部类实现窗口监听
	 */
	class window extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			no = !no;
			int close = JOptionPane.showConfirmDialog(jf, "勇士啊，你真的想退出游戏吗？\n是的话就点击yes\n点击No的话可以返回游戏偶！", "退出or重玩",
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
	 * 创建一个内部类实现键盘的事件处理
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
				int re = JOptionPane.showConfirmDialog(jf, "勇士啊，你真的想重新开始游戏吗？\n是的话就点击yes\n想返回游戏就点击No！", "重玩？",
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
	 * 画笔工具用来重复绘制游戏界面
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
			Font font1 = new Font("楷体", Font.BOLD, 50);
			Color c = g.getColor();
			g.setColor(Color.LIGHT_GRAY);
			g.setFont(font1);
			g.drawString("游戏暂停", 320, 400);
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
	 * 画游戏分数、难度、生命值
	 */
	private void drawScore(Graphics g) {
		int x = 10, y = 50;
		Font font1 = new Font("楷体", Font.BOLD, 30);
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.setFont(font1);
		g.drawString("击杀敌人数目：" + score, x, y);
		Font font2 = new Font("楷体", Font.BOLD, 30);
		g.setColor(Color.white);
		g.setFont(font2);
		g.drawString("生命" + sid.live, x, 2*y);
		g.setColor(Color.ORANGE);
		
		if (gameTime >= 0 && gameTime < 15) {
			g.drawString("游戏难度：0", 10, 150);
		}
		if (gameTime >= 15 && gameTime < 30) {
			g.drawString("游戏难度：1", 10, 150);
		}
		if (gameTime >= 30 && gameTime < 45) {
			g.drawString("游戏难度：2", 10, 150);
		}
		if (gameTime >= 45 && gameTime < 60) {
			g.drawString("游戏难度：3", 10, 150);
		}
		if (gameTime >= 60 && gameTime < 75) {
			g.drawString("游戏难度：4", 10, 150);
		}
		if (gameTime >= 75) {
			g.drawString("游戏难度：5", 10, 150);
		}
			Font fontGame = new Font("楷体", Font.BOLD, 30);
			g.setColor(Color.LIGHT_GRAY);
			g.setFont(fontGame);
			g.drawString("游戏时间:" + gameTime, x, 4*y);
		g.setColor(c);
		if(!no) {
		if (!pause) {
			repaint();
		}
	}
		}
	
	/**
	 * 碰撞检测的方法
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
						int clo = JOptionPane.showConfirmDialog(jf, "You are so 厉害！！！\n您完成任务的耗时为："+gameTime+"\n让我们再来一局好不好？\n再来一局就点Yes\n点击No返回主界面",
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
					int res = JOptionPane.showConfirmDialog(jf, "勇士，很抱歉您没有达成任务目标。\n您本次的最终杀敌数是：" + score + "\n勇士，您本次的耗时是："+gameTime + "\n就快达成目标了再尝试一次吧？",
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
	 * 奖励的线程控制方法
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
	 * 敌人的线程控制方法
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
			int res = JOptionPane.showConfirmDialog(jf, "勇士，很抱歉您没有达成任务目标。\n您本次的最终杀敌数是：" + score + "\n勇士，您本次的耗时是："+gameTime + "\n就快达成目标了再尝试一次吧？",
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
	 * 玩家的越界限制
	 */
	public void sideMoveList() {
		//建一个方法
		if(sid.player_X>=JF_WIDTH-sid.ME_WIDTH) {
			sid.player_X=JF_WIDTH-sid.ME_WIDTH;
		}
		if(sid.player_X<=0) {
			sid.player_X=0;
		}
	}
}