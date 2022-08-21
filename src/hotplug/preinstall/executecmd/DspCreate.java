package hotplug.preinstall.executecmd;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.zhkj.code.mode.ExecuteMode;
import com.zhkj.code.utils.CmdUtils;
import com.zhkj.code.utils.EncryptionUtil;
import com.zhkj.code.utils.FileUtils;
import com.zhkj.log.Log;

public class DspCreate extends ExecuteMode {
	/*
	 * 
dspImgPath|选择文件夹|1.请选择替换的图片路径|选择目录
dspUrl|标题和输入框|2.请填写DSPUrl
dspTimeNodes|获取时间|3.请选择时间节点|获取时间
	 * */
	private String dspImgPath;//	= "替换的图片路径"
	private String dspUrl;// 		= "DspUrl"
	private String dateNode;// 		= "时间节点";
	private String saveDspName;
	private String dspStringName;//= "保存加密文件名字"
	private String _assetsPath;		//资源
	

	private String dspApkPath;	//未签名的 apk 目录
	@Override
	public int compareTo(ExecuteMode o) {
		return this.rankIndex - o.rankIndex;
	}
	@Override
	public void init(HashMap<String, Object> map) {
		super.init(map);
		dspApkPath = (String) map.get("dspApkPath");
	}
	
	@Override
	public void execute(String projectPath) {
		try {
			Log.appendInfo("DSP开始打包！");
			//替换DSP图片  /Dsp/res/drawable/bblak.jpg
			FileUtils.copyFolder(new File(dspImgPath), new File( "DSP/res/drawable/bblak.jpg"));
			//生成加密文件
			StringBuffer strBuf = new StringBuffer();
			strBuf.append(EncryptionUtil.encrypt("url")).append("=");
			strBuf.append("\""+EncryptionUtil.encrypt(dspUrl) + "\";\r\n"); 
			
			strBuf.append(EncryptionUtil.encrypt("fileName")).append("=");
			strBuf.append("\""+EncryptionUtil.encrypt(saveDspName) + "\";\r\n"); 
			
			strBuf.append(EncryptionUtil.encrypt("Entrance")).append("=");
			strBuf.append("\""+EncryptionUtil.
					encrypt("com.game.plug.client.Ecxvrge") + "\";\r\n"); 
			//Entrance Ecxvrge
			strBuf.append(EncryptionUtil.encrypt("Method")).append("=");
			strBuf.append("\""+EncryptionUtil.encrypt("a") + "\";\r\n"); 
			
			strBuf.append(EncryptionUtil.encrypt("APKPage")).append("=");
			strBuf.append("\""+EncryptionUtil.encrypt("com.example.gameplug") + "\";\r\n"); 
			
			strBuf.append(EncryptionUtil.encrypt("Picture1")).append("=");
			strBuf.append("\""+EncryptionUtil.encrypt("bbbgg") + "\";\r\n"); 
			
			strBuf.append(EncryptionUtil.encrypt("Picture2")).append("=");
			strBuf.append("\""+EncryptionUtil.encrypt("bblak") + "\";\r\n"); 
			
			strBuf.append(EncryptionUtil.encrypt("Picture3")).append("=");
			strBuf.append("\""+EncryptionUtil.encrypt("flicker") + "\";\r\n"); 
			
			strBuf.append(EncryptionUtil.encrypt("targetDate")).append("=");
			strBuf.append("\""+EncryptionUtil.encrypt(dateNode) + "\";\r\n"); 
			//获取cmd 执行脚本命令
			String cmd = CmdUtils.getCmdBat("执行ant.bat","DSP","Dsp");
			Map<String,Object> map = new HashMap<String ,Object>();
			map.put("No key.store and key.alias properties", true);
			CmdUtils.executeCmd(cmd, map);
			FileUtils.copyFolder(new File(dspApkPath), 
					new File(_assetsPath + saveDspName));
			FileUtils.saveFile(new File(_assetsPath + dspStringName), strBuf, "UTF-8");
			
			Log.appendInfo("DSP路径【"+_assetsPath + saveDspName);
			Log.appendInfo("DSPString路径【"+_assetsPath + dspStringName);
		} catch (Exception e) {
			Log.appendErr("DSP 打包失败！" + new File( "DSP/res/drawable/bblak.jpg").getAbsolutePath());
		}
	
	}
}
