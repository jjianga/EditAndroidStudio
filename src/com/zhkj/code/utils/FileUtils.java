package com.zhkj.code.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Random;

import com.zhkj.log.Log;

public class FileUtils {
	
	public static String getFileName(File file) {
		return file.getName();
	}

	
	public static String getFileNameSuffix(String fileName) {
		fileName = fileName.substring(fileName.lastIndexOf("."),fileName.length());
		return fileName;
	}
	/**
	 * ��ȡ�ļ� ȥ��׺
	 * @param file
	 * @param suffix
	 * @return
	 */
	public static String getFileNameNoSuffix(File file, String suffix) {
		if (file.getName().contains(suffix)) {
			return file.getName().split(suffix)[0];
		} else {
			return null;
		}
	}
	/**
	 * �ļ�������
	 * @param file
	 * @param newName
	 * @param fileSuffix
	 * @return
	 */
	public static String changeFilePath(File file, String newName,
			String fileSuffix) {
		String path = file.getParent() + File.separator;
		return path + newName + fileSuffix;
	}

	/**
	 * ��ȡĿ¼�������ļ�
	 * @param dirRoot
	 * @param filelist
	 * @return
	 */
	public static List<File> listFiles(File dirRoot, List<File> filelist) {
		File[] files = dirRoot.listFiles(); // ���ļ�Ŀ¼���ļ�ȫ����������
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) { // �ж����ļ������ļ���
					listFiles(files[i].getAbsoluteFile(), filelist); // ��ȡ�ļ�����·��
				} else {
					// String strFileName = files[i].getAbsolutePath();
					// System.out.println("---" + strFileName);
					filelist.add(files[i]);
				}
			}
		}
		return filelist;
	}
	/**
	 * ��ȡĿ¼�������ļ�
	 * @param dirRoot
	 * @param filelist
	 * @return
	 */
	public static List<File> listFiles(String dirRoot, List<File> filelist) {
		File dir = new File(dirRoot);
		listFiles(dir.getAbsoluteFile(), filelist); // ��ȡ�ļ�����·��
		return filelist;
	}
	/**
	 * ��ȡĿ¼�������ļ� ����String
	 * @param dirRoot
	 * @param filelist
	 * @return
	 */
	public static List<String> listFilesString(File dirRoot, List<String> filelist) {
		File[] files = dirRoot.listFiles(); // ���ļ�Ŀ¼���ļ�ȫ����������
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) { // �ж����ļ������ļ���
					listFilesString(files[i].getAbsoluteFile(), filelist); // ��ȡ�ļ�����·��
				} else {
					// String strFileName = files[i].getAbsolutePath();
					// System.out.println("---" + strFileName);
					filelist.add(files[i].getAbsolutePath());
				}
			}
		}
		return filelist;
	}
	/**
	 * ��ȡĿ¼�������ļ���
	 * @param dirRoot
	 * @param directorylist
	 * @return
	 */
	public static List<String> listDirectorys(String dirRoot, List<String> directorylist) {
		File dir = new File(dirRoot);
		File[] files = dir.listFiles(); // ���ļ�Ŀ¼���ļ�ȫ����������
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) { // �ж����ļ������ļ���
					String directoryPath = files[i].getAbsolutePath();
					directorylist.add(directoryPath);
					listDirectorys(files[i].getAbsolutePath(), directorylist); // ��ȡ�ļ�����·��
				} 
			}
		}
		return directorylist;
	}
	
	// �õ�ĳһĿ¼�µ������ļ���
	public List<File> visitAll(File root,List<File> list) {
		File[] dirs = root.listFiles();
		if (dirs != null) {
			for (int i = 0; i < dirs.length; i++) {
				if (dirs[i].isDirectory()) {
					list.add(dirs[i]);
				}
				visitAll(dirs[i],list);
			}
		}
		return list;
	}
	/**
	 * �����ļ�
	 * @param srcPath
	 * @param destPath
	 * @throws IOException
	 */
	public static void copyFile(String srcPath, String destPath) throws IOException {
		try {
			String newPathFolder = destPath.substring(0,
					destPath.lastIndexOf(File.separator));
			File folder = new File(newPathFolder);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			//int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(srcPath);
			if (oldfile.exists()) { // �ļ�����ʱ
				InputStream inStream = new FileInputStream(srcPath); // ����ԭ�ļ�
				FileOutputStream fs = new FileOutputStream(destPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					//bytesum += byteread; // �ֽ��� �ļ���С
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (IOException e) {
			throw new IOException(e);
		}
	}

	/**
	 * �����ļ� �Լ��ļ���   �����֮ǰ���ļ���
	 * @param src		Դ�ļ�
	 * @param dest		Ŀ���ļ�
	 * @throws IOException
	 */
	public static void copyFolderIsDelete(File src, File dest) throws IOException {
		if(dest.exists()){
			FileUtils.deleteFile(dest);
		}
		copyFolder(src, dest);
	}
	/**
	 * �����ļ� �Լ��ļ���   
	 * @param src		Դ�ļ�
	 * @param dest		Ŀ���ļ�
	 * @throws IOException
	 */
	public static void copyFolder(File src, File dest) throws IOException {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();
			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// �ݹ鸴��
				copyFolder(srcFile, destFile);
			}
		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;

			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		}
	}
	/**
	 * ���ļ� �������򴴽��µ��ļ�
	 * @param filePath
	 */
	public static File createOpenNewFile(String filePath) {
		File file = new File(filePath);
		File fileParent = file.getParentFile();  
		if(!fileParent.exists()){  
		    fileParent.mkdirs();  
		}  
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	/**
	 * ɾ���ļ� ���� �ļ���������������
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (!file.exists()) {
		} else {
			if (file.isFile())
				deleteFile(file.getPath());
			else
				deleteDirectory(file.getPath());
		}
	}

	/**
	 * ɾ�������ļ�
	 * 
	 * @param fileName
	 *   Ҫɾ�����ļ����ļ���
	 * @return �����ļ�ɾ���ɹ�����true�����򷵻�false
	 */
	private static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// ����ļ�·������Ӧ���ļ����ڣ�������һ���ļ�����ֱ��ɾ��
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
//				Log.appendInfo("ɾ�������ļ�" + fileName + "�ɹ���");
				return true;
			} else {
				Log.appendErr("ɾ�������ļ�" + fileName + "ʧ�ܣ�");
				return false;
			}
		} else {
			Log.appendErr("ɾ�������ļ�ʧ�ܣ�" + fileName + "�����ڣ�");
			return false;
		}
	}

	/**
	 * ɾ��Ŀ¼��Ŀ¼�µ��ļ�
	 * 
	 * @param dir
	 *            Ҫɾ����Ŀ¼���ļ�·��
	 * @return Ŀ¼ɾ���ɹ�����true�����򷵻�false
	 */
	private static boolean deleteDirectory(String dir) {
		// ���dir�����ļ��ָ�����β���Զ�����ļ��ָ���
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// ���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			System.out.println("ɾ��Ŀ¼ʧ�ܣ�" + dir + "�����ڣ�");
			return false;
		}
		boolean flag = true;
		// ɾ���ļ����е������ļ�������Ŀ¼
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// ɾ�����ļ�
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// ɾ����Ŀ¼
			else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			System.out.println("ɾ��Ŀ¼ʧ�ܣ�");
			return false;
		}
		// ɾ����ǰĿ¼
		if (dirFile.delete()) {
			System.out.println("ɾ��Ŀ¼" + dir + "�ɹ���");
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * �������õ�ͼƬ��xml��java ���ļ�
	 * 
	 * 
	 * �������õ�ͼƬ�ļ�
	 * @param fileName ͼƬ�ļ�����
	 * @param filePath ���ɵ�·��
	 * */
	public static File getImageFile(String fileName,String filePath){
		BufferedWriter writer = null;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		File newFile = null;
		//String newFilePath = null;
		try{
			//newFilePath = filePath+separator+fileName+FixContants.Png_File_Suffix;
			newFile = new File(filePath);//����һ�����ļ�
			if(!newFile.exists()){ //�ļ�������
				newFile.createNewFile(); //����
			}
			//�������õĶ����ƴ���д�뵽�����ɵ��ļ���ȥ
			String randomHan = getRandomHan();
			fos = new FileOutputStream(newFile);
			osw = new OutputStreamWriter(fos, "iso8859-1");
			writer = new BufferedWriter(osw);
			//writer = new BufferedWriter(new FileWriter(newFile));  
			if(randomHan!=null&&!randomHan.equals("")) {  
               writer.write(randomHan + "\n");  
            }
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("�쳣��Ϣ:"+ex.toString());
		}finally{
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return newFile;
	}
	
		/** //**�ļ�������
	    * @param oldName  ԭ�����ļ���
	    * @param newName ���ļ���
	    */ 
	    public static void renameFile(String oldName,String newName){
	        if(!oldName.equals(newName)){//�µ��ļ�������ǰ�ļ�����ͬʱ,���б�Ҫ����������
	            File oldFile=new File(oldName);
	            File newFile=new File(newName);
	            if(!oldFile.exists()){
	                return;//�������ļ�������
	            }
	            if(newFile.exists())//���ڸ�Ŀ¼���Ѿ���һ���ļ������ļ�����ͬ��������������
	            	Log.appendErr(newName+"�Ѿ����ڣ�");
	            else{ 
	                oldFile.renameTo(newFile);
	                Log.appendInfo("�������ɹ���");
	            } 
	        }else{
	            Log.appendErr("���ļ����;��ļ�����ͬ...");
	        }
	    }
	
	
	/**��������ֽڴ�С��һ�ĺ����ַ���*/
	public static String getRandomHan(){
		Random ran = new Random();
		int a = (ran.nextInt(99)+1)*500;
	    int delta = 0x9fa5 - 0x4e00 + 1;
	    StringBuffer buffer = new StringBuffer();
	    for (int i = 0; i <a; i++) { //1000����=2kb  500=1kb 100��������������һ������500����֤ÿ���ļ��Ĵ�С�б仯
	    	char han = (char)(0x4e00 + ran.nextInt(delta));
	    	buffer.append(han);
	    }
	    //System.out.println("���֣�+"+buffer.toString());
	    return buffer.toString();
	}
	/**
	 * ��ȡ�ļ�����
	 * @param file		��ȡ���ļ�
	 * @param encoding	�����ʽ
	 * @return
	 */
   public static StringBuffer readString(File file, String encoding) {
        int len=0;
        StringBuffer str=new StringBuffer("");
        try {
            FileInputStream is=new FileInputStream(file);
            InputStreamReader isr= new InputStreamReader(is,encoding); 
            BufferedReader in= new BufferedReader(isr);
            String line=null;
            while( (line=in.readLine())!=null ){
                if(len != 0){
                    str.append("\r\n"+line);
                }else{
                    str.append(line);
                }
                len++;
            }
            in.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

   /**
    * ��Buf д���ļ�
    * @param file		��д����ļ�
    * @param stringBf	д�������
    * @param encoding	���뷽ʽ
    */
   public static void saveFile(File file,StringBuffer stringBf, String encoding) {
       try {
       	File fileParent = file.getParentFile();  
       	if(!fileParent.exists()){  
       	    fileParent.mkdirs();  
       	} 
   		if (!file.exists()) {
   			try {
   				file.createNewFile();
   			} catch (IOException e) {
   				e.printStackTrace();
   			}
   		}
       	// ���������FileOutputStream����out
       	FileOutputStream out=new FileOutputStream(file);
       	// д���ļ�
       	out.write(stringBf.toString().getBytes(encoding));
       	 // �ر������
       	out.flush(); 
       	out.close();
//		Log.appendInfo("���浥���ļ�" + file.getAbsolutePath() + "�ɹ���");
       } catch (Exception e) {
   			Log.appendErr("���浥���ļ���" + stringBf.toString());
           e.printStackTrace();
       }
   }
   /**
    * д�ļ�  
    * @param file		��д����ļ� 
    * @param string		д�������
    * @param encoding	���뷽ʽ
    */
   public static void saveFile(File file,String string, String encoding) {
      try {
         	File fileParent = file.getParentFile();  
         	if(!fileParent.exists()){  
         	    fileParent.mkdirs();  
         	} 
     		if (!file.exists()) {
     			try {
     				file.createNewFile();
     			} catch (IOException e) {
     				e.printStackTrace();
     			}
     		}
         	// ���������FileOutputStream����out
         	FileOutputStream out=new FileOutputStream(file);
         	// д���ļ�
         	out.write(string.getBytes(encoding));
         	 // �ر������
         	out.flush(); 
         	out.close();
//    		Log.appendInfo("���浥���ļ�" + file.getAbsolutePath() + "�ɹ���");
         } catch (IOException e) {
             e.printStackTrace();
         }
    }

	/**
	 * ɾ���յ��ļ���
	 * 
	 * @param list
	 */
	public void removeNullFile(List<File> list) {
		for (int i = 0; i < list.size(); i++) {
			File temp = list.get(i);
			// ��Ŀ¼��Ϊ��
			if (temp.isDirectory() && temp.listFiles().length <= 0) {
				temp.delete();
			}
		}
	}
	
	public static void main(String[] args) {
		
		File imageFile =getImageFile("ttt","F:");
		
		System.out.println("File����:"+imageFile.getAbsolutePath());
		System.out.println("File����:"+imageFile.getAbsolutePath());
		System.out.println("File����:"+FileUtils.getFileNameSuffix("a.c"));
		
		getRandomHan();

	}
}
