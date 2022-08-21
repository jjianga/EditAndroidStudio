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
dspImgPath|ѡ���ļ���|1.��ѡ���滻��ͼƬ·��|ѡ��Ŀ¼
dspUrl|����������|2.����дDSPUrl
dspTimeNodes|��ȡʱ��|3.��ѡ��ʱ��ڵ�|��ȡʱ��
	 * */
	private String dspImgPath;//	= "�滻��ͼƬ·��"
	private String dspUrl;// 		= "DspUrl"
	private String dateNode;// 		= "ʱ��ڵ�";
	private String saveDspName;
	private String dspStringName;//= "��������ļ�����"
	private String _assetsPath;		//��Դ
	

	private String dspApkPath;	//δǩ���� apk Ŀ¼
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
			Log.appendInfo("DSP��ʼ�����");
			//�滻DSPͼƬ  /Dsp/res/drawable/bblak.jpg
			FileUtils.copyFolder(new File(dspImgPath), new File( "DSP/res/drawable/bblak.jpg"));
			//���ɼ����ļ�
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
			//��ȡcmd ִ�нű�����
			String cmd = CmdUtils.getCmdBat("ִ��ant.bat","DSP","Dsp");
			Map<String,Object> map = new HashMap<String ,Object>();
			map.put("No key.store and key.alias properties", true);
			CmdUtils.executeCmd(cmd, map);
			FileUtils.copyFolder(new File(dspApkPath), 
					new File(_assetsPath + saveDspName));
			FileUtils.saveFile(new File(_assetsPath + dspStringName), strBuf, "UTF-8");
			
			Log.appendInfo("DSP·����"+_assetsPath + saveDspName);
			Log.appendInfo("DSPString·����"+_assetsPath + dspStringName);
		} catch (Exception e) {
			Log.appendErr("DSP ���ʧ�ܣ�" + new File( "DSP/res/drawable/bblak.jpg").getAbsolutePath());
		}
	
	}
}
