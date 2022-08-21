package hotplug.preinstall.executecmd;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.Keystore;
import com.zhkj.code.mode.ExecuteMode;
import com.zhkj.code.utils.FileUtils;
import com.zhkj.log.Log;

public class StudioKeystore extends ExecuteMode {
	private Keystore CLASS_keystore;	//签名
	private String buildPath;
	public void init(java.util.HashMap<String,Object> map) {
		super.init(map);
		buildPath = (String)map.get("buildPath");
	}
 	@Override
	public void execute(String projectPath) {
 		buildPath =projectPath + buildPath;
 		Log.appendInfo("写入签名配置【" + buildPath);
 		File buildFile = new File(buildPath);
		StringBuffer strBf = FileUtils.readString( buildFile, "UTF-8");
		String patString = getSignatureString(".*?", "(.*?)", "(.*?)", "(.*?)", "(.*?)", "\\", "");
		String signatureString = getSignatureString(" ", CLASS_keystore.storePath, CLASS_keystore.alias,
				CLASS_keystore.aliasPassWord, CLASS_keystore.storePassWord, "", "\r\n");
		Pattern pattern = Pattern.compile(patString);
		Matcher matcher = pattern.matcher(strBf.toString());
		if(matcher.find()){//存在就替换
			strBf.delete(0, strBf.length());
			matcher.appendReplacement(strBf,signatureString);
			matcher.appendTail(strBf);
		}else{//不存在就创建 
			pattern = Pattern.compile("android\\s*\\{\\s*");
			matcher = pattern.matcher(strBf.toString());
			if(matcher.find()){
				strBf.delete(0, strBf.length());
				matcher.appendReplacement(strBf,"android {\r\n" + signatureString);
				matcher.appendTail(strBf);
			}else{
				Log.appendErr("签名文件创建错误【" + buildPath);
			}
		}
	}
 	
 	public String getSignatureString(String blank, String storePath, String keyAlias
 			, String keyPassword, String storePassword, String esc, String line){
 		StringBuffer strBf = new StringBuffer("signingConfigs").append(blank).append(esc).append("{").append(blank).append(line);
 		strBf.append("release").append(blank).append(esc).append("{").append(blank).append(line);
 		strBf.append("keyAlias").append(blank).append("\'").append(keyAlias).append("\'").append(blank).append(line);
 		strBf.append("keyPassword").append(blank).append("\'").append(keyPassword).append("\'").append(blank).append(line);
 		strBf.append("storeFile").append(blank).append("file").append(blank).append(esc).append("(").append("\'")
 			.append(storePath).append("\'").append(esc).append(")").append(blank).append(line);
 		strBf.append("storePassword").append(blank).append("\'").append(storePassword).append("\'").append(blank).append(line);
 		strBf.append(esc).append("}").append(blank).append(esc).append("}").append(line);
 		return strBf.toString();
 	}
 	public static void main(String[] args) {
		StudioKeystore s = new StudioKeystore();
//		System.out.println(s.getSignatureString(" ", "c:\\", "keyAlias", "keyPassword", "storePassword", "", "\r\n"));
		System.out.println(s.getSignatureString(".*?", "(.*?)", "(.*?)", "(.*?)", "(.*?)", "\\", ""));
		StringBuffer sb = FileUtils.readString(new File("build.gradle"), "utf-8");
		Pattern pattern = Pattern.compile(s.getSignatureString(".*?", "(.*?)", "(.*?)", "(.*?)", "(.*?)", "\\", "")
				, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(sb.toString());
		if(matcher.find()){
			sb.delete(0, sb.length());
			matcher.appendReplacement(sb, s.getSignatureString(" ", "c:\\", "keyAlias", "keyPassword", "storePassword", "", "\r\n"));
			matcher.appendTail(sb);
		}
		System.out.println(sb.toString());
	}
	@Override
	public int compareTo(ExecuteMode o) {
		return this.rankIndex - o.rankIndex;
	}
}
