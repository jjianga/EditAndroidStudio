package com.zhkj.code.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DSP-ReadJavaUtil 读java文件的工具类
 * @author jjianga
 * @Date 2022-08-21 10:17
 * @version 1.0.0
 * @
 */
public class ReadJavaUtil {
    /**
     * 读取java文件
     * @param file 文件
     * @param encoding 编码格式
     * @return List<String>
     */
    public static List<String> readJavaToString(File file, String encoding){
        return readJavaToString(file, encoding, true);
    }

    /**
     * 读取java文件
     * @param file 文件
     * @param encoding 编码格式
     * @param isLine 是否保留换行
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
                //反斜杠 可能是注释
                int fanXieGang = -100;
                //星号 可能是注释
                int xingHao= -100;
                //开始位置
                int startCode = 0;
                boolean fanxie =false;
                //TODO 检查数据  双引号内的不计数 = javaCode;
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
                            //记录斜杠出现
                            if('/' == code){
                                if(k == fanXieGang+1){//去行注释
                                    javaCode = javaCode.substring(startCode,k-1);
                                    startCode = 0;
                                    break;
                                }
                                fanXieGang = k;
                            }else if('*' == code){ //全局注释
                                if(k == fanXieGang+1){
                                    stringBuffer.append(javaCode, startCode, k-1);
                                    zLock = false;//上锁
                                    continue;
                                }
                            }else if('{' == code){//计数括号
                                bracketNumber++;
                            }else if('}' == javaCode.charAt(k)){
                                bracketNumber--;
                            }
                        }
                    }
                    //解锁
                    if(!zLock){
                        //记录星号位置
                        if('*' == code){
                            xingHao = k;
                        }
                        //全局注释
                        if('/' == code){
                            if(k == xingHao+1){
                                startCode = k + 1;
                                zLock = true;
                            }
                        }
                    }
                }
                if(!zLock || VerificationUtil.isBlankString(javaCode.trim())){//没有解锁  继续读取
                    continue;
                }
                int len = javaCode.length();
                if(len!=startCode)
                    stringBuffer.append(javaCode, startCode, len);
                // 第一个括号 是类  第二个是方法
                boolean isFanFaNei = bracketNumber > 1;
                //结尾不是 {
                boolean isNotKuoSuffix = javaCode.trim().lastIndexOf("{") != len -1;
                //结尾不是 }
                boolean isNotFanKuoSuffix = javaCode.trim().lastIndexOf("}") != len -1;
                //结尾不是 ;
                boolean isNotFenHao = javaCode.trim().lastIndexOf(";") != len -1;
                //值为空  方法内部继续读    方法外面不是 分号 花括号 的 继续读
                if(len == 0 || isFanFaNei|| (isNotFenHao && isNotFanKuoSuffix && isNotKuoSuffix)){
                    if(isLine)stringBuffer.append("\r\n");
                    continue;
                }
//	            System.out.println(bracketNumber +"句完整代码?："+stringBuffer.toString());
                String javaCodeL = stringBuffer.toString();
                stringList.add(javaCodeL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }
}
