package com.game.www;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** 
 *  �򿪳������Ľ���
 */
public class GameFace extends JPanel implements ActionListener {           
	private JFrame jf;
	private JButton play, help, over,about;
	final int JF_WIDTH = 900, JF_HEIGHT = 700;
    final int JB_X = 109, JB_Y = 105, JB_WIDTH = 200, JB_HEIGHT = 80; 
	
	public GameFace() {
		
		jf = new JFrame("�sҫ����ս");
		play = new JButton("��ʼ��Ϸ");
		play.setFont(new Font("���Ĳ���", Font.BOLD, 30));
		play.setForeground(Color.ORANGE);
		//��ť����͸��
		play.setContentAreaFilled(false);
		//������ť�߿�
		play.setFocusPainted(false);
		//������ť�۽�
		play.setBorder(null);
		help = new JButton("��Ϸ����");
		help.setFont(new Font("���Ĳ���", Font.BOLD, 30));
		help.setForeground(Color.ORANGE);
		help.setContentAreaFilled(false);
		help.setFocusPainted(false);
		help.setBorder(null);
		about = new JButton("��Ϸ���");
		about.setFont(new Font("���Ĳ���", Font.BOLD, 30));
		about.setForeground(Color.ORANGE);
		about.setContentAreaFilled(false);
		about.setFocusPainted(false);
		about.setBorder(null);
		about.setMnemonic('a');  //��ݼ���Alt+��a��
		over = new JButton("�˳���Ϸ");
		over.setFont(new Font("���Ĳ���", Font.BOLD, 30));
		over.setForeground(Color.ORANGE);
		over.setContentAreaFilled(false);
		over.setFocusPainted(false);
		over.setBorder(null);
		jf.setSize(JF_WIDTH, JF_HEIGHT);
		this.setSize(JF_WIDTH, JF_HEIGHT);                  
		jf.setLocationRelativeTo(null);						
		//���ھ���						
		jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  
		//�����Ų��ô��ڹ�
		jf.addWindowListener(new window());
		jf.setResizable(false);								
		//���ڴ�С���ɸı�
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
	 * ʵ�ֶԴ��ڵļ���
	 */
	//���ڼ������¼�����
	class window extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			int clo= JOptionPane.showConfirmDialog(jf,"��ʿ������������ľ���Ҫ�뿪��","�˳���Ϸ",JOptionPane.YES_NO_OPTION);
			if(clo==JOptionPane.YES_OPTION) {
				System.exit(0);	
			}else {
			}
		}
	}

	/**
	 * ���̼������¼�����
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == play) {
			jf.dispose();
			new Game();
		}
		else if (e.getSource() == help) {
			JOptionPane.showMessageDialog(jf,
					"һ��СС����Ϸ����������ϣ��������������^_^\n��Ϸȫ�̿ɼ��̲ٿء�\n���������������ֱ����������ƶ���\n��X����������\n��SPACE��(�ո�)��������ͣ��Ϸ�ϡ�\n��Ϸ�а���R��������ѡ�����¿�ʼ��Ϸ��˵��\n��Ϸ����ʱ������񡱵Ļ����Է���������ż��\n�������Ϸ;����Ҫ�˳��Ļ��������ϽǵĲ�ţ������԰���������������أ�\nLast but not list,��̽��Ҫ��100��֮��ɱ��120����Ӯ��ʤ��ż�ף�",
					"��Ϸ����", JOptionPane.PLAIN_MESSAGE); 
		}
		else if (e.getSource() == about) {
			JOptionPane.showMessageDialog(jf,
					"���Ĳ�ʿ�������ؿƼ�����а��Ͽ�ȸ����˴����Ĺ��\n��̽Ϊ�˴ݻٲ�ʿ�Ļ���ֻ��������а��Ͽ�Ⱥ͹��ﶷ־���¡�\n����Ϸ�ɰ�����Ա-����Ϲ���������ģ��", "��Ϸ���",
					JOptionPane.PLAIN_MESSAGE); 
		}
		else if (e.getSource() == over) {
			int shut = JOptionPane.showConfirmDialog(jf, "��ʿ������������ľ���Ҫ�뿪��", "�˳���Ϸ", JOptionPane.YES_NO_OPTION);
			if (shut == JOptionPane.YES_OPTION) {
				System.exit(0);
			} else {
			}
		}
	}

	/**
	 * ��������
	 */
	public static void main(String[] args) {
		new GameFace();
	}

	/**
	 * ��������Ļ���
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