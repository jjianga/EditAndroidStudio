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
 * @date 	 2018年2月6日
 * @version 1.0
 */
public class ProjectDemo {

	/*
	 * 项目模板
	 */
	public static JScrollPane proDemo(String path){
		JPanel proAA=new JPanel();
		proAA.setLayout(null);
		
		/**
		 * 开始
		 */
		ReadFileAddModule rfam=new ReadFileAddModule();
		rfam.readFile(path, proAA);
		/**
		 * 结束
		 */			
		JScrollPane jsAA=new JScrollPane(proAA);	
		//设置滚动条滚动的速度
		jsAA.getVerticalScrollBar().setUnitIncrement(10);
		//设置滚动条的位置
		jsAA.setBounds(10, 130, 575, 1500);
		//设置滚动条永远可见
		jsAA.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsAA.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//设置只有垂直混动条
	    proAA.setPreferredSize(new Dimension(jsAA.getWidth() - 50, jsAA.getHeight()*2));
		return jsAA;
	}

}
