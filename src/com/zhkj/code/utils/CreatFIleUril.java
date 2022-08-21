package com.zhkj.code.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CreatFIleUril {
	static int typeLeng = 6;
	static String[] type = new String[]{"int","String","Integer","Double","double","char"};
	
	public static void counterfeitJava( String packeage, String ClazzName, String path){
		//����java�ļ�
		path += packeage.replaceAll("\\.", "\\\\")+"\\"+ClazzName+".java";
		File file = FileUtils.createOpenNewFile(path);
		// д����� ����
		StringBuffer strBf = new StringBuffer();
		strBf.append("package " + packeage + ";\r\n");
		strBf.append("public class "+ ClazzName +" {");
		//�������Ե� ����
		int nube = 1 + RandomStringUtil.random.nextInt(18);
		List<String> strs = new ArrayList<String>();
		for (int i = 0; i < nube; i++) {
			int typeIndex = RandomStringUtil.random.nextInt(typeLeng);
			String param = creatParam(strBf, typeIndex);
			//�������Ե�get set ����
			strs.add(creatGetAndSetMethod(typeIndex, param));
		}
		//д�����Ե� get set ����
		for (String string : strs) {
			strBf.append(string);
		}
		strBf.append(" }");
		//�����ļ�
		FileUtils.saveFile(file, strBf, "UTF-8");
	}
	/**
	 * ��������
	 */
	public static String creatParam(StringBuffer strBf,int typeIndex) {
			strBf.append("\r\nprivate ");
			strBf.append(type[typeIndex]);
			strBf.append(" ");
			String str = RandomStringUtil.getRandomTOabCd(5);
			strBf.append(str);
			strBf.append(";\r\n");
			return str;
	}
	
	/**
	 * ����get set ����
	 * @param typeIndex
	 * @param param
	 * @return
	 */
	public static String creatGetAndSetMethod(int typeIndex ,String param) {
		StringBuffer strBf = new StringBuffer();
		String paramToUp = RandomStringUtil.toUpperCase4Index(param, 0);
		strBf.append("\r\npublic ").append(type[typeIndex]).append(" get").append(paramToUp).append("( ){\r\n");
		strBf.append("return ").append(param).append(";\r\n}\r\n");

		strBf.append("\r\npublic void").append(" set").append(paramToUp).append("(").append(type[typeIndex])
		.append(" ").append(param).append(" ){\r\n");
		strBf.append("this.").append(param).append(" = ").append(param).append(";\r\n}\r\n");
		/*
		public String getArticleDesc() {
			return articleDesc;
		}
		public void setArticleDesc(String articleDesc) {
			this.articleDesc = articleDesc;
		}
		*/
		return strBf.toString();
	}
	
	public static void main(String[] args) {
		StringBuffer strBf = new StringBuffer();
		creatParam(strBf, 5);
		System.err.println(creatGetAndSetMethod(0, "ttttts"));
		System.out.println("aaa.ccc".replaceAll("\\.", "\\\\"));
	}
}
