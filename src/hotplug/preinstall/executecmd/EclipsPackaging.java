package hotplug.preinstall.executecmd;

import java.io.InputStream;

import com.zhkj.code.mode.ExecuteMode;
import com.zhkj.code.utils.CmdUtils;
import com.zhkj.code.utils.PinYin;
import com.zhkj.code.utils.RandomStringUtil;
import com.zhkj.log.Log;

public class EclipsPackaging extends ExecuteMode {
	private String appShowName;
	private String baiDuChannel;
	private String versionName;
	@Override
	public void execute(String projectPath) {
		String apkName = PinYin.getFullSpell(appShowName)+ RandomStringUtil.autoImitateString("v2") +"_"+
				(baiDuChannel==null?"baiDuChannel":baiDuChannel) +"_"+ (versionName==null?"versionName":versionName);
		String cmd = CmdUtils.getCmdBat("ִ��ant.bat", apkName , projectPath);
		int i = 0;
        try {
            Process ps = Runtime.getRuntime().exec(cmd);
            InputStream inputStream = ps.getInputStream();
            byte[] by = new byte[1024];
            while (inputStream.read(by) != -1) {
            	String str = new String(by,"GBK");
				if(str.contains("SUCCESSFUL")){
		            inputStream.close();
		            ps.waitFor();
		            Log.appendInfo(" ����ɹ�..."); 
		         } else if(str.contains("build.xml does not exist")){
		            inputStream.close();
		            ps.waitFor();
					Log.appendErr("���ʧ����Ŀ����build.xml does not exist");
		         } else if(str.contains("Password verification failed")){
		            inputStream.close();
		            ps.waitFor();
					Log.appendErr("���ʧ����Ŀ����Password verification failed");
		         } else if(str.contains("No key.store and key.alias properties")){
		            inputStream.close();
		            ps.waitFor();
					Log.appendErr(" ���ʧ����Ŀ����No key.store and key.alias properties");
		         }
				switch(i++%5){
					case 0:Log.appendShow(" �����  ��");break;
					case 1:Log.appendShow(" �����  Ҫ");break;
					case 2:Log.appendShow(" �����  ��");break;
					case 3:Log.appendShow(" �����  ��");break;
					case 4:Log.appendShow(" ����� ��");break;
				}
				
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
	}

	@Override
	public int compareTo(ExecuteMode o) {
		int i = this.rankIndex - o.rankIndex;
		return i;
	}
}
