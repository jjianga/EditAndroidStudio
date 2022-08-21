/**
 * 
 */
package com.zhkj.ui;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import com.zhkj.log.Log;
import com.zhkj.ui.project.ProjectDemo;


/**
 * @author WangLei
 * @date 	 2018��2��6��
 * @version 1.0
 */
public class AndroidStudioUnpackTool extends JFrame{

		private static final long serialVersionUID = 1L;
		static JFrame f = null;
		//�������з���
		public static void run(final int width,final int height){
			if(f == null)f = new AndroidStudioUnpackTool(width, height);
			SwingUtilities.invokeLater(new Runnable() {		
				public void run() {
					Toolkit kit=Toolkit.getDefaultToolkit();		
					f.setTitle("��׿������� (V2.0)");
					int w = (kit.getScreenSize().width - width) / 2;
					int h = (kit.getScreenSize().height - height) / 2;
					f.setLocation(w, h);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setSize(width, height);
					f.setVisible(true);
					f.setResizable(false);
				}
			});		
		}
		
		public AndroidStudioUnpackTool(int width, int height){
			//�������岼��ģʽ(����ʹ�õľ��Զ�λ)
			setLayout(null);			
			//����һ������
			Container container=getContentPane();
			
			JLabel hintl=new JLabel("��ʾ�� : ");
			hintl.setBounds(10, 50, 100, 20);
			add(hintl);
			Log.jtp = new JTextPane();
			Log.jtp.setVisible(true);
			Log.jtp.setEditable(false);
			Log.jtp.setFocusable(false);
			add(Log.jtp);
			JScrollPane hintjp=new JScrollPane(Log.jtp);
			hintjp.setBounds(80, 50, width - 100, 80);
			add(hintjp);
			
			//��ʾ
			JLabel homeLabel=new JLabel("��Ŀ���� : ");
			homeLabel.setBounds(10, 10, 100, 20);
			add(homeLabel);
			//ѡ����Ŀ
			JButton homeBtn=new JButton("������Ŀ�б�");
			homeBtn.setFocusable(false);
			homeBtn.setBounds(width - 150, 10, 115, 30);
			add(homeBtn);
			//ѡ����Ŀ����ѡ
			JComboBox<String> homeComboBox=new JComboBox<String>();
			homeComboBox.setFocusable(false);
			homeComboBox.setBounds(80, 10, width - 260, 30);
			add(homeComboBox);
			
			homeComboBox.addItemListener(new ItemListener(){
				public JScrollPane jsA = null;    
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED) {
						if(jsA!=null)container.remove(jsA);
						Object text=homeComboBox.getSelectedItem();
						if(text != null){
							JScrollPane jsA=ProjectDemo.proDemo("config/project/"+text);
							container.add(jsA);	
							this.jsA = jsA;
							container.revalidate();			
							
							//����
							Toolkit tk = Toolkit.getDefaultToolkit();  
							tk.addAWTEventListener(new AWTEventListener(){
						    	@Override
						        public void eventDispatched(AWTEvent event) {
						              if (event.getClass() == KeyEvent.class) {
						                // ��������¼��Ǽ����¼�.
						                KeyEvent keyEvent = (KeyEvent) event;
						                if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
						                    //����ʱ��Ҫ��������
						                    keyPressed(keyEvent);
						                } else if (keyEvent.getID() == KeyEvent.KEY_RELEASED) {
						                    //�ſ�ʱ��Ҫ��������
						                    keyReleased(keyEvent);
						                }
						            }
						        }
						        private void keyPressed(KeyEvent event) {
						        	      	
						        }
						        //�ɿ�ʱ��Ӧ
						        private void keyReleased(KeyEvent event) {
						        	//����tab	����
						        	if(event.getKeyCode()==9){        		
						        		JScrollBar sBar = jsA.getVerticalScrollBar();
						        		Component  com=getFocusOwner();
						        		if(com.getY()>300){
						        			sBar.setValue(com.getY()-300);
						        		}						        								       						        		
						        	}
						        }
							}, AWTEvent.KEY_EVENT_MASK);
						}
					}
				}							
			});

			homeComboBox.addMouseListener(new MouseAdapter() {  
				@Override
                public void mouseEntered(MouseEvent arg0) {
					File file=new File("config/project/");
					if(file.exists()){
						File[] fs=file.listFiles();
						for(int j = 0; j < homeComboBox.getItemCount(); j++){
							boolean b = true;
							String proName=homeComboBox.getItemAt(j);
							for (int i = 0; i < fs.length; i++) {
								if(proName.equals(fs[i].getName())){
									b = false;break;
								}
							}
							if(b){
								//homeComboBox������    fs����û��
								homeComboBox.removeItem(proName);
							}
						}
						for (int i = 0; i < fs.length; i++) {
							String proName=fs[i].getName();
							boolean b = true;
							for(int j = 0; j < homeComboBox.getItemCount(); j++){
								if(proName.equals(homeComboBox.getItemAt(j))){
									b = false;break;
								}
							}
							if(b){
								//fs������    homeComboBox����û��
								homeComboBox.addItem(proName);
							}
						}
					}	
                }
             });
			Component xx=homeComboBox.getComponent(0);
			xx.addMouseListener(new MouseAdapter() {  
				@Override
                public void mouseEntered(MouseEvent arg0) {
					File file=new File("config/project/");
					if(file.exists()){
						File[] fs=file.listFiles();
						for(int j = 0; j < homeComboBox.getItemCount(); j++){
							boolean b = true;
							String proName=homeComboBox.getItemAt(j);
							for (int i = 0; i < fs.length; i++) {
								if(proName.equals(fs[i].getName())){
									b = false;break;
								}
							}
							if(b){
								//homeComboBox������    fs����û��
								homeComboBox.removeItem(proName);
							}
						}
						for (int i = 0; i < fs.length; i++) {
							String proName=fs[i].getName();
							boolean b = true;
							for(int j = 0; j < homeComboBox.getItemCount(); j++){
								if(proName.equals(homeComboBox.getItemAt(j))){
									b = false;break;
								}
							}
							if(b){
								//fs������    homeComboBox����û��
								homeComboBox.addItem(proName);
							}
						}
					}	
                }
             });
			//���°�ť,ѡ����Ŀ		 
			homeBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					homeComboBox.removeAllItems();
					File file=new File("config/project/");
					if(file.exists()){
						File[] fs=file.listFiles();
						for (int i = 0; i < fs.length; i++) {
							String proName=fs[i].getName();
							homeComboBox.addItem(proName);												
						}
					}									
				}
			});			
		}
		
		public static void main(String[] args) {
			run(600,800);
		}
}
