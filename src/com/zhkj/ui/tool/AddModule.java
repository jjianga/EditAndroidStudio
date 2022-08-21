/**
 * 
 */
package com.zhkj.ui.tool;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.eltima.components.ui.DatePicker;
import com.zhkj.ui.entity.ModuleAdress;


/**
 * @author WangLei
 * @date 	 2018��2��6��
 * @version 1.0
 */
public class AddModule{

	static ModuleAdress moduleAdress=new ModuleAdress();

	/**
	 * 1.��ӱ���ķ���
	 * title : ��Ҫչ�ֵ�����
	 * 	flag : 1 �Ǵ�����ڵ�һλ  ������������ �����ǵ�һλ
	 */
	public static JLabel addTitle(String title,int flag){
		ModuleAdress moduleAdress1 =new ModuleAdress();
		if(flag==1){
			moduleAdress1.setY(25);
		}else{
			moduleAdress1.setY(moduleAdress.getY()+80);
		}		
		JLabel titleLabel=new JLabel(title);
		titleLabel.setBounds(250, moduleAdress1.getY(), 200, 20);
		titleLabel.setFont(new java.awt.Font("Dialog",0,20));
		moduleAdress.setY(moduleAdress1.getY());		
		return titleLabel;
	}
	
	/**
	 * 2.�����ʾ+�����ķ���
	 * hintName : ��Ҫչʾ����ʾ��
	 * flag : 1 �Ǵ�����ڵ�һλ  �����������ִ����ǵ�һλ
	 */		
	public static List<Object> addHintAndInput(String hintName,int flag){
		ModuleAdress moduleAdress2 =new ModuleAdress();
		List<Object> list=new ArrayList<>();
		if(flag==1){
			moduleAdress2.setY(25);
		}else{
			moduleAdress2.setY(moduleAdress.getY()+50);
		}
		JLabel hintLabel=new JLabel(hintName);		
		hintLabel.setFont(new java.awt.Font("Dialog",0,15));
		hintLabel.setBounds(10, moduleAdress2.getY(), 500, 20);
		list.add(hintLabel);
		JTextField input=new JTextField(30);
		input.setBounds(10, moduleAdress2.getY()+30, 500, 30);
		list.add(input);
		moduleAdress.setY(moduleAdress2.getY()+30);		
		return list;
	}
	
	/**
	 * �����ʾ����ͨ��ť�������
	 * @param hintName
	 * @param btnName
	 * @return
	 */
	public static List<Object> addHintAndInputAndBtn(String hintName,String btnName){
		ModuleAdress moduleAdress3 =new ModuleAdress();
		List<Object> list=new ArrayList<>();
		moduleAdress3.setY(moduleAdress.getY()+45);
		JLabel hintLabel=new JLabel(hintName);
		hintLabel.setFont(new java.awt.Font("Dialog",0,15));
		hintLabel.setBounds(10, moduleAdress3.getY(), 300, 20);
		list.add(hintLabel);
		JButton btn=new JButton(btnName);
		btn.setBounds(410, moduleAdress3.getY(), 100, 30);
		list.add(btn);
		JTextField input=new JTextField(30);
		input.setBounds(10, moduleAdress3.getY()+40, 500, 30);
		list.add(input);
		moduleAdress.setY(moduleAdress3.getY()+40);
		return list;		
	}
	
	/**
	 * 3.�����ʾ + ѡ���ļ� + �����ķ���
	 * hintName : ��Ҫչ�ֵ���ʾ��
	 *  flag : 1 �Ǵ�����ڵ�һλ  �����������ִ����ǵ�һλ
	 *  btnName : ��ť������
	 */
	public static List<Object> addHintAndFileAndInput(String hintName,int flag,String btnName){
		ModuleAdress moduleAdress3 =new ModuleAdress();
		List<Object> list=new ArrayList<>();
		if(flag==1){
			moduleAdress3.setY(25);
		}else{
			moduleAdress3.setY(moduleAdress.getY()+45);
		}
		JLabel hintLabel=new JLabel(hintName);
		hintLabel.setFont(new java.awt.Font("Dialog",0,15));
		hintLabel.setBounds(10, moduleAdress3.getY(), 300, 20);
		list.add(hintLabel);
		JButton btn=new JButton(btnName);
		btn.setBounds(410, moduleAdress3.getY(), 100, 30);
		list.add(btn);
		JTextField input=new JTextField(30);
		input.setBounds(10, moduleAdress3.getY()+40, 500, 30);
		list.add(input);
		moduleAdress.setY(moduleAdress3.getY()+40);
		return list;
		
	}
	
	/**
	 * 4.�����ʾ + ѡ���ļ��� + �����ķ���
	 * hintName : ��Ҫչʾ����ʾ��
	 * 	flag : 1 �Ǵ�����ڵ�һλ  �����������ִ����ǵ�һλ
	 * btnName : ��Ҫչ�ֵİ�ť������
	 */
	public static List<Object> addHintAndFloderAndInput(String hintName,int flag,String btnName){
		List<Object> list=new ArrayList<>();
		ModuleAdress moduleAdress4 =new ModuleAdress();
		if(flag==1){
			moduleAdress4.setY(25);
		}else{
			moduleAdress4.setY(moduleAdress.getY()+45);
		}
		JLabel hintLabel=new JLabel(hintName);
		hintLabel.setFont(new java.awt.Font("Dialog",0,15));
		hintLabel.setBounds(10, moduleAdress4.getY(), 300, 20);
		list.add(hintLabel);
		JButton btn=new JButton(btnName);
		btn.setBounds(410, moduleAdress4.getY(), 100, 30);
		list.add(btn);
		JTextField input=new JTextField(30);
		input.setBounds(10, moduleAdress4.getY()+40, 500, 30);
		list.add(input);
		moduleAdress.setY(moduleAdress4.getY()+40);
		return list;
	}
	
	/**
	 * 5.�����ʾ + ѡ��ʱ��ؼ� + ѡ��ʱ�䰴ť + �����ķ���
	 *  hintName : ��Ҫչʾ����ʾ��
	 * 	flag : 1 �Ǵ�����ڵ�һλ  �����������ִ����ǵ�һλ
	 */
	public static List<Object> addHintAndTimeAndInput(String hintName,String btnName,int flag){
		List<Object> list=new ArrayList<>();
		ModuleAdress moduleAdress5 =new ModuleAdress();
		if(flag==1){
			moduleAdress5.setY(25);
		}else{
			moduleAdress5.setY(moduleAdress.getY()+45);
		}
		JLabel hintLabel=new JLabel(hintName);
		hintLabel.setFont(new java.awt.Font("Dialog",0,15));
		hintLabel.setBounds(10, moduleAdress5.getY(), 300, 20);
		list.add(hintLabel);
		DatePicker datepick=AddModule.getDatePicker();
		datepick.setBounds(240, moduleAdress5.getY(), 170, 30);
		list.add(datepick);
		JButton btn=new JButton(btnName);
		btn.setBounds(410, moduleAdress5.getY(), 100, 30);
		list.add(btn);
		JTextField input=new JTextField(30);
		input.setBounds(10, moduleAdress5.getY()+40, 500, 30);
		list.add(input);
		moduleAdress.setY(moduleAdress5.getY()+40);
		return list;
		
	}
	
	/**
	 * 6.���һ����ʾ��λ��
	 * moduleAdress ��һ������ ������������
	 */
	public static JLabel addHint(){	
		ModuleAdress moduleAdress6=new ModuleAdress();
		JLabel hintLabel=new JLabel("����һ��������ʾ�����");
		moduleAdress6.setY(moduleAdress.getY()+32);
		hintLabel.setBounds(10, moduleAdress6.getY(),300,20);
		moduleAdress.setY(moduleAdress6.getY());
		return hintLabel;
		
	}
	
	/**
	 * 7.���һ��������������
	 *  moduleAdress ��һ������ ������������
	 */
	public static JTextArea addBigTextInput(){
		ModuleAdress moduleAdress10=new ModuleAdress();
		moduleAdress10.setY(moduleAdress.getY());
		JTextArea area=new JTextArea();
		area.setFocusable(false);
		area.setBounds(10, moduleAdress10.getY(),300, 300);
		moduleAdress.setY(moduleAdress10.getY());
		return area;
	}
	
	/**
	 *8. ���һ�������ť
	 *name ��Ҫչ�ֵİ�ť����
	 * moduleAdress ��һ������ ������������
	 */
	public static  JButton addUnpackBtn(String name){
		ModuleAdress moduleAdress8=new ModuleAdress();
		moduleAdress8.setY(moduleAdress.getY()+70);
		JButton btn=new JButton(name);		
		btn.setBounds(195, moduleAdress8.getY(),150,70);	
		moduleAdress.setY(moduleAdress8.getY());
		return btn;
	}
	
	/**
	 * ������ֵ�������
	 * @return
	 */
	public static  JButton addSaveDataBtn(){
		ModuleAdress moduleAdress9=new ModuleAdress();
		JButton btn=new JButton("��������");		
		moduleAdress9.setY(moduleAdress.getY()-40);
		btn.setBounds(120, moduleAdress9.getY(),100,30);	
		moduleAdress.setY(moduleAdress9.getY());
		return btn;
	}
	
	
	/**
	 * 9. ���һ����ʾ + checkbox + ������ť + ѡ���ļ���ť + input
	 * hintName : ��Ҫչʾ����ʾ��
	 *	checkName : checkbox չʾ������
	 *createName : ��ť������
	 *	flag : 1 �Ǵ�����ڵ�һλ  �����������ִ����ǵ�һλ
	 */
	/*public static List<Object> addHintAndCheckBoxAndBtnAndInput(String hintName,String checkName,
			String createName,int flag){
		List<Object> list=new ArrayList<>();
		ModuleAdress moduleAdress6=new ModuleAdress();
		if(flag==1){
			moduleAdress6.setY(25);
		}else{
			moduleAdress6.setY(moduleAdress.getY()+45);
		}
		JLabel hintLabel=new JLabel(hintName);
		hintLabel.setFont(new java.awt.Font("Dialog",0,15));
		hintLabel.setBounds(10, moduleAdress6.getY(), 300, 20);
		list.add(hintLabel);
		JLabel checkLabel=new JLabel(checkName);
		checkLabel.setBounds(320, moduleAdress6.getY(), 200, 20);
		list.add(checkLabel);
		JCheckBox  box=new JCheckBox();
		box.setBounds(380, moduleAdress6.getY()-5, 20, 30);
		list.add(box);
		JButton btn=new JButton(createName);
		btn.setBounds(410, moduleAdress6.getY(), 100, 30);
		list.add(btn);
		JTextField input=new JTextField(30);
		input.setBounds(10, moduleAdress6.getY()+40, 500, 30);
		list.add(input);
		moduleAdress.setY(moduleAdress6.getY()+40);
		return list;
	}*/
	public static Map<String,JComponent> addHintAndCheckBoxAndBtnAndInput(String hintName,String getAgainName,
			String choseFileName,int flag){
		Map<String,JComponent> maps=new HashMap<String,JComponent>();
		ModuleAdress moduleAdress6=new ModuleAdress();
		if(flag==1){
			moduleAdress6.setY(25);
		}else{
			moduleAdress6.setY(moduleAdress.getY()+45);
		}
		JLabel hintLabel=new JLabel(hintName);
		hintLabel.setFont(new java.awt.Font("Dialog",0,15));
		hintLabel.setBounds(10, moduleAdress6.getY(), 300, 20);
		maps.put("hintLabel", hintLabel);
		JButton getAgainbtn=new JButton(getAgainName);
		getAgainbtn.setBounds(290, moduleAdress6.getY(), 100, 30);
		maps.put("getAgainbtn", getAgainbtn);
		JButton choseFilebtn=new JButton(choseFileName);
		choseFilebtn.setBounds(410, moduleAdress6.getY(), 100, 30);
		maps.put("choseFilebtn", choseFilebtn);
		JTextField input=new JTextField(30);
		input.setBounds(10, moduleAdress6.getY()+40, 500, 30);
		maps.put("input", input);
		moduleAdress.setY(moduleAdress6.getY()+40);
		return maps;
	}
	
	/**
	 *  ���һ�� JComboBox<String> ����ѡ�Ŀؼ�
	 * @param hintName  ��ʾ��
	 * @param valList     ֵ���
	 * @return
	 */
	public static List<Object> addHintAndJComboBox(String hintName,List<String> valList){
		List<Object> list=new ArrayList<>();
		ModuleAdress moduleAdress12=new ModuleAdress();
		moduleAdress12.setY(moduleAdress.getY()+45);
		//������ʾ��
		JLabel hintLabel=new JLabel(hintName);
		hintLabel.setFont(new java.awt.Font("Dialog",0,15));
		hintLabel.setBounds(10, moduleAdress12.getY(), 300, 20);
		list.add(hintLabel);
		//����JComboBox
		JComboBox<String> homeComboBox=new JComboBox<String>();
		homeComboBox.setBounds(10, moduleAdress12.getY()+30, 500, 30);
		for(String str:valList){
			homeComboBox.addItem(str);
		}
		list.add(homeComboBox);
		moduleAdress.setY(moduleAdress12.getY()+30);		
		return list;
	}
	
	/**
	 * һ��ʱ��ؼ� ֻ�ڱ�����ʹ��
	 * @return
	 */
	private static DatePicker getDatePicker() {
		final DatePicker datepick;
		// ��ʽ
        String DefaultFormat = "yyyy-MM-dd HH:mm:ss";
        // ��ǰʱ��
        Date date = new Date();
        // ����
        Font font = new Font("Times New Roman", Font.BOLD, 14); 
        Dimension dimension = new Dimension(177, 24); 
        datepick = new DatePicker(date, DefaultFormat, font, dimension);  
        datepick.setLocation(260, 530);
        // ���ù���
        datepick.setLocale(Locale.CHINA);
        // ����ʱ�����ɼ�
        datepick.setTimePanleVisible(true);
		return datepick;
	}
	
}
