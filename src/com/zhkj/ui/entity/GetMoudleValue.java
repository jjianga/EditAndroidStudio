/**
 * 
 */
package com.zhkj.ui.entity;
import java.util.Map;
import javax.swing.JTextField;

/**
 * @author WangLei
 * @date 	 2018��2��7��
 * @version 1.0
 */
public class GetMoudleValue {
	
	/**
	 * APPǩ��
	 */
	//keyStore����
	private String appKeyStoreAlias;
	//keyStore����
	private String appKeyStorePwd;
	//��֯����
	private String appTeamPassWord;
	
	/**
	 * 
	 */
	private Map<String,JTextField> map;
	public String getAppKeyStoreAlias() {
		return appKeyStoreAlias;
	}
	public void setAppKeyStoreAlias(String appKeyStoreAlias) {
		this.appKeyStoreAlias = appKeyStoreAlias;
	}
	public String getAppKeyStorePwd() {
		return appKeyStorePwd;
	}
	public void setAppKeyStorePwd(String appKeyStorePwd) {
		this.appKeyStorePwd = appKeyStorePwd;
	}
	public String getAppTeamPassWord() {
		return appTeamPassWord;
	}
	public void setAppTeamPassWord(String appTeamPassWord) {
		this.appTeamPassWord = appTeamPassWord;
	}
	public Map<String, JTextField> getMap() {
		return map;
	}
	public void setMap(Map<String, JTextField> map) {
		this.map = map;
	}

	

	
	
	
}
