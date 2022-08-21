/**
 * 
 */
package com.zhkj.ui.project;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.zhkj.ui.tool.ReadFileAddModule;

/**
 * @author WangLei
 * @date 	 2018��2��6��
 * @version 1.0
 */
public class ProjectDemo {

	/*
	 * ��Ŀģ��
	 */
	public static JScrollPane proDemo(String path){
		JPanel proAA=new JPanel();
		proAA.setLayout(null);
		
		/**
		 * ��ʼ
		 */
		ReadFileAddModule rfam=new ReadFileAddModule();
		rfam.readFile(path, proAA);
		/**
		 * ����
		 */			
		JScrollPane jsAA=new JScrollPane(proAA);	
		//���ù������������ٶ�
		jsAA.getVerticalScrollBar().setUnitIncrement(10);
		//���ù�������λ��
		jsAA.setBounds(10, 130, 575, 1500);
		//���ù�������Զ�ɼ�
		jsAA.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsAA.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//����ֻ�д�ֱ�춯��
	    proAA.setPreferredSize(new Dimension(jsAA.getWidth() - 50, jsAA.getHeight()*2));
		return jsAA;
	}

}
