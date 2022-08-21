package hotplug.preinstall.editJavaFile;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.mode.EditJavaFileMode;

/**
 * 修改包名  引用
 * @author Administrator
 *
 */
public class EditJavaClazzPackage implements EditJavaFileMode{
	String packageName;
	String rPath;
	@Override
	public String editJavaCode(String javaCode, ClazzObjectTrait clazzObjectTraitThis,
			List<ClazzObjectTrait> clazzObjectTraitS) {
		if(javaCode.isEmpty())return javaCode;
		StringBuffer javaCodeB = new StringBuffer();
		//修改包名
		Pattern pattern = Pattern.compile("package\\s+(.*?)\\s*;");	
		Matcher matcher = pattern.matcher(javaCode);
		if(matcher.find()){
			String packageName=matcher.group(1);
			if(clazzObjectTraitThis.getPackageName().equals(packageName)){
				packageName= clazzObjectTraitThis.getPackageEditName();
				javaCodeB.append("package "+packageName+";");
				javaCode = javaCodeB.toString();
			}
		}
		
		//修改引用包名
		pattern = Pattern.compile("import\\s+(.*?)\\s*;");
		matcher = pattern.matcher(javaCode);
		if(matcher.find()){
			String importPath=matcher.group(1);
			for(ClazzObjectTrait clazzObjctTrait: clazzObjectTraitS){
				String importName1=clazzObjctTrait.getPackageName()+"."+clazzObjctTrait.getClazzName();
				int end = importPath.lastIndexOf(".");
				String importPath2=importPath.substring(0,end);
				if(importName1.equals(importPath)){
					javaCodeB.append("import ").append(clazzObjctTrait.getPackageEditName())
					.append(".").append(clazzObjctTrait.getClazzEditName()).append(";");
					javaCode = javaCodeB.toString();
				}else if(importName1.equals(importPath2)){
					//获取类路径  com.ring.cn.wed.bafen.sprite
					String clazzName = importPath.substring(end,importPath.length());
					javaCodeB.append("import ").append(clazzObjctTrait.getPackageEditName())
					.append(".").append(clazzObjctTrait.getClazzEditName())
					.append(clazzName).append(";");
					javaCode = javaCodeB.toString();
				}
			}
			pattern = Pattern.compile("import\\s+(.*?)\\.R\\s*;");
			matcher = pattern.matcher(javaCode);
			if(matcher.find()){
				javaCode = rPath;
			}
		}
		return javaCode;
	}

	@Override
	public void init(HashMap<String, Object> map) {
		rPath = "import " + packageName +".R;";
	}

}
