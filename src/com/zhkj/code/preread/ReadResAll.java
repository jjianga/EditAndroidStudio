package com.zhkj.code.preread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.zhkj.code.bean.ResFile;
import com.zhkj.code.mode.ReadResFileMode;
import com.zhkj.code.utils.ReflectGetClazzUtils;

/**
 * ��ȡ����Ϣ ��  ��������Ϣ  ����
 * @author Administrator
 *
 */
public class ReadResAll {
	public static List<ResFile> resFileS;
	public static ReadResAll init(){
		resFileS  =  new ArrayList<ResFile>();
		return new ReadResAll();
	}
	/**
	 * �Ķ�Դ�ļ���	��ȡ����xml�ļ���  ��ȡ��������Ϣ���޸ĵĲ��֣�
	 * @param path		����Ŀ¼
	 * @param encoding	xml���뷽ʽ
	 * @param readResFileModeS	����ģ��ʵ��
	 * @return
	 */
	public void resFolder(String path, String encoding,List<ReadResFileMode> readResFileModeS){
		resFolder(new File(path), encoding,readResFileModeS);
	}
	public static void resFolder(File file,String encoding,List<ReadResFileMode> redeResFileModeS){
		//��ȡ��Դ�ļ�
		if  (!file .isDirectory()){     
			for (ReadResFileMode redeResFileMode : redeResFileModeS) {
				ResFile resFile = redeResFileMode.resResFile(file, encoding);
				if(resFile != null){resFileS.add(resFile);}
			}
		} else{
			// TODO �Ķ��¼�Ŀ¼
			for (File files : file.listFiles()) {
				resFolder(files, encoding,redeResFileModeS);
			}
		} 
	}
	
	public static void main(String[] args) {
		List<ReadResFileMode> redeResFileMode
			= ReflectGetClazzUtils.getClazzS(
				"D:\\lyf\\EditAndroidStudio\\src\\hotplug\\meipian\\readResFile\\");
		ReadResAll.init().resFolder("D:\\dabao\\GameBase_jdqs_base\\res\\", "UTF-8",redeResFileMode);
		int i = ReadResAll.resFileS.size();
		for (ResFile res : ReadResAll.resFileS) {
			System.out.println(res.getType());
		}
		System.out.println("�ܼƣ�" + i);
	}
}
