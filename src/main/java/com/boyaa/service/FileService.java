package com.boyaa.service;

import java.io.File;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.boyaa.base.utils.FileLastModifiedComparator;
import com.boyaa.mf.constants.Constants;

public class FileService {
	static Logger taskLogger = Logger.getLogger("taskLogger");
	static Logger errorLogger = Logger.getLogger("errorLogger");
	final static double DIR_MAX_SIZE = 50;//G为单位
	final static double File_MAX_SIZE = 5;//G为单位
	final static String UNIT="G";

	public void scanFile(){
		File dir = new File(Constants.FILE_DIR);
		double size = getFileSize(dir,UNIT);
		taskLogger.info("系统自动清理文件扫描<"+Constants.FILE_DIR+">开始,目录大小为:"+size);
		if(size>=DIR_MAX_SIZE){
			File[] list = dir.listFiles();
			for(File f : list) {
				double fileSize = getFileSize(f,UNIT);
				if(fileSize>=File_MAX_SIZE){//如果文件大于等于5G,清除掉
					deleteFile(f);
				}
			}
			
			//再次判断整个目录的大小,如果还是大于50G,递归删除最先生成的文件,直到整个目录的大小小于50G
			dir = new File(Constants.FILE_DIR);
			size = getFileSize(dir,UNIT);
			if(size>=DIR_MAX_SIZE){
				FileLastModifiedComparator com = new FileLastModifiedComparator();
				list = dir.listFiles();
				Arrays.sort(list, com);
				for(File f : list) {
					f.delete();
					size = getFileSize(dir,UNIT);
					if(size<DIR_MAX_SIZE){
						break;
					}
				}
			}
		}
		taskLogger.info("系统自动清理文件扫描<"+Constants.FILE_DIR+">结束,目录大小为:"+size);
	}
	
	
	private void deleteFile(File file){
		String fileName = file.getName();
		if(file.isFile()){
			file.delete();
			taskLogger.info("系统清理导出文件:fileName="+fileName);
		}else{
			errorLogger.error("系统清理导出文件失败:filePath="+file.getAbsolutePath()+",fileName="+fileName);
		}
	}

	/**
	 * 获取文件的大小
	 * @param file
	 * @param unit 单位,K/M/G
	 * @return
	 */
	public static double getFileSize(File file,String unit) {
		// 判断文件是否存在
		if (file.exists()) {
			// 如果是目录则递归计算其内容的总大小
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				double size = 0;
				for (File f : children)
					size += getFileSize(f,unit);
				return size;
			} else {// 如果是文件则直接返回其大小,以“G”为单位
				if(unit.equals("G")){
					return (double) file.length() / 1024 / 1024 / 1024;
				}else if(unit.equals("M")){
					return (double) file.length() / 1024 / 1024;
				}else if(unit.equals("K")){
					return (double) file.length() / 1024;
				}else{
					return file.length();
				}
			}
		} else {
			errorLogger.error("文件或者文件夹不存在，请检查路径是否正确！file:"+file.getPath()+",fileName:"+file.getName());
			return 0.0;
		}
	}
	
	/**
	 * 获取一个文件的长度
	 * @param path
	 * @return
	 */
	public static long getFileSize(String path){
		File file = new File(path);
		return file.length();
	}
}