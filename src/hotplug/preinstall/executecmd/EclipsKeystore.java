package hotplug.preinstall.executecmd;

import java.io.File;
import java.io.FileOutputStream;

import com.zhkj.code.bean.Keystore;
import com.zhkj.code.mode.ExecuteMode;
import com.zhkj.log.Log;

public class EclipsKeystore extends ExecuteMode {
	private Keystore CLASS_keystore;	//ǩ��
	@Override
	public void execute(String projectPath) {
		File file = new File(projectPath + "ant.properties");
		String string = "key.store=" + CLASS_keystore.storePath + "\r\n";
		string += "key.store.password=" + CLASS_keystore.storePassWord + "\r\n";
		string += "key.alias=" + CLASS_keystore.alias + "\r\n";
		string += "key.alias.password=" + CLASS_keystore.aliasPassWord + "\r\n";
		try {
			Log.appendInfo("֤���������ļ�������" + projectPath + "ant.properties");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fis = new FileOutputStream(file);
			fis.write(string.getBytes());
			fis.close();
		} catch (Exception e) {
			Log.appendErr("֤���������ļ�����ʧ�ܣ�");
		} 

	}
	
	@Override
	public int compareTo(ExecuteMode o) {
		int i = this.rankIndex - o.rankIndex;
		return i;
	}
}
