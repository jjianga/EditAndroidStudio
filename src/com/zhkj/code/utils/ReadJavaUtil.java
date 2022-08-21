package com.zhkj.code.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DSP-ReadJavaUtil ��java�ļ��Ĺ�����
 * @author jjianga
 * @Date 2022-08-21 10:17
 * @version 1.0.0
 * @
 */
public class ReadJavaUtil {
    /**
     * ��ȡjava�ļ�
     * @param file �ļ�
     * @param encoding �����ʽ
     * @return List<String>
     */
    public static List<String> readJavaToString(File file, String encoding){
        return readJavaToString(file, encoding, true);
    }

    /**
     * ��ȡjava�ļ�
     * @param file �ļ�
     * @param encoding �����ʽ
     * @param isLine �Ƿ�������
     * @return List<String>
     */
    public static List<String> readJavaToString(File file, String encoding, boolean isLine){
        List<String> stringList = new ArrayList<>();
        try (
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);
                BufferedReader reader = new BufferedReader(read)
        ){
            StringBuffer stringBuffer = new StringBuffer();
            String javaCode;
            int bracketNumber = 0;
            boolean eLock = true;
            boolean cLock = true;
            boolean zLock = true;
            while ((javaCode = reader.readLine()) != null) {
                javaCode = javaCode.trim();
                if(VerificationUtil.isBlankString(javaCode)){continue;}
                //��б�� ������ע��
                int fanXieGang = -100;
                //�Ǻ� ������ע��
                int xingHao= -100;
                //��ʼλ��
                int startCode = 0;
                boolean fanxie =false;
                //TODO �������  ˫�����ڵĲ����� = javaCode;
                for (int k = 0; k < javaCode.length(); k++) {
                    char code = javaCode.charAt(k);
                    if(zLock){
                        if(cLock || eLock){
                            if(fanxie){
                                fanxie = false;
                                continue;
                            }
                            if('\\' == code){
                                fanxie=true;
                                continue;
                            }
                        }
                        if(cLock && '"' == code){
                            eLock = !eLock;
                            continue;
                        }
                        if(eLock && '\'' == code){
                            cLock = !cLock;
                            continue;
                        }
                        if(eLock && cLock){
                            //��¼б�ܳ���
                            if('/' == code){
                                if(k == fanXieGang+1){//ȥ��ע��
                                    javaCode = javaCode.substring(startCode,k-1);
                                    startCode = 0;
                                    break;
                                }
                                fanXieGang = k;
                            }else if('*' == code){ //ȫ��ע��
                                if(k == fanXieGang+1){
                                    stringBuffer.append(javaCode, startCode, k-1);
                                    zLock = false;//����
                                    continue;
                                }
                            }else if('{' == code){//��������
                                bracketNumber++;
                            }else if('}' == javaCode.charAt(k)){
                                bracketNumber--;
                            }
                        }
                    }
                    //����
                    if(!zLock){
                        //��¼�Ǻ�λ��
                        if('*' == code){
                            xingHao = k;
                        }
                        //ȫ��ע��
                        if('/' == code){
                            if(k == xingHao+1){
                                startCode = k + 1;
                                zLock = true;
                            }
                        }
                    }
                }
                if(!zLock || VerificationUtil.isBlankString(javaCode.trim())){//û�н���  ������ȡ
                    continue;
                }
                int len = javaCode.length();
                if(len!=startCode)
                    stringBuffer.append(javaCode, startCode, len);
                // ��һ������ ����  �ڶ����Ƿ���
                boolean isFanFaNei = bracketNumber > 1;
                //��β���� {
                boolean isNotKuoSuffix = javaCode.trim().lastIndexOf("{") != len -1;
                //��β���� }
                boolean isNotFanKuoSuffix = javaCode.trim().lastIndexOf("}") != len -1;
                //��β���� ;
                boolean isNotFenHao = javaCode.trim().lastIndexOf(";") != len -1;
                //ֵΪ��  �����ڲ�������    �������治�� �ֺ� ������ �� ������
                if(len == 0 || isFanFaNei|| (isNotFenHao && isNotFanKuoSuffix && isNotKuoSuffix)){
                    if(isLine)stringBuffer.append("\r\n");
                    continue;
                }
//	            System.out.println(bracketNumber +"����������?��"+stringBuffer.toString());
                String javaCodeL = stringBuffer.toString();
                stringList.add(javaCodeL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }
}
