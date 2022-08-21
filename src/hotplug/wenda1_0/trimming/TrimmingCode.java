package hotplug.wenda1_0.trimming;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.bean.ResFile;
import com.zhkj.code.mode.TrimmingMode;
import com.zhkj.code.replaceSours.ReplaceRegular;
import com.zhkj.code.utils.EncryptionUtil;
import com.zhkj.code.utils.FileUtils;
import com.zhkj.code.utils.RandomStringUtil;
import com.zhkj.log.Log;

public class TrimmingCode implements TrimmingMode{
	private String _srcPath;		//Դ��Ŀ¼
	private String fileString;		//string.xml�ļ����ļ�
	private String dspStringName;
	private String adUrl;				//��Ϣ����ȡ�����ļ�
	private String adAppName;			//��Ϣ����ȡ��������
	@Override
	public void init(HashMap<String, Object> map) {
	}
	@Override
	public void trimming(List<ClazzObjectTrait> clazzObjectTraitS, List<ResFile> resFileS, String encoding) {
		Log.appendInfo("����ʴ��޸�...");
		for (ClazzObjectTrait clazzObjctTrait : clazzObjectTraitS) {
			if("Constant".equals(clazzObjctTrait.getClazzName())){
				String path = clazzObjctTrait.getPackageEditName().replace(".", "/")+
						"/"+clazzObjctTrait.getClazzEditName()+".java";
				File file = new File(_srcPath + path);
				StringBuffer strBuf =FileUtils.readString(file, encoding);
				ReplaceRegular ReplaceRegular = null;
				ReplaceRegular = 
						new ReplaceRegular(fileString, strBuf);
				ReplaceRegular.lineExecute("UPMMSTRING\\s*=\\s*\"(.*?)\";");//����String�ļ�
				Log.appendInfo("Constant�ɹ��޸�UPMMSTRING��" + fileString);
				
				ReplaceRegular = 
						new ReplaceRegular(fileString+".xml", strBuf);
				ReplaceRegular.lineExecute("MMSTRING\\s*=\\s*\"(.*?)\";");//�����ڴ��µ�string�ļ�
				Log.appendInfo("Constant�ɹ��޸�MMSTRING��" + fileString);
				FileUtils.saveFile(file, strBuf, encoding);
			}
			if("ADConstants".equals(clazzObjctTrait.getClazzName())){
				String path = clazzObjctTrait.getPackageEditName().replace(".", "/")+
						"/"+clazzObjctTrait.getClazzEditName()+".java";
				File file = new File(_srcPath + path);
				StringBuffer strBuf =FileUtils.readString(file, encoding);
				ReplaceRegular replaceRegular = 
						new ReplaceRegular(
								EncryptionUtil.encrypt(adUrl), strBuf);
				replaceRegular.lineExecute("String\\s+Url\\s*=\\s*\"(.*?)\";");
				Log.appendInfo("ADConstants�ɹ��޸�Url��" + adUrl);

				replaceRegular = 
						new ReplaceRegular(adAppName, strBuf);
				replaceRegular.lineExecute("String\\s+APP_NAME\\s*=\\s*\"(.*?)\";");
				Log.appendInfo("ADConstants�ɹ��޸�APP_NAME��" + adAppName);
				
				FileUtils.saveFile(file, strBuf, encoding);
			}
			if("ZDApplication".equals(clazzObjctTrait.getClazzName())){
				String path = clazzObjctTrait.getPackageEditName().replace(".", "/")+
						"/"+clazzObjctTrait.getClazzEditName()+".java";
				File file = new File(_srcPath + path);
				StringBuffer strBuf =FileUtils.readString(file, encoding);
				ReplaceRegular replaceRegular = 
						new ReplaceRegular(
								EncryptionUtil.encrypt(dspStringName), strBuf);
				replaceRegular.lineExecute("assestPath\\s*=\\s*\"(.*?)\";");
				Log.appendInfo("ZDApplication�ɹ��޸�DSPString��" + dspStringName);
				FileUtils.saveFile(file, strBuf, encoding);
			}
			if("UpdateActivity".equals(clazzObjctTrait.getClazzName())){
				String path = clazzObjctTrait.getPackageEditName().replace(".", "/")+
						"/"+clazzObjctTrait.getClazzEditName()+".java";
				File file = new File(_srcPath + path);
				StringBuffer strBuf =FileUtils.readString(file, encoding);
				ReplaceRegular replaceRegular = 
						new ReplaceRegular(
								RandomStringUtil.autoImitateString("i"), strBuf);
				replaceRegular.lineExecute("\\.\\s*(i)\\s*\\(");
				
				FileUtils.saveFile(file, strBuf, encoding);
			}
			if("D".equals(clazzObjctTrait.getClazzName())){
				String path = clazzObjctTrait.getPackageEditName().replace(".", "/")+
						"/"+clazzObjctTrait.getClazzEditName()+".java";
				File file = new File(_srcPath + path);
				StringBuffer strBuf =FileUtils.readString(file, encoding);
				ReplaceRegular replaceRegular = 
						new ReplaceRegular(
								EncryptionUtil.encrypt(dspStringName), strBuf);
				replaceRegular.lineExecute("assestPath\\s*=\\s*\"(.*?)\";");
				Log.appendInfo("D�ɹ��޸�DSPString��" + dspStringName);
				
				//��������
				Pattern pattern = Pattern.compile(
						"(public|private|protected)+(\\s+static)?(\\s+final)?" + //���β���
						"(\\s+\\w+|\\s+\\w+<.*?>)+\\s+(\\w+)"+//����ֵ    ������
						"\\s*\\(");
				Matcher matcher = pattern.matcher(strBuf.toString());
				while(matcher.find()){
					String fangFaMingCheng= matcher.group(5);
					replaceRegular.setString(RandomStringUtil.autoImitateString(fangFaMingCheng));
					replaceRegular.lineExecute("\\s*("+fangFaMingCheng+")\\s*\\(");
				}
				
				//��������
				pattern = Pattern.compile("\\s*\\w+(\\s*<.*?>)\\s+(\\w+)\\s*=");
				matcher = pattern.matcher(strBuf.toString());
				while (matcher.find()) {
					String param= matcher.group(2);
					if(!"assestText".equals(param)){
					replaceRegular.setString(RandomStringUtil.autoImitateString(param));
					replaceRegular.lineExecute("\\b("+param+")\\b");}
				}
				FileUtils.saveFile(file, strBuf, encoding);
			}
			if("RTReFragment".equals(clazzObjctTrait.getClazzName())){//
				for(ClazzObjectTrait clazzObjctTraitTo : clazzObjectTraitS){
					if("OnItemClickListener".equals(clazzObjctTraitTo.getClazzName())){
						String path = clazzObjctTrait.getPackageEditName().replace(".", "/")+
								"/"+clazzObjctTrait.getClazzEditName()+".java";
						File file = new File(_srcPath + path);
						StringBuffer strBuf =FileUtils.readString(file, encoding);
						String a = clazzObjctTraitTo.getPackageEditName()+"."+
										clazzObjctTraitTo.getClazzEditName();
						String b = clazzObjctTraitTo.getPackageName()+"."+
										clazzObjctTraitTo.getClazzName();
						Log.appendInfo(b + "�� �� RTReFragment ����" + a);
						FileUtils.saveFile(file, strBuf.toString().replace(b, a), encoding);
					}
				}
			}
		}
	}
}
