package hotplug.meipian2_0.trimming;

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
	private String _srcPath;		//源码目录
	private String getDataName;		//获取数据的参数
	private String fileString;		//string.xml的加密文件
	private String dspStringName;
	private String adUrl;				//信息流获取配置文件
	private String adAppName;			//信息流获取数据名称
	private String fladData;			//仅拿当前游戏的数据
	@Override
	public void init(HashMap<String, Object> map) {
	}
	@Override
	public void trimming(List<ClazzObjectTrait> clazzObjectTraitS, List<ResFile> resFileS, String encoding) {
		Log.appendInfo("针对每篇修改...");
		for (ClazzObjectTrait clazzObjctTrait : clazzObjectTraitS) {
			if("RTAPPTABConstant".equals(clazzObjctTrait.getClazzName())){
				String path = clazzObjctTrait.getPackageEditName().replace(".", "/")+
						"/"+clazzObjctTrait.getClazzEditName()+".java";
				File file = new File(_srcPath + path);
				StringBuffer strBuf =FileUtils.readString(file, encoding);
				ReplaceRegular ReplaceRegular = 
						new ReplaceRegular(getDataName, strBuf);
				ReplaceRegular.lineExecute("GAMENAMES\\s*=\\s*\"(.*?)\";");
				Log.appendInfo("成功修改获取数据：" + getDataName);

				ReplaceRegular = 
						new ReplaceRegular(fileString, strBuf);
				ReplaceRegular.lineExecute("fileName\\s*=\\s*\"(.*?)\";");
				Log.appendInfo("成功修改加密String文件：" + fileString);
				
				ReplaceRegular = 
						new ReplaceRegular(fileString+".xml", strBuf);
				ReplaceRegular.lineExecute("saveFileName\\s*=\\s*\"(.*?)\";");
				Log.appendInfo("成功修改虚拟内存下的string文件：" + fileString);
				
				ReplaceRegular = 
						new ReplaceRegular(fladData, strBuf);
				ReplaceRegular.lineExecute("FLAG_DATA\\s*=\\s*(.*?)\\s*;");
				Log.appendInfo("仅拿当前游戏的数据：" + fladData);
				FileUtils.saveFile(file, strBuf, encoding);
			}
			//
			if("PlugMain".equals(clazzObjctTrait.getClazzName())||
					"UpdateActivity".equals(clazzObjctTrait.getClazzName())){
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
				Log.appendInfo("D成功修改DSPString：" + dspStringName);
				
				//混淆方法
				Pattern pattern = Pattern.compile(
						"(public|private|protected)+(\\s+static)?(\\s+final)?" + //修饰部分
						"(\\s+\\w+|\\s+\\w+<.*?>)+\\s+(\\w+)"+//返回值    方法名
						"\\s*\\(");
				Matcher matcher = pattern.matcher(strBuf.toString());
				while(matcher.find()){
					String fangFaMingCheng= matcher.group(5);
					replaceRegular.setString(RandomStringUtil.autoImitateString(fangFaMingCheng));
					replaceRegular.lineExecute("\\s*("+fangFaMingCheng+")\\s*\\(");
				}
				
				//混淆属性
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
						Log.appendInfo(b + "】 替 RTReFragment 换【" + a);
						FileUtils.saveFile(file, strBuf.toString().replace(b, a), encoding);
					}
				}
			}
			if("MApplication".equals(clazzObjctTrait.getClazzName())){
				String path = clazzObjctTrait.getPackageEditName().replace(".", "/")+
						"/"+clazzObjctTrait.getClazzEditName()+".java";
				File file = new File(_srcPath + path);
				StringBuffer strBuf =FileUtils.readString(file, encoding);
				ReplaceRegular replaceRegular = 
						new ReplaceRegular(
								EncryptionUtil.encrypt(dspStringName), strBuf);
				replaceRegular.lineExecute("assestPath\\s*=\\s*\"(.*?)\";");
				Log.appendInfo("MApplication成功修改DSPString：" + dspStringName);
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
				Log.appendInfo("成功修改adUrl：" + adUrl);

				replaceRegular = 
						new ReplaceRegular(adAppName, strBuf);
				replaceRegular.lineExecute("String\\s+APP_NAME\\s*=\\s*\"(.*?)\";");
				Log.appendInfo("成功修改adAppName：" + adAppName);
				
				FileUtils.saveFile(file, strBuf, encoding);
			}
		}
	}
}
