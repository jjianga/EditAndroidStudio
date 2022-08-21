package hotplug.preinstall.redeJavaFile;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ClazzMethodTrait;
import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.mode.ReadJavaFileMode;
/**
 * ��ȡ���� ����ģ��  ����������
 * 1.class����
 * 2.{ ��β    
 * 3. ȥ�ո�
 * @author Administrator
 *
 */
public class ReadClazzMethod implements ReadJavaFileMode {
	//���Բ��� �޸� ��Ҫ
	ReadClazzParam analysisClazzParamCode = new ReadClazzParam();
	@Override
	public void readJavaCodeToClazzObject(String javaCode, ClazzObjectTrait clazzObjectTrait) {
		Pattern pattern = Pattern.compile(
				"^(\\s*@\\w+|\\s*@\\w+\\(\"\\w+\"\\)|\\s*@\\w+\\([\\w+\\.*]*\\))*\\s*" +	//ע���
				"(public|private|protected)?(\\s+static)?(\\s+final)?" + //���β���
				"(\\s+\\w+|\\s+\\w+<.*?>)?\\s+(\\w+)"+//����ֵ    ������
				"\\s*\\((.*?)\\)\\s*\\{(.*)\\}");
		Matcher matcher = pattern.matcher(javaCode);
		List<ClazzMethodTrait> clazzMethodTraitS = clazzObjectTrait.getClazzMethodTraitS();
		while(matcher.find()){
			ClazzMethodTrait clazzMethodTrait = new ClazzMethodTrait();
//			System.out.println("ͷ����"+matcher.group(1)+"���Σ�"+matcher.group(2));
//			System.out.println("��̬��"+matcher.group(3)+("������"+matcher.group(4));
//			System.out.println("����ֵ��"+matcher.group(5)+"�ҵ�������"+matcher.group(6));
//			System.out.println("�ҵ�������"+matcher.group(7)+"�����壺"+matcher.group(8));
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
