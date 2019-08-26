package com.game.www;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** 
 *  打开程序进入的界面
 */
public class GameFace extends JPanel implements ActionListener {           
	private JFrame jf;
	private JButton play, help, over,about;
	final int JF_WIDTH = 900, JF_HEIGHT = 700;
    final int JB_X = 109, JB_Y = 105, JB_WIDTH = 200, JB_HEIGHT = 80; 
	
	public GameFace() {
		
		jf = new JFrame("榮耀大作战");
		play = new JButton("开始游戏");
		play.setFont(new Font("华文彩云", Font.BOLD, 30));
		play.setForeground(Color.ORANGE);
		//按钮背景透明
		play.setContentAreaFilled(false);
		//消除按钮边框
		play.setFocusPainted(false);
		//消除按钮聚焦
		play.setBorder(null);
		help = new JButton("游戏帮助");
		help.setFont(new Font("华文彩云", Font.BOLD, 30));
		help.setForeground(Color.ORANGE);
		help.setContentAreaFilled(false);
		help.setFocusPainted(false);
		help.setBorder(null);
		about = new JButton("游戏简介");
		about.setFont(new Font("华文彩云", Font.BOLD, 30));
		about.setForeground(Color.ORANGE);
		about.setContentAreaFilled(false);
		about.setFocusPainted(false);
		about.setBorder(null);
		about.setMnemonic('a');  //快捷键：Alt+‘a’
		over = new JButton("退出游戏");
		over.setFont(new Font("华文彩云", Font.BOLD, 30));
		over.setForeground(Color.ORANGE);
		over.setContentAreaFilled(false);
		over.setFocusPainted(false);
		over.setBorder(null);
		jf.setSize(JF_WIDTH, JF_HEIGHT);
		this.setSize(JF_WIDTH, JF_HEIGHT);                  
		jf.setLocationRelativeTo(null);						
		//窗口居中						
		jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  
		//点击叉号不让窗口关
		jf.addWindowListener(new window());
		jf.setResizable(false);								
		//窗口大小不可改变
		play.setBounds(JB_X+75, JB_Y, JB_WIDTH, JB_HEIGHT);
		help.setBounds(JB_X+412,JB_Y+3, JB_WIDTH, JB_HEIGHT);
		about.setBounds(JB_X+75, JB_Y+403,JB_WIDTH, JB_HEIGHT);
		over.setBounds(JB_X+409, JB_Y+403,JB_WIDTH, JB_HEIGHT);  
		jf.add(help);
		jf.add(play);
		jf.add(over);
		jf.add(about);
		jf.add(this);
		play.addActionListener(this);
		help.addActionListener(this);
		over.addActionListener(this);
		about.addActionListener(this);
		jf.setVisible(true);								
	}

	/**
	 * 实现对窗口的监听
	 */
	//窗口监听的事件处理
	class window extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			int clo= JOptionPane.showConfirmDialog(jf,"勇士，你真的真的真的决定要离开吗？","退出游戏",JOptionPane.YES_NO_OPTION);
			if(clo==JOptionPane.YES_OPTION) {
				System.exit(0);	
			}else {
			}
		}
	}

	/**
	 * 键盘监听的事件处理
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == play) {
			jf.dispose();
			new Game();
		}
		else if (e.getSource() == help) {
			JOptionPane.showMessageDialog(jf,
					"一点小小的游戏方法给您，希望给您带来帮助^_^\n游戏全程可键盘操控。\n“←”，“→”分别控制人物的移动。\n“X”键攻击。\n“SPACE”(空格)键可以暂停游戏呦。\n游戏中按“R”键可以选择重新开始游戏的说！\n游戏结束时点击“否”的话可以返回主界面偶！\n如果您游戏途中想要退出的话请点击右上角的叉号，他可以帮您返回主界面的呢！\nLast but not list,神探需要在100秒之内杀敌120才能赢得胜利偶亲！",
					"游戏帮助", JOptionPane.PLAIN_MESSAGE); 
		}
		else if (e.getSource() == about) {
			JOptionPane.showMessageDialog(jf,
					"黑心博士带着神秘科技来到邪恶峡谷复制了大量的怪物，\n神探为了摧毁博士的机器只身来到了邪恶峡谷和怪物斗志斗勇。\n本游戏由八组组员-付豪瞎编而成切勿模仿", "游戏简介",
					JOptionPane.PLAIN_MESSAGE); 
		}
		else if (e.getSource() == over) {
			int shut = JOptionPane.showConfirmDialog(jf, "勇士，你真的真的真的决定要离开吗？", "退出游戏", JOptionPane.YES_NO_OPTION);
			if (shut == JOptionPane.YES_OPTION) {
				System.exit(0);
			} else {
			}
		}
	}

	/**
	 * 程序的入口
	 */
	public static void main(String[] args) {
		new GameFace();
	}

	/**
	 * 画主界面的画笔
	 */
	public void paint(Graphics g) {
		Image im = new ImageIcon("src/images/Map.png").getImage(); 
		g.drawImage(im, 0, 0, JF_WIDTH, JF_HEIGHT, this);
		help.repaint();
		play.repaint();
		over.repaint();
		about.repaint(); 
	}
}