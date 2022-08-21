/**
 * 
 */
package com.zhkj.ui.tool;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.zhkj.log.Log;


/**
 * @author WangLei
 * @date 	 2018��2��8��
 * @version 1.0
 */
public class ReadFileAddModule {	
	public static Map<String,JComponent> maps=new HashMap<String,JComponent>();
	public static String resPath;
	public static String srcPath;
	public static String assetsPath;
	/**
	 * ��ȡ�ļ� ���ؿؼ�
	 * @param path
	 * @param proAA
	 */
	public void readFile(String path,JPanel proAA){		
		File file=new File(path);		
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			String str=null;								
			while((str=br.readLine())!=null){				
				//����
				if(str.contains("����")){
					String[] arr=str.split("\\|");
					if(arr[1].equals("����")){
						if(arr[3].equals("1")){
							proAA.add(AddModule.addTitle(arr[2],1));
						}
						if(arr[3].equals("2")){
							proAA.add(AddModule.addTitle(arr[2],2));
						}					
					}
				}
				//����������
				if(str.contains("����������")){
					String[] arr=str.split("\\|");
					if(arr[1].equals("����������")){
						List<Object> list=AddModule.addHintAndInput(arr[2], 2);
						JTextField hintAndInput=new ParseModule().parseHintAndInput(list, proAA);	
						String key=arr[0];
						if(hintAndInput!=null){							
							maps.put(key, hintAndInput);
						}
					}				
				}
				//�����
				if(str.contains("���������")){
					String[] arr=str.split("\\|");
					if(arr[1].equals("���������")){
						List<Object> list=AddModule.addHintAndInputAndBtn(arr[2],arr[3]);
						JTextField useFile=new ParseModule().parseHintAndBtnAndInput(list, proAA);
						String key=arr[0];
						if(useFile!=null){
							maps.put(key, useFile);
						}						
					}		
				}
				//ѡ���ļ�
				if(str.contains("ѡ���ļ�")){
					String[] arr=str.split("\\|");
					if(arr[1].equals("ѡ���ļ�")){
						List<Object> list=AddModule.addHintAndFileAndInput(arr[2],2,arr[3]);
						JTextField useFile=new ParseModule().parseHintAndFileAndInput(list, proAA);
						String key=arr[0];
						if(useFile!=null){
							maps.put(key, useFile);
						}						
					}				
				}
				//ѡ���ļ���
				if(str.contains("ѡ���ļ���")){
					String[] arr=str.split("\\|");
					if(arr[1].equals("ѡ���ļ���")){
						List<Object> list3=AddModule.addHintAndFloderAndInput(arr[2], 2, arr[3]);
						JTextField useDirectory=new ParseModule().parseHintAndFloderAndInput(list3, proAA);	
						String key=arr[0];
						if(useDirectory!=null){
							maps.put(key, useDirectory);
						}
						
					}			
				}
				//��ȡʱ���
				if(str.contains("��ȡʱ��")){
					String[] arr=str.split("\\|");
					if(arr[1].equals("��ȡʱ��")){
						List<Object> list5=AddModule.addHintAndTimeAndInput(arr[2],arr[3],2);
						JTextField getTime=new ParseModule().parseHintAndTimeAndInput(list5,proAA);
						String key=arr[0];
						if(getTime!=null){
							maps.put(key, getTime);
						}
						
					}
					
				}
				//���ز���
				if(str.contains("���ز���")){
					String[] arr=str.split("\\|");
					if(arr[1].equals("���ز���")){
						String key=arr[0];
						JTextField s = new JTextField();
						s.setText(arr[2]);
						maps.put(key, s);
					}
					
				}
				//����ѡ��
				if(str.contains("JComboBox����ѡ")){
					String[] arr=str.split("\\|");
					List<String> valList=new ArrayList<>();
					for (int i = 3; i < arr.length; i++) {
						valList.add(arr[i]);
					}
					if(arr[1].equals("JComboBox����ѡ")){
						List<Object> list=AddModule.addHintAndJComboBox(arr[2], valList);
						JComboBox<String> jComboBox=new ParseModule().parseHintAndJComboBox(list, proAA);
						String key=arr[0];
						if(jComboBox!=null){
							ReadFileAddModule.maps.put(key, jComboBox);
						}
					}
				}
				//checkBox ��������ļ�
				if(str.contains("checkBox")){
					String[] arr=str.split("\\|");
					if(arr[1].equals("checkBox")){
						Map<String,JComponent> map=AddModule.addHintAndCheckBoxAndBtnAndInput(arr[2],arr[3],arr[4], 2);
						JTextField checkBox=new ParseModule().parseHintAndCheckBoxAndBtnAndInput(proAA,map);						
						String key=arr[0];
						if(checkBox!=null){
							maps.put(key, checkBox);
						}
						
					}
					
				}
				//��ť
				if(str.contains("��ť")){
					String[] arr=str.split("\\|");
					if(arr[1].equals("���")){
						JButton btn=AddModule.addUnpackBtn(arr[3]);
						new ParseModule().parseUnpackBtn(btn, proAA);
					}
				}
				//���ı���
				if(str.contains("�ı���")){
					String[] arr=str.split("\\|");
					if(arr[1].equals("�ı�")){
						JTextArea txt=AddModule.addBigTextInput();
						String key=arr[0];
						txt.setEditable(false);
						txt.setBackground(new Color(238, 238, 238));
						proAA.add(txt);
						maps.put(key, txt);
					}
				}			
			}
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
		
	}
	/**
	 * ��ȡֵ
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		if(ReadFileAddModule.maps.get(key) != null){
			JComponent jcomponent = ReadFileAddModule.maps.get(key);
			if(jcomponent instanceof JTextComponent){
				return ((JTextComponent)jcomponent).getText();
			}else {
				return (String)((JComboBox<?>)jcomponent).getSelectedItem();
			}
		}
		System.out.println(key+",UiIsNull");
		return null;
	}
	/**
	 * ��ȡĿ¼
	 * @param key
	 * @return
	 */
	public static String getDirectory(String key) {
		if(ReadFileAddModule.maps.get(key) != null){
			JComponent jcomponent = ReadFileAddModule.maps.get(key);
			if(jcomponent instanceof JTextComponent){
				return ((JTextComponent)jcomponent).getText()+File.separator;
			}else{
				return (String)((JComboBox<?>)jcomponent).getSelectedItem()+File.separator;
			}
		}
		System.out.println(key+",isNullDirectory");
		return null;
	}

	/**
	 * ����ֵ
	 * @param key
	 * @return
	 */
	public static void setText(String key,String text) {
		if(ReadFileAddModule.maps.get(key) != null){
			JComponent jcomponent = ReadFileAddModule.maps.get(key);
			if(jcomponent instanceof JTextComponent){
				((JTextComponent)jcomponent).setText(text);
			}else{
				Log.appendErr(key+"isNotJTextComponent");
			}
		}
		else
		System.out.println(key+",isNullDirectory");
	}	
		    
}
