package hotplug.preinstall.redeJavaFile;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.bean.ClazzParamTrait;
import com.zhkj.code.mode.ReadJavaFileMode;
import com.zhkj.code.utils.VerificationUtil;
/**
 * ��ȡ���� ����ģ��  ����������
 * 1. ��������   String a;  ���� (�ո�) ������;
 * 2.{ ��β    
 * 3. ȥ�ո�
 * @author Administrator
 *
 */
public class ReadClazzParam implements ReadJavaFileMode{
	@Override
	public void readJavaCodeToClazzObject(String javaCode, ClazzObjectTrait clazzObjectTrait) {
		List<ClazzParamTrait> clazzParamTraitS = clazzObjectTrait.getClazzParamTraitS();
		Pattern pattern = Pattern.compile(
				"^\\s*(public|private|protected)?(\\s+static)?(\\s+final)?" + //���β���
						"\\s+(\\w+)(<.*?>|\\s*\\[\\s*\\])?\\s+([\\w_0-9]+)" + 
						"(\\s*=(.*);\\s*$|\\s*;\\s*$)");
		Matcher matcher = pattern.matcher(javaCode);
		while(matcher.find()){
			String isStatic = matcher.group(2);
			String ClazzType = matcher.group(4);
			String ClazzName = matcher.group(6);
			ClazzParamTrait clazzParamTrait = new ClazzParamTrait();
			//������Ч�ȼ�      
			clazzParamTrait.setStatic(VerificationUtil.isNotBlankString(isStatic));
			clazzParamTrait.setLive(clazzObjectTrait.getState());
			clazzParamTrait.setParamName(ClazzName);
			clazzParamTrait.setParamTypeName(ClazzType);
			clazzParamTraitS.add(clazzParamTrait);
		}
	}

	@Override
	public void init(HashMap<String, Object> map) {
		
	}
}
