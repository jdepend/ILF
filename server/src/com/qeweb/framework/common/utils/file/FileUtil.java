package com.qeweb.framework.common.utils.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qeweb.framework.common.utils.StringUtils;

/**
 * 文件处理帮助类
 */
public class FileUtil

{
	private static final Log log = LogFactory.getLog(FileUtil.class);

	/***************************************************************************
	 * 根据传入的合法路径得到目录下的文件数组
	 * 
	 * 
	 * @param path
	 *            传入的路径,如带扩展名称E:\\log\\*.log，则获取目录下扩展名为log的文件对象集合，
	 *            如不带扩展名，则获取目录下所有文件对象集合
	 * 
	 * @return 返回文件对象集合
	 **************************************************************************/
	public static File[] getfiles(String path) {
		String extension = "";
		FilenameFilter filter = null;
		if (path.indexOf("*.") != -1) { // 获取指定扩展名的文件
			extension = path.substring(path.indexOf("*.") + 2, path.length());
			path = path.substring(0, path.indexOf("*."));
			filter = new FileNameFilterImpl(extension);
		}
		File f = new File(path);
		if (f.exists()) {
			List<File> children = Arrays.asList((filter == null) ? f.listFiles(): f.listFiles(filter));
			return (File[]) getSortfiles(children).toArray();
		} else
			return null;

	}

	/***************************************************************************
	 * 由CallErpWS类中转过来的公用文件与文件夹操作方法
	 * 
	 * 
	 * 
	 **************************************************************************/

	/**
	 * 功 能: 创建文件夹 参 数: strDir 要创建的文件夹名称 返回值: 如果成功true;否则false
	 */
	public static boolean makedir(String strDir) {
		File fileNew = new File(strDir);

		if (!fileNew.exists())
			return fileNew.mkdirs();
		else
			return true;
	}

	/**
	 * 功 能: 删除文件夹 参 数: strDir 要删除的文件夹名称 返回值: 如果成功true;否则false
	 */
	public static boolean rmdir(String strDir) {
		File rmDir = new File(strDir);
		if (rmDir.isDirectory() && rmDir.exists()) {
			String[] fileList = rmDir.list();

			for (int i = 0; i < fileList.length; i++) {
				String subFile = strDir + File.separator + fileList[i];
				File tmp = new File(subFile);
				if (tmp.isFile())
					tmp.delete();
				else if (tmp.isDirectory())
					rmdir(subFile);
				else
					log.info("error! ");
			}
			rmDir.delete();
		} else
			return false;
		return true;
	}

	/***
	 * 解压Zip
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] unZip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ZipInputStream zip = new ZipInputStream(bis);
			while (zip.getNextEntry() != null) {
				byte[] buf = new byte[1024];
				int num = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((num = zip.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
				b = baos.toByteArray();
				baos.flush();
				baos.close();
			}
			zip.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/**
	 * 解压下载的数据压缩包
	 * 
	 * @param zipFileName
	 * @param outputDirectory
	 * @return
	 * @throws Exception
	 */
	public static boolean UnZIP(String zipFileName, String outputDirectory)
			throws Exception {
		ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
		ZipEntry z;

		while ((z = in.getNextEntry()) != null) {
			log.info("unziping " + z.getName());
			if (z.isDirectory()) {
				String name = z.getName();
				name = name.substring(0, name.length() - 1);
				File f = new File(outputDirectory + File.separator + name);
				f.mkdir();
				log.info("mkdir " + outputDirectory + File.separator + name);
			} else {
				File f = new File(outputDirectory + File.separator
						+ z.getName());
				f.createNewFile();
				FileOutputStream out = new FileOutputStream(f);
				int b;
				while ((b = in.read()) != -1)
					out.write(b);
				out.close();
			}
		}

		in.close();

		return true;
	}

	/**
	 * 解压下载的数据压缩包
	 * 
	 * @param zipFileName
	 * @param outputDirectory
	 * @return
	 * @throws Exception
	 */
		public static boolean UnZIP2(String zipFileName, String outputDirectory)
			throws Exception {
		ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
		ZipEntry z;

		while ((z = in.getNextEntry()) != null) {
			log.info("unziping " + z.getName());
			if (z.isDirectory()) {
				String name = z.getName();
				name = name.substring(0, name.length() - 1);
				File f = new File(outputDirectory + File.separator + name);
				f.mkdir();
				log.info("mkdir " + outputDirectory + File.separator + name);
			} 
			else {
				File f = new File(outputDirectory + File.separator + z.getName());
				f.createNewFile();
				FileOutputStream out = new FileOutputStream(f);
				int b;
				while ((b = in.read()) != -1)
					out.write(b);
				out.close();
			}
		}

		in.close();

		return true;
	}

	/**
	 * 将指定的文件拆分成ｎ小份,终端访问WebServcies服务时，作为返回值之用
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean SpliteProgramFiles(String basePath, String filename,
			int blocksize) {

		String filePath = basePath + File.separator + filename;
		File fileover = new File(filePath);
		long size = fileover.length();

		log.info(filePath + "文件的大小：" + (int) size);
		byte[] bytesfile = new byte[(int) size];

		try {
			FileInputStream fi = new FileInputStream(filePath);
			fi.read(bytesfile);
			fi.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(filePath + "压缩文件读取出错：" + e.getMessage());
			return false;
		}

		String base64str = new sun.misc.BASE64Encoder().encode(bytesfile);
		size = base64str.length();
		log.info("BASE64编码后的文件大小：" + (int) size);
		// int num = (int)Math.floor(size / (1024 * 10));
		int num;
		if (size > blocksize)
			num = (int) Math.round((double) size / (double) blocksize);
		else
			num = 1;

		log.info("预计拆分后的文件个数：" + num);
		// 建立zip拆分文件夹
		filePath = basePath + File.separator + "zipFiles";

		File f = new File(filePath);
		if (!f.exists()) {
			f.mkdirs();
		}

		for (int i = 0; i < num; i++) {
			String substr = "";
			if (base64str.length() > blocksize) {
				substr = base64str.substring(0, blocksize);
				base64str = base64str.substring(blocksize);
			} else {
				substr = base64str;
			}

			filePath = basePath + File.separator + "zipFiles" + File.separator
					+ (i + 1) + ".txt";
			File file = new File(filePath);
			if (file.exists())
				file.delete();
			try {
				file.createNewFile();

				FileOutputStream fs = new FileOutputStream(filePath, true);

				byte[] bytes = substr.toString().getBytes();
				fs.write(bytes, 0, bytes.length);
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error("创建拆分文件出错：" + e.getMessage());
				return false;
			}
			log.info("生成：" + filePath);

		}

		return true;
	}
	/***
	 * 压缩文件或文件夹
	 * @param outputFile
	 * @param inputFile
	 */
		public static void Zip(String inputFile,String outputFile){	  
		  ZipCompressor zc = new  ZipCompressor(outputFile);   
	      zc.compress(inputFile); 
	  }

	/**
	 * 返回指定路径下的.zip文件数量
	 * 
	 * @param subPath
	 * @return
	 * @throws IOException
	 */
	public static List<Integer> zipFileNum(String subPath) {
		List<Integer> sizeList = null;

		File file = new File(subPath);
		if (!file.exists()) {
			return null;
		}
		if (!file.isDirectory()) {
			return null;
		}
		String[] tempList = file.list();
		int lengthZip = 0;
		for (String files : tempList) {
			if (files.toLowerCase().endsWith(".zip")) {
				lengthZip++;
			}
		}

		System.out.println(tempList.length);
		if (tempList.length > 0) {
			sizeList = new ArrayList<Integer>();
			for (int i = 1; i <= lengthZip; i++) {
				try {
					sizeList.add(new FileInputStream(new File(subPath + "/" + i+ ".zip")).available());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					log.error("读取文件大小出错" + e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
					log.error("读取文件大小出错" + e.getMessage());
				}
			}
		}

		return sizeList;
	}
	/**
	 * 读取文件
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static String readFile(String fileName) {
		String output = "";

		File file = new File(fileName);

		if (file.exists()) {
			if (file.isFile()) {
				try {
					BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
					StringBuffer buffer = new StringBuffer();
					String text;

					while ((text = input.readLine()) != null)
						buffer.append(text + "\n");

					output = buffer.toString();
				} catch (IOException ioException) {
					System.err.println("File Error!");

				}
			}

		} else {
			log.info("文件不存在!" + fileName);
		}
		return output;
	}
	
	/**
	 * 如果目录不存在就创建目录
	 * @param Path
	 */
	public static void mkdirs(String Path){
		File tagPath= new File(Path);
		if(!tagPath.exists()){
			tagPath.mkdirs();
		}
	}
	/**
	 * 删除目录及目录下的所有文件
	 * 删除文件
	 * @param filepath
	 * @throws IOException
	 */
	public static void del(String filepath) throws IOException {
		File f = new File(filepath);// 定义文件路径
		if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
			if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
				f.delete();
			} else {// 若有则把文件放进数组，并判断是否有下级目录
				File delFile[] = f.listFiles();
				int i = f.listFiles().length;
				for (int j = 0; j < i; j++) {
					if (delFile[j].isDirectory()) {
						del(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
					}
					delFile[j].delete();// 删除文件
				}
				f.delete();
			}
		} else if (f.exists() && f.isFile()) {
			f.delete();
		}
	}

	 /** 
     * 分割文件
     * @param fileName 待分割的文件名
     * @param size 小文件的大小，以字节为单位
     * @return 分割后小文件的文件名
     * @throws Exception 分割过程中可能抛出的异常
     */
	  public static String[] divide(String fileName, long size,String SUFFIX) throws Exception {
    	
        File inFile = new File(fileName);
        if (!inFile.exists() || inFile.isDirectory()) {
            throw new Exception("指定文件不存在!");
        }
        // 获得被分割文件父文件，将来被分割成的小文件就存放在这个目录下
        File parentFile = inFile.getParentFile();

        // 取得文件的大小
        long fileLength = inFile.length();
        if (size <= 0)
            size = fileLength / 2;

        // 取得分割后的小文件的数目
        int num = (int) ((fileLength + size - 1) / size);

        // 存放分割后的小文件名
        String[] outFileNames = new String[num];

        FileInputStream in = new FileInputStream(inFile);

        // 读输入文件流的开始和结束下标
        long inEndIndex = 0;
        int inBeginIndex = 0;

        // 根据要分割的数目分割文件
        for (int outFileIndex = 0; outFileIndex < num; outFileIndex++) {
            // 对于前outFileIndex-1个文件，大小都是size
            File outFile = new File(parentFile, (outFileIndex+1)    + SUFFIX);
            FileOutputStream out = new FileOutputStream(outFile);
            inEndIndex += size;
            inEndIndex = (inEndIndex > fileLength) ? fileLength : inEndIndex;
            // 从输入流中读取字节存储到输出流中
            for (; inBeginIndex < inEndIndex; inBeginIndex++)
                out.write(in.read());
            out.close();
            outFileNames[outFileIndex] = outFile.getAbsolutePath();
        }
        in.close();
        return outFileNames;
    }
	  public static void divideZip(String fileName, long size,String SUFFIX) throws Exception {
    	
    String[] files=	divide( fileName,  size, SUFFIX);
      if(files==null||files.length<1){
    	  log.error("文件拆分失败：");
    	  return;
      }
      //解压并删除拆分出的
      for(int i=0;i<files.length;i++){
    	  String outFile=files[i].substring(0,files[i].lastIndexOf(File.separator))+File.separator+(i+1)+".zip";
    	  FileUtil.Zip(files[i],outFile);
    	  del(files[i]);
      }
    }

    /** 
     * 合并文件
     * @param fileNames 带合并的文件名，是一个数组
     * @param targerFileName 目标文件名
     * @return 目标文件的全路径
     * @throws Exception 合并过程中可能抛出的异常
     */
	  public static String unite(String[] fileNames, String targerFileName)
            throws Exception {
        File inFile = null;
        File outFile = new File(targerFileName);
        FileOutputStream out = new FileOutputStream(outFile);

        for (int i = 0; i < fileNames.length; i++) {
            inFile = new File(fileNames[i]);
            FileInputStream in = new FileInputStream(inFile);
            int c;
            while ((c = in.read()) != -1)
                out.write(c);
            in.close();
        }
        out.close();
        return outFile.getAbsolutePath();
    }
	  
	/**
	 * 复制文件
	 * @param sourceFile 源文件
	 * @param targetFile 目录文件
	 * @param flag 0 如果目标文件存在，即保留 1 如果目标文件存在，即删除
	 */
	@SuppressWarnings("resource")
	public static void FileCopy(String sourceFile, String targetFile, int flag) {
		File tf = new File(targetFile);
		if (flag == 0) {
			if (tf.exists()) {
				log.warn("目标文件已存在，复制失败;");
				return;
			}
		} 
		else if (flag == 1) {
			if (tf.exists()) {
				tf.delete();
				log.warn("目标文件已存在，删除后重新复制;");
			}
		}
		try {
			FileChannel srcChannel = new FileInputStream(sourceFile).getChannel();
			FileChannel dstChannel = new FileOutputStream(targetFile).getChannel();
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

			srcChannel.close();
			dstChannel.close();
		} catch (IOException e) {
		}
	}

	/**
	 * 
	 * @param sourceFile
	 * @param out
	 */
	public static boolean fileCopy(String sourceFile, OutputStream out) {
		byte[] buf = new byte[1024 * 100];
		try {
			File file = new File(sourceFile);
			FileInputStream in = new FileInputStream(file);

			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			in.close();
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}   
	}
	
	/**
	 * path是否是文件
	 * @param path
	 * @return
	 */
	public static boolean isFile(String path) {
		if(StringUtils.isNotEmpty(path)) {
			File clientFile = new File(path);
			return clientFile.isFile();
		}
		
		return false;
	}
	
	/**
	 * path是否是文件夹
	 * @param path
	 * @return
	 */
	public static boolean isDir(String path) {
		File clientFile = new File(path);
		return clientFile.isDirectory();
	}
	
	public static void main(String[] arg) {
		try {
			divideZip(
					"D:\\QEWEB\\Ver\\Invoice\\320\\CK1003313200010001\\all.txt",
					100, ".txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得root下的所有的文件
	 * @param root
	 * @return
	 */
	public static List<File> getAllFiles(File root) {
		return getAllFiles(root, typeFileter());
	}

	/**
	 * 获得root下满足fileFilter的所有的文件
	 * @param root
	 * @param fileFilter
	 * @return
	 */
	public static List<File> getAllFiles(File root, FileFilter fileFilter) {
		if(!root.isDirectory())
			return null;
		
		List<File> fileInfo = new LinkedList<File>();

		File[] files = root.listFiles(fileFilter);

		for (File file : files) {
			if (file.isDirectory()) { 
				fileInfo.addAll(getAllFiles(file, fileFilter));
			} 
			else {
				fileInfo.add(file);
			}
		}

		return fileInfo;
	}
	
	/**
	 * 文件过滤器，过滤隐藏文件夹和隐藏文件
	 * @return
	 */
	final public static FileFilter typeFileter() {
		return new FileFilter() {
			public boolean accept(File pathname) {
				// 去掉隐藏文件夹
				if (pathname.isDirectory() && pathname.isHidden()) { 
					return false;
				}
				// 去掉隐藏文件
				else if (pathname.isFile() && pathname.isHidden()) {
					return false;
				}
				else {
					return true;
				}
			}
		};
	}
	
	/**
	 * 文件过滤器，过滤隐藏文件夹和隐藏文件， 并根据文件名进行like过滤
	 * @return
	 */
	final public static FileFilter nameFileter(final String fileName) {
		return new FileFilter() {
			public boolean accept(File pathname) {
				// 去掉隐藏文件夹
				if (pathname.isDirectory() && pathname.isHidden()) { 
					return false;
				}
				// 去掉隐藏文件
				else if (pathname.isFile() && pathname.isHidden()) {
					return false;
				}
				//根据文件名进行like过滤
				else if(pathname.isFile() && StringUtils.isNotEmpty(fileName) && !StringUtils.hasSubString(pathname.getName(), fileName)) {
					return false;
				}
				else {
					return true;
				}
			}
		};
	}
	
	/**
	 * 创建文件
	 * 
	 * @param destFileName
	 * @return
	 */
	public static boolean createFile(String destFileName) {
		File file = new File(destFileName);

		if (destFileName.endsWith(File.separator)) {
			System.out.println("创建单个文件" + destFileName + "失败，目标不能是目录！");
			return false;
		}
		if (!file.getParentFile().exists()) {
			System.out.println("目标文件所在路径不存在，准备创建。。。");
			if (!file.getParentFile().mkdirs()) {
				System.out.println("创建目录文件所在的目录失败！");
				return false;
			}
		}

		// 创建目标文件
		try {
			if (file.createNewFile()) {
				System.out.println("创建单个文件" + destFileName + "成功！");
				return true;
			} else {
				System.out.println("创建单个文件" + destFileName + "失败！");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("创建单个文件" + destFileName + "失败！");
			return false;
		}
	}

	/**
	 * 将字符串写入文件
	 * @param filePath
	 * @param str
	 */
	public static void writeStrToFile(String filePath, String str){
		File f = new File(filePath);
		try {
			// 先写入文件
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(f), "GBK"));
			output.write(str);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将文件按规则排序<br>
	 * 规则：目录排在前面，按字母顺序排序文件列表<br>
	 * @param files
	 * @return
	 */
	public static List<File> getSortfiles(List<File> files) {		
		Collections.sort(files, new Comparator<File>(){
			@Override
			public int compare(File o1, File o2) {
				if(o1.isDirectory() && o2.isFile())
					return -1;
				if(o1.isFile() && o2.isDirectory())
					return 1;
				return o1.getName().compareTo(o2.getName());
			}
		});
		return files;
	}
}