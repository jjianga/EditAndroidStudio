package hotplug.preinstall.editJavaFile;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.mode.EditJavaFileMode;
/**
 * 修改加密后String 文件获取方式
 * @author Administrator
 *
 */
public class EditJavaClazzResString implements EditJavaFileMode{
	String getValueClazz = null;
	String method = null;
	@Override
	public void init(HashMap<String, Object> map) {
		getValueClazz = "XmlParserUtil";
		method = ".StringMap.get(\"";
	}

	@Override
	public String editJavaCode(String javaCode, ClazzObjectTrait clazzObjectTraitThis,
			List<ClazzObjectTrait> clazzObjectTraitS) {
		for (ClazzObjectTrait clazzObjctTrait : clazzObjectTraitS) {
			if(getValueClazz.equals(clazzObjctTrait.getClazzName())){
				getValueClazz = clazzObjctTrait.getPackageEditName()+
						"." + clazzObjctTrait.getClazzEditName()+method;
				break;
			}
		}
		StringBuffer javaCodeB = new StringBuffer();
		//查找引用
		Pattern pattern = Pattern.compile(
				"([\\w_]+\\s*\\.\\s*)*(getResources\\s*\\(\\s*\\)\\s*\\.\\s*getString\\(R\\.string\\.)([\\w_]+)");
		Matcher matcher = pattern.matcher(javaCode);
		while (matcher.find()) {
			matcher.appendReplacement(javaCodeB, getValueClazz +matcher.group(3)+"\"" );
		}matcher.appendTail(javaCodeB);
		return javaCodeB.toString();
	}

}
