package com.boyaa.mf.service.task;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boyaa.base.utils.HDFSUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.privilege.Resources;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.vo.LoginUserInfo;

@Service
public class DownloadService {
	static Logger errorLogger = Logger.getLogger("errorLogger");
	
	@Autowired
	private ProcessInfoService processInfoService;
	@Autowired
	private TaskService taskService;
	
	/**
	 *  预览
	 * @param request
	 * @param response
	 */
	public void preview(HttpServletRequest request,HttpServletResponse response){
		String tid = request.getParameter("tid");
		String pid = request.getParameter("pid");
		
		LoginUserInfo userInfo = (LoginUserInfo) request.getSession().getAttribute(Constants.USER_LOGIN_SESSION_NAME);
		if (StringUtils.isNotBlank(tid) && StringUtils.isBlank(pid)) {
			Task task = taskService.findById(Integer.parseInt(tid));
			if (task != null && StringUtils.isNotBlank(task.getPath())) {
				if(check(userInfo,task)){
					if(task.getPath()!=null && task.getPath().startsWith(HDFSUtil.root)){
						previewHDFSFile(task.getPath(),response);
					}else{
						previewLocalFile(task.getPath(), request, response);
					}
				}else{
					downError("没有权限下载此文件",request,response);
				}
			}else{
				downError("传入参数不正确",request,response);
			}
		}else if(StringUtils.isNotBlank(tid) && StringUtils.isNotBlank(pid)){
			ProcessInfoService processInfoService = new ProcessInfoService();
			ProcessInfo processInfo = processInfoService.findById(Integer.parseInt(pid));
			if(processInfo!=null && StringUtils.isNotBlank(processInfo.getPath()) && processInfo.getTaskId()==Integer.parseInt(tid)){
				Task task = taskService.findById(Integer.parseInt(tid));
				if(check(userInfo,task)){
					if(processInfo.getPath()!=null && processInfo.getPath().startsWith(HDFSUtil.root)){
						previewHDFSFile(processInfo.getPath(),response);
					}else{
						previewLocalFile(processInfo.getPath(),request,response);
					}
				}else{
					downError("没有权限下载此文件",request,response);
				}
			}else{
				downError("传入参数不正确",request,response);
			}
		}else{
			downError("传入参数不正确",request,response);
		}
	}
	
	public void previewLocalFile(String fileName,HttpServletRequest request,HttpServletResponse response){
		String path = Constants.FILE_DIR+fileName;
		BufferedReader br = null;
		try {
			File file = new File(path);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			
			previewFile(fileName,file.length(),response,br);
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
		} finally {
			close(br);
		}
	}
	
	public void previewHDFSFile(String fileName,HttpServletResponse response){
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = HDFSUtil.download(fileName);
			if(fileName.endsWith(".gz")){
				br = new BufferedReader(new InputStreamReader(new GZIPInputStream(is)));
			}else{
				br = new BufferedReader(new InputStreamReader(is));
			}
			previewFile(fileName,HDFSUtil.getSize(fileName),response,br);
		} catch (IOException e) {
			errorLogger.error(e.getMessage());
		}finally {
			close(br);
			close(is);
		}
	}
	
	public void previewFile(String fileName,Long fileSize,HttpServletResponse response,BufferedReader br){
		response.setContentType("text/html");

		OutputStream os = null;
		Writer out = null;
		try {
			out = response.getWriter();  
			String s;
			StringBuffer sb = new StringBuffer();
			int lineNum = 0;
			while(StringUtils.isNotBlank(s=br.readLine())){
				if(lineNum>10){
					break;
				}
				
				sb.append(s).append("<br/>");
				lineNum++;
			}

			// 将输出字节流写到response的输出流中
			out.write(sb.toString());
		} catch (IOException e) {
			errorLogger.error(e.getMessage());
		} finally {
			close(os);
			close(out);
		}
	}
	
	public void downFile(HttpServletRequest request,HttpServletResponse response) {
		String tid = request.getParameter("tid");
		String pid = request.getParameter("pid");
		
		LoginUserInfo userInfo = (LoginUserInfo) request.getSession().getAttribute(Constants.USER_LOGIN_SESSION_NAME);
		if (StringUtils.isNotBlank(tid) && StringUtils.isBlank(pid)) {
			Task task = taskService.findById(Integer.parseInt(tid));
			if (task != null && StringUtils.isNotBlank(task.getPath())) {
				if(check(userInfo,task)){
					if(task.getPath()!=null && task.getPath().startsWith(HDFSUtil.root)){
						downloadHDFSFile(task.getPath(),response);
					}else{
						downloadLocalFile(task.getPath(), request, response);
					}
				}else{
					downError("没有权限下载此文件",request,response);
				}
			}else{
				downError("传入参数不正确",request,response);
			}
		}else if(StringUtils.isNotBlank(tid) && StringUtils.isNotBlank(pid)){
			ProcessInfo processInfo = processInfoService.findById(Integer.parseInt(pid));
			if(processInfo!=null && StringUtils.isNotBlank(processInfo.getPath()) && processInfo.getTaskId()==Integer.parseInt(tid)){
				Task task = taskService.findById(Integer.parseInt(tid));
				if(check(userInfo,task)){
					if(processInfo.getPath()!=null && processInfo.getPath().startsWith(HDFSUtil.root)){
						downloadHDFSFile(processInfo.getPath(),response);
					}else{
						downloadLocalFile(processInfo.getPath(),request,response);
					}
				}else{
					downError("没有权限下载此文件",request,response);
				}
			}else{
				downError("传入参数不正确",request,response);
			}
		}else{
			downError("传入参数不正确",request,response);
		}
	}
	
	public void downloadLocalFile(String fileName,HttpServletRequest request,HttpServletResponse response){
		String path = Constants.FILE_DIR+fileName;
		String fileType = request.getSession().getServletContext().getMimeType(path);
		if (fileType==null) {
			fileType = "application/octet-stream";
		}else{
			response.setContentType(fileType);
		}
		InputStream is = null;
		try {
			File file = new File(path);
			is = new BufferedInputStream(new FileInputStream(file));
			downloadFile(fileName,file.length(),response,is);
		} catch (FileNotFoundException e) {
			errorLogger.error(e.getMessage());
		} finally {
			close(is);
		}
	}
	
	public void downloadHDFSFile(String fileName,HttpServletResponse response){
		String fileType = "application/octet-stream";
		response.setContentType(fileType);
		InputStream is = null;
		try {
			is = HDFSUtil.download(fileName);
			downloadFile(fileName,HDFSUtil.getSize(fileName),response,is);
		} catch (IOException e) {
			errorLogger.error(e.getMessage());
		}finally {
			close(is);
		}
	}
	public void downloadFile(String fileName,Long fileSize,HttpServletResponse response,InputStream is){
		if(fileSize!=null && fileSize.longValue()>0){
			response.addHeader("Content-Length",fileSize.toString());
		}
		//设置文件类型
		response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");

		OutputStream os = null;
		try {
			// 定义输出字节流
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// 定义response的输出流
			os = new BufferedOutputStream(response.getOutputStream());
			// 定义buffer
			byte[] buffer = new byte[4 * 1024]; // 4k Buffer
			int read = 0;

			// 从文件中读入数据并写到输出字节流中
			while ((read = is.read(buffer)) != -1) {
				baos.write(buffer, 0, read);
			}

			// 将输出字节流写到response的输出流中
			os.write(baos.toByteArray());
		} catch (IOException e) {
			errorLogger.error(e.getMessage());
		} finally {
			close(os);
		}
	}
	
	private void downError(String msg,HttpServletRequest request,HttpServletResponse response){
		try {
			request.setAttribute("title", "下载提示");
			request.setAttribute("info",msg);
			request.getRequestDispatcher("/WEB-INF/jsp/common/error.jsp").forward(request,response);
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
		}  
	}

	/**
	 * 较验权限
	 * @param userInfo
	 * @param task
	 * @return
	 */
	private boolean check(LoginUserInfo userInfo,Task task) {
		boolean downFlag = false;
		
		//判断task里面有没有这个接收邮件
		if(StringUtils.isNotBlank(task.getEmail())){
			String[] emails = task.getEmail().split(";");
			for(int i=0;i<emails.length;i++){
				if(emails[i].equalsIgnoreCase(userInfo.getEmail())){
					downFlag = true;
					break;
				}
			}
			if(downFlag) return downFlag;
		}
		
		//判断他是否有下载所有附件的权限
		List<Resources> resources = userInfo.getResources();
		for (Resources resource : resources) {
			if (StringUtils.isNotBlank(resource.getUrl()) && Constants.downAllFile.startsWith(resource.getUrl())) {
				downFlag = true;
				break;
			}
		}
		return downFlag;
	}
	
	
	private void close(OutputStream os){
		if(os!=null){
			try {
				os.close();
			} catch (IOException e) {
				errorLogger.error(e.getMessage());
			}
		}
	}
	private void close(InputStream is){
		if(is!=null){
			try {
				is.close();
			} catch (IOException e) {
				errorLogger.error(e.getMessage());
			}
		}
	}
	
	private void close(Writer os){
		if(os!=null){
			try {
				os.close();
			} catch (IOException e) {
				errorLogger.error(e.getMessage());
			}
		}
	}
	private void close(Reader is){
		if(is!=null){
			try {
				is.close();
			} catch (IOException e) {
				errorLogger.error(e.getMessage());
			}
		}
	}
}
