package com.bupt.ios.decompile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class UnzipIpa {
	private static String fileSeparator = System.getProperty("file.separator");

	public static void unzip(String filePath,String desDir) throws IOException {
		File pathFile = new File(desDir);
		if(!pathFile.exists()){
			pathFile.mkdirs();
		}
		ZipFile zip = new ZipFile(filePath);
		for(Enumeration entries = zip.entries();entries.hasMoreElements();){
			ZipEntry entry = (ZipEntry)entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (desDir+fileSeparator+zipEntryName);
			//判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf(fileSeparator)));
			if(!file.exists()){
				file.mkdirs();
			}
			//判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if(new File(outPath).isDirectory()){
				continue;
			}
			//输出文件路径信息
//			System.out.println(outPath);
			
			OutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while((len=in.read(buf1))>0){
				out.write(buf1,0,len);
			}
			in.close();
			out.close();
			}
//		System.out.println("******************解压完毕********************");
	}  

//	public static void main(String[] args) {
//
//		try {
//			UnzipIpa.unzip("/Users/konghaohao/Desktop/DamnVulnerableiOSApp.ipa", "/Users/konghaohao/Desktop/");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}