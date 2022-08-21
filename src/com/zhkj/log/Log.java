package com.zhkj.log;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;


public class Log {
	public static JTextPane jtp;
	private static FileWriter writer = null;
	static{
        try {
	    	//��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�
	    	writer = new FileWriter("this.log", false);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
		}
        Runtime run=Runtime.getRuntime();//��ǰ Java Ӧ�ó�����ص�����ʱ����  
        run.addShutdownHook(new Thread(){ //ע���µ���������رչ���  
            @Override  
            public void run() {  
                //�������ʱ���еĲ���  
            	try {
        	        writer.write("\r\n********************************************************** ");
    				writer.close();
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
             }
         });  

	}
	public static void appendErr(String string){
		try {
			setString(string,Color.red);
	        writer.write("\r\nErr�� " + string);
			writer.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public static void appendInfo(String string){
		try {
			setString(string,Color.black);
	        writer.write("\r\nInfo�� " + string);
			writer.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public static void appendTest(String string){
		try {
			setString(string,Color.blue);
	        writer.write("\r\nTest�� " + string);
			writer.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	static int len =0;
	public static void appendShow(String string) {
		setString(string,Color.green);
		len = string.length();
	}
	public static void setString(String string,Color color) {
		if(jtp != null){
	        Style def = Log.jtp.getStyledDocument().addStyle(null, null);
	        Style s = Log.jtp.addStyle("red", def);
	        StyleConstants.setForeground(s, color);
			/*: "normal"*/
			try {
				if(len != 0){
					len += 2;
					jtp.getDocument().remove(jtp.getDocument().getLength() - len,len);
				}
				jtp.getDocument().insertString(
						jtp.getDocument().getLength(),string+"\r\n", jtp.getStyle("red"));
				jtp.setCaretPosition(jtp.getDocument().getLength()); 
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			len = 0;
		}
	}
}
