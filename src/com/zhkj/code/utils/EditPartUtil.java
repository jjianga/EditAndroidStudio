package com.zhkj.code.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ClazzObjectTrait;

public class EditPartUtil {
	/**
	 *  < >  泛型里面的参数处理
	 * @param generosity	< >
	 * @param clazzObjectTraitThis 该代码属于该类
	 * @param clazzObjectTraitS
	 * @return
	 */
	public static StringBuffer editGenerosity(String generosity,
											  ClazzObjectTrait clazzObjectTraitThis, List<ClazzObjectTrait> clazzObjectTraitS){
		StringBuffer javaCodeB = new StringBuffer();
		//去括号
		generosity = generosity.trim().substring(1,generosity.length()-1).trim();
		javaCodeB.append("<");
		//匹配 泛型里面的泛型参数
		Pattern pattern = Pattern.compile(
			"([_\\w]+)(<\\s*.*\\s*>)?(\\s*,\\s*|\\s*$)");
		Matcher matcher = pattern.matcher(generosity);
		while (matcher.find()) {
			String leiXing = matcher.group(1);
			String fanXing = matcher.group(2); 
			//类型判断
        	for(ClazzObjectTrait clazzObjctTrait : clazzObjectTraitS){
        		//是否有引用关系
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
	 * 拼接 数组 用逗号隔开    a,b,c
	 * @param arr
	 * @return
	 */
	public static StringBuffer addComma(String[] arr){
		StringBuffer javaCodeB = new StringBuffer();
		return addComma(javaCodeB,arr);
	}
	
	/**
	 * 在  javaCodeB 追加  ，拼接 数组 用逗号隔开          javaCodeB a, b, c
	 * @param javaCodeB 会改变其值
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
