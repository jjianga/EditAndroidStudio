package hotplug.preinstall.redeJavaFile;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.bean.ClazzPackageTrait;
import com.zhkj.code.mode.ReadJavaFileMode;
import com.zhkj.log.Log;
/**
 * 读取类名 创建模板  不包括引用
 * 1.class修饰
 * 2.{ 结尾    
 * 3. 去空格
 * @author Administrator
 *
 */
public class ReadClazzName implements ReadJavaFileMode {
	
	@Override
	public void readJavaCodeToClazzObject(String javaCode, ClazzObjectTrait clazzObjectTrait) {
		Pattern pattern = Pattern.compile(
				".*?(class|interface|enum)\\s+?(\\w+)(<\\s*.*\\s*>)?( +?extends +?)*(\\w+<+.*?>+|\\w+)*( +?implements +?)*(.*?)\\s*?(\\{.*)");
		Matcher matcher = pattern.matcher(javaCode);
		if(matcher.find()){	
			if( clazzObjectTrait.getClazzName() != null){
				//如果是类中类，就调用扫描类中类的方法
	//			new AnalysisClazzInternalCode().javaConvertClazz(javaCode,clazzObjctTrait);
				Log.appendInfo("内部类，没处理：" + matcher.group(2));
			}else{
				clazzObjectTrait.setClazzName(matcher.group(2));
			}
			
			String absName = matcher.group(5);
			if(absName != null){
				clazzObjectTrait.setAbstractClazz(absName);
			}
			//TODO implements 实现的接口
			absName = matcher.group(7);
			if(absName != null && !"".equals(absName)){
				clazzObjectTrait.setImplementsString(absName);
			}
		}
		
		pattern = Pattern.compile("package\\s+(.*?)\\s*;");//( +extends +(.*?))|
		matcher = pattern.matcher(javaCode);
		if(matcher.find()){
//			System.out.println("找到包名："+matcher.group(1));
			clazzObjectTrait.setPackageName(matcher.group(1));
		}
		
		pattern = Pattern.compile("import\\s+(.*?)\\s*;");
		matcher = pattern.matcher(javaCode);
		if(matcher.find()){
//			System.out.println("找到引用包名："+matcher.group(1));
			clazzObjectTrait.getClazzPackageTraitS().add(
					new ClazzPackageTrait(matcher.group(1), null));
		}
	}

	@Override
	public void init(HashMap<String, Object> map) {
	}

}
