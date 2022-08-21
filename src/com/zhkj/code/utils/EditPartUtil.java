package com.zhkj.code.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ClazzObjectTrait;

public class EditPartUtil {
	/**
	 *  < >  ��������Ĳ�������
	 * @param generosity	< >
	 * @param clazzObjectTraitThis �ô������ڸ���
	 * @param clazzObjectTraitS
	 * @return
	 */
	public static StringBuffer editGenerosity(String generosity,
											  ClazzObjectTrait clazzObjectTraitThis, List<ClazzObjectTrait> clazzObjectTraitS){
		StringBuffer javaCodeB = new StringBuffer();
		//ȥ����
		generosity = generosity.trim().substring(1,generosity.length()-1).trim();
		javaCodeB.append("<");
		//ƥ�� ��������ķ��Ͳ���
		Pattern pattern = Pattern.compile(
			"([_\\w]+)(<\\s*.*\\s*>)?(\\s*,\\s*|\\s*$)");
		Matcher matcher = pattern.matcher(generosity);
		while (matcher.find()) {
			String leiXing = matcher.group(1);
			String fanXing = matcher.group(2); 
			//�����ж�
        	for(ClazzObjectTrait clazzObjctTrait : clazzObjectTraitS){
        		//�Ƿ������ù�ϵ
        		//System.out.println(clazzObjctTrait.getClazzName() + " s  " + leiXing);
    			if(VerificationUtil.isQuoteClazz(leiXing,clazzObjectTraitThis, clazzObjctTrait)){
    				leiXing = clazzObjctTrait.getClazzEditName();
    				break;
    			}
        	}
			javaCodeB.append(leiXing);
			if(VerificationUtil.isNotBlankString(fanXing)){
				javaCodeB.append(editGenerosity(fanXing,clazzObjectTraitThis,clazzObjectTraitS));
			}
        	if(VerificationUtil.isNotBlankString(matcher.group(3)))
        		javaCodeB.append(",");
		}
		javaCodeB.append(">" );
		return javaCodeB;
	}
	
	/**
	 * ƴ�� ���� �ö��Ÿ���    a,b,c
	 * @param arr
	 * @return
	 */
	public static StringBuffer addComma(String[] arr){
		StringBuffer javaCodeB = new StringBuffer();
		return addComma(javaCodeB,arr);
	}
	
	/**
	 * ��  javaCodeB ׷��  ��ƴ�� ���� �ö��Ÿ���          javaCodeB a, b, c
	 * @param javaCodeB ��ı���ֵ
	 * @param arr
	 * @return
	 */
	public static StringBuffer addComma(StringBuffer javaCodeB,String[] arr){
		for (int i = 0; i < arr.length-1; i++) {
			javaCodeB.append(arr[i]).append(",");
		}
		javaCodeB.append(arr[arr.length-1]);
		return javaCodeB;
	}
}
