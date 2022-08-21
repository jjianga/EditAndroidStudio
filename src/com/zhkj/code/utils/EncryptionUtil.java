package com.zhkj.code.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class EncryptionUtil {

	private static final char[] base64EncodeChars = new char[]{
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z', '0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9', '+', '/'};

	private static final byte[] base64DecodeChars = new byte[]{
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
        52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
        -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
        15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
        -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
        41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};
	
	
	public static byte[] decode(String str) throws Exception {
        StringBuilder sb = new StringBuilder();
        byte[] data = str.getBytes(StandardCharsets.US_ASCII);
        int len = data.length;
        int i = 0;
        int b1, b2, b3, b4;
        while (i < len) {
           
            do {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1) break;
           
            do {
                b2 = base64DecodeChars
                        [data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1) break;
            sb.append((char) ((b1 << 2) | ((b2 & 0x30) >>> 4)));
           
            do {
                b3 = data[i++];
                if (b3 == 61) return sb.toString().getBytes("iso8859-1");
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1) break;
            sb.append((char) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));
           
            do {
                b4 = data[i++];
                if (b4 == 61) return sb.toString().getBytes("iso8859-1");
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1) break;
            sb.append((char) (((b3 & 0x03) << 6) | b4));
        }
        return sb.toString().getBytes("iso8859-1");
    }
	/**
	 * 加密String文件专用
	 * @param string string
	 * @return String
	 */
	public static String encode(String string){
		byte[] data;
        data = string.getBytes(StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }
	
	// 加密
		public static String encrypt(String ssoToken) {
			try {
				byte[] _ssoToken = ssoToken.getBytes(StandardCharsets.UTF_8);
				StringBuilder name = new StringBuilder();
				// char[] _ssoToken = ssoToken.toCharArray();
				for (int i = 0; i < _ssoToken.length; i++) {
					int asc = _ssoToken[i];
					_ssoToken[i] = (byte) (asc + 27);
					name.append(asc + 27).append("%");
				}
				return name.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		// 解密
		public static String decrypt(String ssoToken) {
			try {
				StringBuilder name = new StringBuilder();
				java.util.StringTokenizer st = new java.util.StringTokenizer(
						ssoToken, "%");
				while (st.hasMoreElements()) {
					int asc = Integer.parseInt((String) st.nextElement()) - 27;
					name.append((char) asc);
				}

				return name.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
public static void main(String[] args) {
	System.out.println(encrypt("2018-03-16 09:56:17"));
	/*System.out.println(decrypt("126%138%136%73%130%124%136%128%73%139%135%144%130%73%126%135%132%128%137%143%73%96%126%147%145%141%130%128%"));
	System.out.println(decrypt("142%126%142%126%124%142%129%145%124%142%129%145%124%142%"));
	System.out.println(decrypt("126%138%136%73%130%124%136%128%73%139%135%144%130%73%126%135%132%128%137%143%73%96%126%147%145%141%130%128%"));
	System.out.println(decrypt("124%"));
	System.out.println(decrypt("126%138%136%73%128%147%124%136%139%135%128%73%130%124%136%128%139%135%144%130%"));
	System.out.println(decrypt("125%125%125%130%130%"));
	System.out.println(decrypt("125%125%135%124%134%"));
	System.out.println(decrypt("129%135%132%126%134%128%141%"));*/
	System.out.println(decrypt("87%90%147%136%135%59%145%128%141%142%132%138%137%88%61%76%73%75%61%59%128%137%126%138%127%132%137%130%88%61%144%143%129%72%83%61%90%89%"));
	
}
			
		
}
