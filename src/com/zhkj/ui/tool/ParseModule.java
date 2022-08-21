/**
 * 
 */
package com.zhkj.ui.tool;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.eltima.components.ui.DatePicker;
import com.zhkj.code.utils.RandomStringUtil;
import com.zhkj.process.ExecuteDispose;



/**
 * @author WangLei
 * @date 	 2018��2��7��
 * @version 1.0
 */
public class ParseModule {
	
	/**
	 * 1.���� ��ʾ+�����ķ���
	 */
	static JTextField txt;
	 public JTextField parseHintAndInput(List<Object> list,JPanel  jpanel){
		 for (int i = 0; i < list.size(); i++) {
			 Object param = list.get(i);
			 if(param instanceof JTextField){
				 txt=((JTextField) param);
			 }
			 jpanel.add((Component) list.get(i));
		}
		return txt;
		 
	 }
	 
	 /**
	  * ���� ��ʾ+�����+������ķ���
	  */
	 JTextField text1=null;
	 public JTextField parseHintAndBtnAndInput(List<Object> list,JPanel  jpanel){	
		 for (int i = 0; i < list.size(); i++) {
				Object param = list.get(i);				
				if(param instanceof JTextField){					
					text1=((JTextField) param);													
				}
				if(param instanceof JButton){
					((JButton) param).setFocusable(false);
					((JButton) param).addActionListener(new ActionListener() {						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							int num=5+(int)(Math.random()*5);
							String num2= RandomStringUtil.randomLowSting(num);
							text1.setText(num2);
						}
					});
				}	
				jpanel.add((Component) list.get(i));
			}	 	
		 	return text1;	 
	 }
	
	/**
	 * 2.���� ��ʾ + ѡ���ļ� + �����ķ���
	 */
	 JTextField text=null;
	 public JTextField parseHintAndFileAndInput(List<Object> list,JPanel  jpanel){	
		 for (int i = 0; i < list.size(); i++) {
				Object param = list.get(i);				
				if(param instanceof JTextField){					
					text=((JTextField) param);
					((JTextField) param).setEditable(false);
					((JTextField) param).setFocusable(false);
				}
				if(param instanceof JButton){
					((JButton) param).setFocusable(false);
					((JButton) param).addActionListener(new ActionListener() {						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							JFileChooser chooser = new JFileChooser();// ����ѡ����
							chooser.setMultiSelectionEnabled(true);// ��Ϊ��ѡ
							int returnVal = chooser.showOpenDialog((JButton) param);
							// �ж��Ƿ��Ǵ򿪰�ť
							if (returnVal == JFileChooser.APPROVE_OPTION) {
								String filepath = chooser.getSelectedFile().getAbsolutePath();// ��ȡ����·��
								text.setText(filepath);	
																					
							}
						}
					});
				}	
				jpanel.add((Component) list.get(i));
			}	 	
		 	return text;
		 
	 }
	
	/**
	 *3. ���� ��ʾ + ѡ���ļ��� + �����ķ���
	 */
	 JTextField text2=null;
	 public JTextField parseHintAndFloderAndInput(List<Object> list,JPanel  jpanel){	
		 for (int i = 0; i < list.size(); i++) {	 	
				Object param = list.get(i);	
				 if(param instanceof JTextField){
					 text2=((JTextField) param);
					((JTextField) param).setEditable(false);			
					((JTextField) param).setFocusable(false);
				}
				if(param instanceof JButton){
					((JButton) param).setFocusable(false);
					((JButton) param).addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							JFileChooser chooser = new JFileChooser();// ����ѡ����
							chooser.setMultiSelectionEnabled(true);// ��Ϊ��ѡ
							chooser.setFileSelectionMode(1);// �趨ֻ��ѡ���ļ���				
							int returnVal = chooser.showOpenDialog((JButton) param);
							// �ж��Ƿ��Ǵ򿪰�ť
							if (returnVal == JFileChooser.APPROVE_OPTION) {
								String filepath = chooser.getSelectedFile().getAbsolutePath();// ��ȡ����·��											
								text2.setText(filepath);
							}
						}
					});					
				}			
				jpanel.add((Component) list.get(i));
			}
		 	return text2;
	 }
	
	/**
	 * 4.���� ��ʾ + ѡ��ʱ��ؼ� + ѡ��ʱ�䰴ť + �����ķ���
	 */
	 JTextField text3=null;
	 DatePicker dp=null;
	 public JTextField parseHintAndTimeAndInput(List<Object> list,JPanel  jpanel){
		for (int i = 0; i < list.size(); i++) {
			Object param = list.get(i);	
			if(param instanceof DatePicker){
				 dp=(DatePicker) param;				
			}
			if(param instanceof JTextField){
				text3=((JTextField) param);
				((JTextField) param).setEditable(false);	
				((JTextField) param).setFocusable(false);
			}
			if(param instanceof JButton){
				((JButton) param).setFocusable(false);
				((JButton) param).addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {				
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String time=sdf.format(dp.getValue());	
						text3.setText(time);
						
					}
				});
			}
			 jpanel.add((Component) list.get(i));
		}
		return text3;
	 }
		
	/**
	 * 5.���� һ�������ť
	 */
	 public void parseUnpackBtn(JButton btn,JPanel  jpanel){
		btn.setFocusable(false);
		btn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				ExecuteDispose.goOn();
			}
		});
		jpanel.add(btn);
	 }
	 	
	/**
	 * 6.���� ��ʾ + checkbox + ������ť + ѡ���ļ���ť + input
	 */
	/* JTextField text4=null;
	 JCheckBox box=null;
	 public JTextField parseHintAndCheckBoxAndBtnAndInput(List<Object> list,JPanel  jpanel){
		 for (int i = 0; i < list.size(); i++) {
			 Object param = list.get(i);
			 if(param instanceof JTextField){
				 text4=(JTextField) param;
				 ((JTextField) param).setEditable(false);
				 ((JTextField) param).setFocusable(false);
			 }
			 if(param instanceof JCheckBox){
				box=(JCheckBox) param;
				((Component) param).setFocusable(false);
				//box.setSelected(true);
				box.addActionListener(new ActionListener() {					
					@Override
					public void actionPerformed(ActionEvent e) {
						if(box.isSelected()){							
							//ѡ���ļ�
							JFileChooser chooser = new JFileChooser();// ����ѡ����
							chooser.setMultiSelectionEnabled(true);// ��Ϊ��ѡ
							int returnVal = chooser.showOpenDialog((JCheckBox) param);
							// �ж��Ƿ��Ǵ򿪰�ť
							if (returnVal == JFileChooser.APPROVE_OPTION) {
								String filepath = chooser.getSelectedFile().getAbsolutePath();// ��ȡ����·��
								text4.setText(filepath);
								box.setSelected(true);								
							}
						}
					}
				});
			 }
			 if(param instanceof JButton){
				 ((JButton) param).setFocusable(false);
				 ((JButton) param).addActionListener(new ActionListener() {					
					@Override
					public void actionPerformed(ActionEvent e) {
							
					}				
				});
			 }			
			 jpanel.add((Component) list.get(i));
		}
		return text4;
	 }*/
	 
	 public JTextField parseHintAndCheckBoxAndBtnAndInput(JPanel  jpanel,Map<String,JComponent> map){
		 //��ʾ
		 JLabel hintLabel=(JLabel) map.get("hintLabel");
		 jpanel.add(hintLabel);
		 //�ı���
		 JTextField input= (JTextField) map.get("input");
		 input.setEditable(false);
		 input.setFocusable(false);
		 jpanel.add(input);
		 //��հ�ť
		 JButton getAgainbtn=(JButton) map.get("getAgainbtn");
		 getAgainbtn.setFocusable(false);
		 getAgainbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				input.setText(" ");
			}
		});
		 jpanel.add(getAgainbtn);
		 //��ȡ�ļ���ť
		 JButton choseFilebtn=(JButton) map.get("choseFilebtn");
		 getAgainbtn.setFocusable(false);
		 choseFilebtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//ѡ���ļ�
				JFileChooser chooser = new JFileChooser();// ����ѡ����
				chooser.setMultiSelectionEnabled(true);// ��Ϊ��ѡ
				int returnVal = chooser.showOpenDialog(choseFilebtn);
				// �ж��Ƿ��Ǵ򿪰�ť
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String filepath = chooser.getSelectedFile().getAbsolutePath();// ��ȡ����·��
					input.setText(filepath);					
				}
			}
		});
		 jpanel.add(choseFilebtn);
		 
		return input;
	 }
	 
	 /**
	  * ������ʾ+ JComboBox����ѡ
	  */
	 static JComboBox<String> txt5;
	 @SuppressWarnings("unchecked")
	public JComboBox<String> parseHintAndJComboBox(List<Object> list,JPanel  jpanel){
		 for (int i = 0; i < list.size(); i++) {
			 Object param = list.get(i);
			 if(param instanceof JComboBox){
				 txt5=(JComboBox<String>) param;
				 ((Component) param).setFocusable(false);
			 }
			 jpanel.add((Component) list.get(i));
		}
		return txt5;
		 
	 }
	
}
