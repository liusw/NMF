package com.boyaa.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.util.ServletUtil;

public class FileUpLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(FileUpLoadServlet.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	private static final String CONTENT_TYPE = "application/x-javascript";
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8"); // 设置编码
		request.setCharacterEncoding(Constants.DEFAULT_ENCODING);
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(Constants.DEFAULT_ENCODING);
		
		String action = request.getParameter("action");
		if(action!=null && action.equals("uploadImages")){
			updateImages(request, response);
			return;
		}
		updateFile(request, response);
	}

	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void updateImages(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		File file = new File(Constants.IMAGES_DIR);
		if(!file.isDirectory()){
			file.mkdirs();
		}
		factory.setRepository(file);
		
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024);
		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		String newFileName = "";

		JSONObject jsonObject = JSONUtil.getJSONObject();
		try {
			// 可以上传多个文件
			List<FileItem> list = (List<FileItem>) upload.parseRequest(request);
			for (FileItem item : list) {
				// 如果获取的 表单信息是普通的 文本 信息
				if (!item.isFormField()) {
					// 获取路径名
					String value = item.getName();
					// 索引到最后一个反斜杠
					int ext = value.lastIndexOf(".");
					newFileName = new Date().getTime()+(ext==-1?"":value.substring(ext));
					OutputStream out = new FileOutputStream(new File(Constants.IMAGES_DIR,newFileName));
					InputStream in = item.getInputStream();

					int length = 0;
					byte[] buf = new byte[1024];
					logger.info("获取上传文件的总共的容量：" + item.getSize());

					while ((length = in.read(buf)) != -1) {
						out.write(buf, 0, length);
					}
					in.close();
					out.close();
				}
			}
			jsonObject.put("error", 0);
			jsonObject.put("url", Constants.UPLOAD_IMAGES_DOMAIN+newFileName);
			response.getWriter().print(jsonObject.toString());
			return;
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
			try {
				jsonObject.put("error", 1);
				jsonObject.put("message", "上传失败");
			} catch (JSONException e1) {
				errorLogger.error(e.getMessage());
			}
		}
		response.getWriter().print(jsonObject.toString());
	}

	private void updateFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
		// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
		/**
		 * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem
		 * 格式的 然后再将其真正写到 对应目录的硬盘上
		 */
		factory.setRepository(new File(Constants.FILE_DIR));
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024);

		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		String newFileName = "";
		String getFileColumn = null;

		try {
			// 可以上传多个文件
			List<FileItem> list = (List<FileItem>) upload.parseRequest(request);

			for (FileItem item : list) {
				// 获取表单的属性名字
				//String name = item.getFieldName();

				// 如果获取的 表单信息是普通的 文本 信息
				if (item.isFormField()) {
					// 获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
					//String value = item.getString();
					if("getFileColumn".equals(item.getFieldName())){
						getFileColumn=item.getString();
					}
				} else {// 对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
					/**
					 * 以下三步，主要获取 上传文件的名字
					 */
					// 获取路径名
					String value = item.getName();
					// 索引到最后一个反斜杠
					int ext = value.lastIndexOf(".");
					/*if(ext==-1 || !value.substring(ext).equals("txt") || !value.substring(ext).equals("csv")){
						ServletUtil.responseRecords(response, null, success);	
					}*/
					
					
					newFileName = new Date().getTime()+(ext==-1?"":value.substring(ext));

					// 真正写到磁盘上
					// 它抛出的异常 用exception 捕捉
					// item.write( new File(path,filename) );//第三方提供的

					// 手动写的
					OutputStream out = new FileOutputStream(new File(Constants.FILE_DIR,newFileName));

					InputStream in = item.getInputStream();

					int length = 0;
					byte[] buf = new byte[1024];

					logger.info("获取上传文件的总共的容量：" + item.getSize());

					// in.read(buf) 每次读到的数据存放在 buf 数组中
					while ((length = in.read(buf)) != -1) {
						// 在 buf 数组中 取出数据 写到 （输出流）磁盘上
						out.write(buf, 0, length);

					}

					in.close();
					out.close();
				}
			}
			
			ResultState success = new ResultState(ResultState.SUCCESS);
			success.setMsg("添加成功");
			JSONObject json = JSONUtil.getJSONObject();
			json.put("fileName", newFileName);
			
			//判断是否要获取第一行
			if(StringUtils.isNotBlank(getFileColumn)){
				//读取文件的第一行
				json.put("fileColumn",getFileColumn(Constants.FILE_DIR+newFileName));
			}
			
			success.setOther(json);
			ServletUtil.responseRecords(response, null, success);	
			return;
		} catch (FileUploadException e) {
			errorLogger.error(e.getMessage());
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
		}
		ResultState failure = new ResultState(ResultState.FAILURE);
		failure.setMsg("上传失败");
		ServletUtil.responseRecords(response, null, failure);
	}
	
	public String getFileColumn(String path) {
		File file = new File(path);
	
		if(file.getName().startsWith(".")){
			return null;
		}
		
		String column = null;
		if(file.getName().endsWith(".gz")) {
			column = gzip(file);
		} else if(file.getName().endsWith(".zip")) {
			column = zip(file);
		} else {
			column = text(file);
		}
		
		return column;
	}
	
	private String getColumn(BufferedReader br) throws IOException{
		String line = null;
		StringBuilder sb = new StringBuilder();
		while((line = br.readLine()) != null) {
			if(StringUtils.isNotBlank(line)){
				String[] columns = line.split(",");
				for(int i=0;i<columns.length;i++){
					sb.append("c"+i).append(i!=columns.length-1?",":"");
				}
			}
			break;
		}
		return sb.toString();
	}
	
	public String text(File file) {
		BufferedReader br = null;
		String column = null;
		try {
			br = new BufferedReader(new FileReader(file));
			column = getColumn(br);
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
		} finally{
			try {
				if(br != null) br.close();
			} catch (IOException e) {
				errorLogger.error(e.getMessage());
			}
		}
		return column;
	}
	
	public String gzip(File file) {
		InputStream is = null;
		GZIPInputStream gis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		String column = null;
		try {
			is = new FileInputStream(file);
			gis = new GZIPInputStream(is);
			isr = new InputStreamReader(gis);
			br = new BufferedReader(isr);
			column = getColumn(br);
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
		} finally {
			try {
				if(is != null) is.close();
				if(gis != null) gis.close();
				if(isr != null) isr.close();
				if(br != null) br.close();
			} catch (IOException e) {
				errorLogger.error(e.getMessage());
			}
		}
		return column;
	}
	
	public String zip(File file) {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		ZipFile zip = null;
		String column = null;
		try {
			zip = new ZipFile(file);
			Enumeration<? extends ZipEntry> all = zip.entries();
			while (all.hasMoreElements()) {
				is = zip.getInputStream(all.nextElement());
				isr = new InputStreamReader(is);
				br = new BufferedReader(isr);
				column=getColumn(br);
				if(is != null) is.close();
				if(isr != null) isr.close();
				if(br != null) br.close();
			}
		} catch (Exception e) {
			errorLogger.error("open file=" + file.getAbsolutePath() +  " error: " + e.getMessage());
		} finally {
			try {
				if(zip != null) zip.close();
				if(br != null) br.close();
			} catch (IOException e) {
				errorLogger.error(e.getMessage());
			}
		}
		return column;
	}
}