package com.zhkj.code.mode;

import java.util.List;

import com.zhkj.code.bean.ClazzObjectTrait;
	/**
	 * 修改模板 修改java代码！  返回修改后的数据
	 * @author Administrator
	 *
	 */
	public interface EditJavaFileMode extends Mode{
	/**
	 * 传入java代码  跟类描述相关的信息全部修改     ，  返回修改后的代码
	 * @param javaCode				//要修改的代码段
	 * @param clazzObjectTraitThis	//修改代码的类的描述
	 * @param clazzObjectTraitS		//需要修改的类的描述
	 * @return 
	 */
	public String editJavaCode(String javaCode, ClazzObjectTrait clazzObjectTraitThis, List<ClazzObjectTrait> clazzObjectTraitS);
}
