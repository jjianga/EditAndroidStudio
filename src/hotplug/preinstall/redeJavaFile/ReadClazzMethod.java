package hotplug.preinstall.redeJavaFile;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ClazzMethodTrait;
import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.mode.ReadJavaFileMode;
/**
 * 读取类名 创建模板  不包括引用
 * 1.class修饰
 * 2.{ 结尾    
 * 3. 去空格
 * @author Administrator
 *
 */
public class ReadClazzMethod implements ReadJavaFileMode {
	//属性查找 修改 需要
	ReadClazzParam analysisClazzParamCode = new ReadClazzParam();
	@Override
	public void readJavaCodeToClazzObject(String javaCode, ClazzObjectTrait clazzObjectTrait) {
		Pattern pattern = Pattern.compile(
				"^(\\s*@\\w+|\\s*@\\w+\\(\"\\w+\"\\)|\\s*@\\w+\\([\\w+\\.*]*\\))*\\s*" +	//注解等
				"(public|private|protected)?(\\s+static)?(\\s+final)?" + //修饰部分
				"(\\s+\\w+|\\s+\\w+<.*?>)?\\s+(\\w+)"+//返回值    方法名
				"\\s*\\((.*?)\\)\\s*\\{(.*)\\}");
		Matcher matcher = pattern.matcher(javaCode);
		List<ClazzMethodTrait> clazzMethodTraitS = clazzObjectTrait.getClazzMethodTraitS();
		while(matcher.find()){
			ClazzMethodTrait clazzMethodTrait = new ClazzMethodTrait();
//			System.out.println("头部："+matcher.group(1)+"修饰："+matcher.group(2));
//			System.out.println("静态："+matcher.group(3)+("常量："+matcher.group(4));
//			System.out.println("返回值："+matcher.group(5)+"找到方法："+matcher.group(6));
//			System.out.println("找到参数："+matcher.group(7)+"方法体："+matcher.group(8));
//			System.out.println(javaCode);
			clazzMethodTrait.setReturnValue(matcher.group(5));
			clazzMethodTrait.setMethodName(matcher.group(6));
			clazzMethodTrait.setClazzParamTraitStr(matcher.group(7));
			clazzMethodTraitS.add(clazzMethodTrait);
		}
	}

	@Override
	public void init(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		
	}
}
