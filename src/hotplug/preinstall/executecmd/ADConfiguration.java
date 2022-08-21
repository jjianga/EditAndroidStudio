package hotplug.preinstall.executecmd;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.mode.ExecuteMode;
import com.zhkj.code.utils.CmdUtils;
import com.zhkj.code.utils.FileUtils;
import com.zhkj.log.Log;

public class ADConfiguration extends ExecuteMode {
	public String dependPackagePath;
	@Override
	public int compareTo(ExecuteMode o) {
		return this.rankIndex - o.rankIndex;
	}

	@Override
	public void execute(String projectPath) {
		if (dependPackagePath == null) {
			Log.appendErr("dependPackagePath 属性未初始化" + dependPackagePath);
		}
		String path = projectPath + "project.properties";
		//检查   dependPackagePath  盘符
		
		StringBuffer strBf = FileUtils.readString(new File(path), "UTF-8");
		Pattern pattern = Pattern.compile("android.library.reference.1=(.*)");
		Matcher matcher = pattern.matcher(strBf.toString().replace("\\", "\\\\"));
		strBf.delete(0, strBf.length());
		if(matcher.find()){
			String str = matcher.group(1);
			StringBuffer reg = new StringBuffer();
			//同目录处理方式，
			if(path.charAt(0)==str.charAt(0)){
				for (int i = 0; i < path.length(); i++) {
					if('\\'==path.charAt(i)||
							'/'==path.charAt(i)){
						reg.append("../");
					}
				}
				reg.append(str.substring(3));
			}else{
			//不同目录处理方式
				File srcFile = new File(str);
				File dootFile = new File(projectPath + srcFile.getName());
				try {
					FileUtils.copyFolderIsDelete(srcFile, dootFile);
					str = dootFile.getAbsolutePath();
					Log.appendInfo("复制依赖包到【"+ str);
				} catch (IOException e) {
					e.printStackTrace();
					Log.appendErr("复制依赖包错误【"+dootFile.getAbsolutePath());
				}
				reg.append(dootFile.getName());
			}
			matcher.appendReplacement(strBf, "android.library.reference.1="+reg.toString());
			matcher.appendTail(strBf);
			FileUtils.saveFile(new File(path), strBf, "UTF-8");
			
			Map<String,Object> map = new HashMap<String ,Object>();
			map.put("please use the --subprojects parameter.", true);
			String cmd ="cmd /c cd /d "+ str +" && android update project --path .";
			CmdUtils.executeCmd(cmd, map);
			Log.appendInfo("依赖包初始化OK!");
		}else{
			Log.appendErr("依赖包没找到相关配置！");
		}
	
	}
}
