package com.zhkj.code.bean;

import java.io.File;

import com.zhkj.code.utils.RandomStringUtil;
import com.zhkj.log.Log;
/**
 * 签名 配置类
 * @author Administrator
 *
 */
public class Keystore {
	public String storePath;
	public String storePassWord;
	public String alias;
	public String aliasPassWord;

	public Keystore(String storePath,String alias,  String storePassWord, String aliasPassWord) {
		super();
		this.storePath = storePath;
		this.storePassWord = storePassWord;
		this.alias = alias;
		this.aliasPassWord = aliasPassWord;
		creartKeystore();
	}
	/**
	 * 创建秘钥
	 */
	public void creartKeystore() {
		try {
			if(storePath.lastIndexOf(".keystore") < 0
					&& storePath.lastIndexOf(".jks") < 0){
				File path = new File("keystore");
				path.mkdirs();
				this.storePath = path.getAbsolutePath().replaceAll("\\\\", "/") +"/"
						+ RandomStringUtil.randomLowSting(5) + ".keystore";
				String keyExc = "keytool -genkey"
						+ " -alias " + alias 
						+ " -keyalg RSA -validity 20000  -keystore " + storePath
						+ " -keypass " + aliasPassWord
						+ " -storepass " + storePassWord
						+ " -dname \"CN=yj, OU=zhkj, O=zhkj, L=cs, ST=hn, C=CN\"";
				Log.appendInfo("Keystore证书创建：" + storePath);
					Runtime.getRuntime().exec(keyExc);
			}
			Log.appendInfo("Keystore证书：" + storePath);
		} catch (Exception e) {
			Log.appendErr("证书创建失败【" + toString());
		}
	}
	@Override
	public String toString() {
		return "Keystore [storePath=" + storePath + ", storePassWord=" + storePassWord + ", alias=" + alias
				+ ", aliasPassWord=" + aliasPassWord + "]";
	}
	
}
