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
	 * 获取文件 去后缀
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
	 * 文件重命名
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
	 * 读取目录下所有文件
	 * @param dirRoot
	 * @param filelist
	 * @return
	 */
	public static List<File> listFiles(File dirRoot, List<File> filelist) {
		File[] files = dirRoot.listFiles(); // 该文件目录下文件全部放入数组
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) { // 判断是文件还是文件夹
					listFiles(files[i].getAbsoluteFile(), filelist); // 获取文件绝对路径
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
	 * 读取目录下所有文件
	 * @param dirRoot
	 * @param filelist
	 * @return
	 */
	public static List<File> listFiles(String dirRoot, List<File> filelist) {
		File dir = new File(dirRoot);
		listFiles(dir.getAbsoluteFile(), filelist); // 获取文件绝对路径
		return filelist;
	}
	/**
	 * 读取目录下所有文件 返回String
	 * @param dirRoot
	 * @param filelist
	 * @return
	 */
	public static List<String> listFilesString(File dirRoot, List<String> filelist) {
		File[] files = dirRoot.listFiles(); // 该文件目录下文件全部放入数组
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) { // 判断是文件还是文件夹
					listFilesString(files[i].getAbsoluteFile(), filelist); // 获取文件绝对路径
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
	 * 读取目录下所有文件夹
	 * @param dirRoot
	 * @param directorylist
	 * @return
	 */
	public static List<String> listDirectorys(String dirRoot, List<String> directorylist) {
		File dir = new File(dirRoot);
		File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) { // 判断是文件还是文件夹
					String directoryPath = files[i].getAbsolutePath();
					directorylist.add(directoryPath);
					listDirectorys(files[i].getAbsolutePath(), directorylist); // 获取文件绝对路径
				} 
			}
		}
		return directorylist;
	}
	
	// 得到某一目录下的所有文件夹
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
	 * 复制文件
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
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(srcPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(destPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					//bytesum += byteread; // 字节数 文件大小
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
	 * 复制文件 以及文件夹   会清空之前的文件夹
	 * @param src		源文件
	 * @param dest		目的文件
	 * @throws IOException
	 */
	public static void copyFolderIsDelete(File src, File dest) throws IOException {
		if(dest.exists()){
			FileUtils.deleteFile(dest);
		}
		copyFolder(src, dest);
	}
	/**
	 * 复制文件 以及文件夹   
	 * @param src		源文件
	 * @param dest		目的文件
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
				// 递归复制
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
	 * 打开文件 不存在则创建新的文件
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
	 * 删除文件 或者 文件夹下面所有数据
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
	 * 删除单个文件
	 * 
	 * @param fileName
	 *   要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	private static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
//				Log.appendInfo("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				Log.appendErr("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			Log.appendErr("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	/**
	 * 删除目录及目录下的文件
	 * 
	 * @param dir
	 *            要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	private static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			System.out.println("删除目录失败：" + dir + "不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			System.out.println("删除目录失败！");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 生成无用的图片，xml，java 等文件
	 * 
	 * 
	 * 生成无用的图片文件
	 * @param fileName 图片文件名称
	 * @param filePath 生成的路径
	 * */
	public static File getImageFile(String fileName,String filePath){
		BufferedWriter writer = null;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		File newFile = null;
		//String newFilePath = null;
		try{
			//newFilePath = filePath+separator+fileName+FixContants.Png_File_Suffix;
			newFile = new File(filePath);//生成一个空文件
			if(!newFile.exists()){ //文件不存在
				newFile.createNewFile(); //创建
			}
			//生成无用的二进制代码写入到新生成的文件中去
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
			System.out.println("异常信息:"+ex.toString());
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
	
		/** //**文件重命名
	    * @param oldName  原来的文件名
	    * @param newName 新文件名
	    */ 
	    public static void renameFile(String oldName,String newName){
	        if(!oldName.equals(newName)){//新的文件名和以前文件名不同时,才有必要进行重命名
	            File oldFile=new File(oldName);
	            File newFile=new File(newName);
	            if(!oldFile.exists()){
	                return;//重命名文件不存在
	            }
	            if(newFile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
	            	Log.appendErr(newName+"已经存在！");
	            else{ 
	                oldFile.renameTo(newFile);
	                Log.appendInfo("重命名成功！");
	            } 
	        }else{
	            Log.appendErr("新文件名和旧文件名相同...");
	        }
	    }
	
	
	/**随机生成字节大小不一的汉字字符串*/
	public static String getRandomHan(){
		Random ran = new Random();
		int a = (ran.nextInt(99)+1)*500;
	    int delta = 0x9fa5 - 0x4e00 + 1;
	    StringBuffer buffer = new StringBuffer();
	    for (int i = 0; i <a; i++) { //1000汉字=2kb  500=1kb 100个随机数里面随机一个数乘500，保证每个文件的大小有变化
	    	char han = (char)(0x4e00 + ran.nextInt(delta));
	    	buffer.append(han);
	    }
	    //System.out.println("汉字：+"+buffer.toString());
	    return buffer.toString();
	}
	/**
	 * 读取文件内容
	 * @param file		读取的文件
	 * @param encoding	编码格式
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
    * 将Buf 写入文件
    * @param file		被写入的文件
    * @param stringBf	写入的内容
    * @param encoding	编码方式
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
       	// 创建输出流FileOutputStream对象out
       	FileOutputStream out=new FileOutputStream(file);
       	// 写入文件
       	out.write(stringBf.toString().getBytes(encoding));
       	 // 关闭输出流
       	out.flush(); 
       	out.close();
//		Log.appendInfo("保存单个文件" + file.getAbsolutePath() + "成功！");
       } catch (Exception e) {
   			Log.appendErr("保存单个文件【" + stringBf.toString());
           e.printStackTrace();
       }
   }
   /**
    * 写文件  
    * @param file		被写入的文件 
    * @param string		写入的内容
    * @param encoding	编码方式
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
         	// 创建输出流FileOutputStream对象out
         	FileOutputStream out=new FileOutputStream(file);
         	// 写入文件
         	out.write(string.getBytes(encoding));
         	 // 关闭输出流
         	out.flush(); 
         	out.close();
//    		Log.appendInfo("保存单个文件" + file.getAbsolutePath() + "成功！");
         } catch (IOException e) {
             e.printStackTrace();
         }
    }

	/**
	 * 删除空的文件夹
	 * 
	 * @param list
	 */
	public void removeNullFile(List<File> list) {
		for (int i = 0; i < list.size(); i++) {
			File temp = list.get(i);
			// 是目录且为空
			if (temp.isDirectory() && temp.listFiles().length <= 0) {
				temp.delete();
			}
		}
	}
	
	public static void main(String[] args) {
		
		File imageFile =getImageFile("ttt","F:");
		
		System.out.println("File名称:"+imageFile.getAbsolutePath());
		System.out.println("File名称:"+imageFile.getAbsolutePath());
		System.out.println("File名称:"+FileUtils.getFileNameSuffix("a.c"));
		
		getRandomHan();

	}
}
