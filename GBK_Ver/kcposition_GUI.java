import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class kcposition_GUI extends JFrame implements ActionListener {
	JLabel tip,swf;
	JTextField main, swfname;
	JButton btn, btn2;
	String ch;

	kcposition_GUI() {
		/** 
		*@Project:KanColleCoordinatesMaker�������������ļ�������GUI
		*@Author: a5566123s
		*@Date: 2018-1-13
		*@Copyright: weibo.com/a5566123s All rights reserved. 
		*/  
		this.setTitle("�������������ļ������� V1.0");
		Container cp = this.getContentPane();
		cp.setLayout(new FlowLayout());// ������
		tip = new JLabel("��ѡ��api_start2�ļ�");
		main = new JTextField(24);
		btn = new JButton("���");
		swf = new JLabel("������swf�ļ���:");
		swfname = new JTextField(30);
		btn2 = new JButton("���������ļ�");

		cp.add(tip);
		cp.add(main);
		cp.add(btn);
		cp.add(swf);
		cp.add(swfname);
		cp.add(btn2);

		this.setSize(400, 180);
		this.setLocation(700, 450);
		this.setVisible(true);
		btn.addActionListener(this);
		btn.setActionCommand("1");
		btn2.addActionListener(this);
		btn2.setActionCommand("2");
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser h;
		String pathname="";
	
		if (e.getActionCommand().equals("1")) {
			h=new JFileChooser();
			h.showOpenDialog(null);
			h.setVisible(true);
			pathname=h.getSelectedFile().getAbsolutePath();
			main.setText(pathname);
		} 
		if (e.getActionCommand().equals("2")) {
			try {
				System.out.println(pathname);
				File filename = new File(main.getText());
				FileInputStream fInputStream = new FileInputStream(filename);
				InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
				BufferedReader br = new BufferedReader(reader);
				String line = "";
				String str = "";
				while ((line = br.readLine()) != null) {
					str = str + line;
				}


				String sc=swfname.getText();
//				String ch, ce;
//				ce = sc.nextLine();
				ch = "\"" + sc;
				FileSystemView fsv = FileSystemView.getFileSystemView();
				File com=fsv.getHomeDirectory();    //��ȡ����·��
				File writename = new File(com.getPath() +"\\"+ sc + ".config.ini");// ���config.ini�ļ������·��
				writename.createNewFile(); // �������ļ�
				BufferedWriter out = new BufferedWriter(new FileWriter(writename));
				int i = str.indexOf(ch);
				int j = str.indexOf("}", i);
				String c = "";
				for (; i <= j; i++) {
					c = c + str.charAt(i);
				}
				int flag = 0, k = 0, z = 0;
				String a = "";
				String b = "";
				String d = "";
				out.write("[graph]" + "\r\n");
				// System.out.println("[graph]");
				for (i = 0; i < c.length(); i++) {
					if ((c.charAt(i) + "").equals("\"")) {
						flag = flag % 2 + 1;
						z = 0;
						if (a.equals("") != true) {
							d = a;
						}
						a = "";
					}
					if (flag == 1) {
						if (z > 4) {
							if ((c.charAt(i) + "").equals("\"") != true) {
								a = a + c.charAt(i);
							}
						}
						z++;
					} else {
						if (c.charAt(i) > 47 && c.charAt(i) < 58 || c.charAt(i) == '-') {
							b = b + c.charAt(i);
						} else {
							if ((b.equals("") != true) && k == 0) {
								// System.out.println(d+"_left="+b);
								out.write(d + "_left=" + b + "\r\n");
								k = (k + 1) % 2;
							} else if ((b.equals("") != true) && k == 1) {
								// System.out.println(d+"_top="+b);
								out.write(d + "_top=" + b + "\r\n");
								k = (k + 1) % 2;
							}
							b = "";
						}
					}
				}

				out.flush(); // �ѻ���������ѹ���ļ�
				out.close(); // ���ǵùر��ļ�
				reader.close();
				br.close();
//				System.out.println(sc+".config.ini"+"�����ļ����ɳɹ���");
				JOptionPane.showMessageDialog(this, sc+".config.ini"+"���������ϳɹ����ɣ�");

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		kcposition_GUI fa = new kcposition_GUI();
	}
}
