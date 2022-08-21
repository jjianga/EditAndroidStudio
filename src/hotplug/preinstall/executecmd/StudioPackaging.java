package hotplug.preinstall.executecmd;

import java.io.File;
import java.io.IOException;

import com.zhkj.code.bean.Keystore;
import com.zhkj.code.mode.ExecuteMode;
import com.zhkj.code.utils.CmdUtils;
import com.zhkj.code.utils.FileUtils;
import com.zhkj.code.utils.PinYin;
import com.zhkj.code.utils.RandomStringUtil;
import com.zhkj.log.Log;

public class StudioPackaging extends ExecuteMode {
	private Keystore CLASS_keystore;	//Ç©Ãû
	private String appShowName;
	private String baiDuChannel;
	private String versionName;
	@Override
	public void execute(String projectPath) {
		String apkName = PinYin.getFullSpell(appShowName)+ RandomStringUtil.autoImitateString("v2") +"_"+
				(baiDuChannel==null?"baiDuChannel":baiDuChannel) +"_"+ (versionName==null?"versionName":versionName);
		String cmd = CmdUtils.getCmd(projectPath, "gradlew assembleRelease");
		CmdUtils.executeCmd(cmd, null);
		try {
			FileUtils.copyFolder(new File(projectPath+"app/build/outputs/apk"), new File("produceApk/"+apkName));
		} catch (IOException e) {
			Log.appendErr("ÒÆ¶¯APk´íÎó¡¾" + projectPath + "app/build/outputs/apk");
			e.printStackTrace();
		}
		try {
			FileUtils.copyFolder(new File(CLASS_keystore.storePath), new File("produceApk/"+apkName+".jks"));
		} catch (IOException e) {
			e.printStackTrace();
			Log.appendErr("ÒÆ¶¯Ç©Ãû´íÎó¡¾" + CLASS_keystore.storePath);
		}
	}

	@Override
	public int compareTo(ExecuteMode o) {
		int i = this.rankIndex - o.rankIndex;
		return i;
	}
}
