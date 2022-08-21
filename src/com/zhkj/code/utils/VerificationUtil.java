package com.zhkj.code.utils;

import java.util.List;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.bean.ClazzPackageTrait;

/**
 * 验证 List<ClazzObjctTrait>等 是否存在重复的数据 等规则
 * 
 * @author Administrator
 *
 */
public class VerificationUtil {
	/**
	 * clazzObjctTraitThis 是否引用了 类名等于 clazzName 的 clazzObjctTrait
	 * 
	 * @param clazzName
	 * @param clazzObjctTraitThis
	 * @param clazzObjctTrait
	 * @return
	 */
	public static boolean isQuoteClazz(String clazzName, ClazzObjectTrait clazzObjctTraitThis,
			ClazzObjectTrait clazzObjctTrait) {
		// 类名不匹配 没有引用关系
		if (clazzName == null)
			return false;
		if (!clazzName.equals(clazzObjctTrait.getClazzName())) {
			return false;
		}
		List<ClazzPackageTrait> yinYongDe = clazzObjctTraitThis.getClazzPackageTraitS();
		// 被引用的路径
		String packageName = clazzObjctTrait.getPackageName() + "." + clazzObjctTrait.getClazzName();
		// 查找显式引用 如果有 返回 判断引用关系结果
		for (ClazzPackageTrait clazzPackageTrait : yinYongDe) {
			// 显示引用的类
			String clazz = clazzPackageTrait.getPackagePath();
			clazz = clazz.substring(clazz.lastIndexOf(".") + 1, clazz.length());
			// 类名相同
			if (clazz.equals(clazzName)) {
				// 引用路径一致 则是引用关系
				if (clazzPackageTrait.getPackagePath().equals(packageName)) {
					return true;
				}else{
					return false;
				}
			}
		}
		// 没有顯式引用 就判断 是否 同包名
		if (clazzObjctTraitThis.getPackageName().equals(clazzObjctTrait.getPackageName())) {
			//读取该类的文件
			return true;
		}
		return false;
	}

	/**
	 * 字符串是否为空
	 */
	public static boolean isBlankString(String string) {
		return string == null || string.isEmpty();
	}

	/**
	 * 字符串是否不为空
	 */
	public static boolean isNotBlankString(String string) {
		return string != null && string.length() > 0;
	}

}
