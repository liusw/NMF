package com.boyaa.mf.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;

import com.boyaa.mf.util.SystemContext;

public class SystemContextFilter implements Filter{
	private Integer pageSize;
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		try {
			int currentPage = 1;
			String _pageSize = req.getParameter("iDisplayLength");
			if(!StringUtils.isBlank(_pageSize)){
				int temp_pageSize = Integer.parseInt(_pageSize);
				if(temp_pageSize>0){
					pageSize=temp_pageSize;
				}
			}
			
			String iDisplayStart = req.getParameter("iDisplayStart");
			if(!StringUtils.isBlank(iDisplayStart)){
				int start = Integer.parseInt(iDisplayStart);
				currentPage = start/pageSize+1;
			}
			
			String _page=req.getParameter("page");
			int temp_page = 0;
			if(!StringUtils.isBlank(_page)){
				temp_page = Integer.parseInt(req.getParameter("page"));
			}
			if(temp_page<=0){
				_page=req.getParameter("page");
			}
			if(temp_page>0){
				currentPage=temp_page;
			}
			
			SystemContext.setPageIndex(currentPage);
			SystemContext.setPageSize(pageSize);
			
			chain.doFilter(req, resp);
		} finally {
			SystemContext.removePageIndex();
			SystemContext.removePageSize();
		}
	}
	
	@Override
	public void init(FilterConfig cfg) throws ServletException {
		try {
			pageSize = Integer.parseInt(cfg.getInitParameter("pageSize"));
		} catch (NumberFormatException e) {
			pageSize = 10;
		}
	}
}
