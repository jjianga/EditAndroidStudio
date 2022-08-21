package com.game.plug.utils;

import java.util.StringTokenizer;

public class EncryptUtils {
	public static String deciphering(String token) {
		try {
			String name = new String();
			StringTokenizer stringTokenizer = new StringTokenizer(
					token, "%");
			while (stringTokenizer.hasMoreElements()) {
				int asc = Integer.parseInt((String) stringTokenizer.nextElement()) - 27;
				name = name + (char) asc;
			}
			return name;
		} catch (Exception e) {
			return null;
		}
	}
}
