package hotplug.preinstall.executecmd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.zhkj.code.bean.Keystore;
import com.zhkj.code.mode.ExecuteMode;
import com.zhkj.code.utils.PinYin;
import com.zhkj.code.utils.RandomStringUtil;
import com.zhkj.log.Log;

public class EclipsKeystoreV2 extends ExecuteMode {
	private Keystore CLASS_keystore;	//签名
	private String appShowName;
	private String baiDuChannel;
	private String versionName;
	@Override
	public void execute(String projectPath) {
		String apkName = PinYin.getFullSpell(appShowName)+ RandomStringUtil.autoImitateString("v2")+"_"+
				(baiDuChannel==null?"":baiDuChannel) +"_"+ (versionName==null?"versionName":versionName);
		String keyExc = "java -jar lib/apksigner.jar sign --ks "+ CLASS_keystore.storePath 
				+ " --ks-key-alias " + CLASS_keystore.alias 
				+ " --ks-pass pass:" + CLASS_keystore.storePassWord
				+ " --key-pass pass:" + CLASS_keystore.aliasPassWord
				+ " --out " + "produceApk/" + apkName + "_v2.apk "
				+ "produceApk/" + apkName + ".apk";
		Log.appendInfo("v2命令：" + keyExc);
		try {
			Runtime.getRuntime().exec(keyExc);
		} catch (IOException e) {
			Log.appendErr("Keystore证书创建失败！");
		}
	}

	/**
	 * 生成秘钥配置文件
	 * @param producePath  ant.properties 存放目录
	 */
	public void creartAntProperties(String producePath,Keystore keystore) {
		File file = new File(producePath + "/ant.properties");
		String string = "key.store=" + keystore.storePath + "\r\n";
		string += "key.store.password=" + keystore.storePassWord + "\r\n";
		string += "key.alias=" + keystore.alias + "\r\n";
		string += "key.alias.password=" + keystore.aliasPassWord + "\r\n";
		try {
			Log.appendInfo("证书打包配置文件创建：" + producePath + "/ant.properties");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fis = new FileOutputStream(file);
			fis.write(string.getBytes());
			fis.close();
		} catch (Exception e) {
			Log.appendErr("证书打包配置文件创建失败！");
		} 

	}

	/**
	 * 拼接CMD命令
	 * @param batName    	bat名称   
	 * @param argStrings	参数
	 * @return
	 */
	public static String getCmdBat(String batName, String... argStrings) {// /b
        String cmd = "cmd /c start /b " + batName + " ";
        if (argStrings != null && argStrings.length > 0) {
            for (String string : argStrings) {
                cmd += string + " ";
            }
        }
        return cmd;
    }

	@Override
	public int compareTo(ExecuteMode o) {
		int i = this.rankIndex - o.rankIndex;
		return i;
	}
}
