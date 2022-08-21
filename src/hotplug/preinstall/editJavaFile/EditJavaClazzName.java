package hotplug.preinstall.editJavaFile;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.mode.EditJavaFileMode;
import com.zhkj.code.utils.VerificationUtil;
/**
 * 修改类名！   不包括类名相似的，例如包名引用
 * @author Administrator
 *
 */
public class EditJavaClazzName implements EditJavaFileMode {

	@Override
	public String editJavaCode(String javaCode, ClazzObjectTrait clazzObjectTraitThis,
			List<ClazzObjectTrait> clazzObjectTraitS) {
		for (ClazzObjectTrait clazzObjctTrait : clazzObjectTraitS) {
			//有关联对象全部修改
			if (VerificationUtil.isQuoteClazz(clazzObjctTrait.getClazzName(),
					clazzObjectTraitThis, clazzObjctTrait)) {
				Pattern pattern = Pattern.compile("([^\\.\\w_]|^)" +
							clazzObjctTrait.getClazzName() + "([^\\w_])");
				Matcher macher = pattern.matcher(javaCode);
				StringBuffer sb = new StringBuffer();
				while (macher.find()) {
					macher.appendReplacement(sb, macher.group(1) +
							clazzObjctTrait.getClazzEditName() + macher.group(2));
				}
				macher.appendTail(sb);
				javaCode = sb.toString();
			}
		}

		Pattern pattern = Pattern.compile("new\\s+(([\\w_]+\\.)+[\\w_]+)");
		Matcher macher = pattern.matcher(javaCode);
		StringBuffer sb = new StringBuffer();
		while (macher.find()) {
			for (ClazzObjectTrait clazzObjctTrait : clazzObjectTraitS) {
				String name = macher.group(1);
				String nameTo = clazzObjctTrait.getPackageName() +"."+ clazzObjctTrait.getClazzName();
				if(name.equals(nameTo)){
					macher.appendReplacement(sb, 
							"new "+clazzObjctTrait.getPackageEditName() +"."
									+ clazzObjctTrait.getClazzEditName());
				}
			}
		}
		macher.appendTail(sb);
		javaCode = sb.toString();
		return javaCode;
	}

	@Override
	public void init(HashMap<String, Object> map) {
		
	}

}
