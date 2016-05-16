package com.boyaa.mf.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController{
	/**
	 * 页面跳转请求
	 */
	@RequestMapping("/{module}/{action}.htm")
	public String page(@PathVariable String module, @PathVariable String action) {

		return module+"/"+action;
	}
}
