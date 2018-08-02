package com.hh.projectxx.server.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 系统通用性的controller. 如404, 403, 500等.
 * 
 */
@Controller
public class GeneralController {

	public static final String URL_400 = "/";

	public static final String URL_404 = "/";

	public static final String URL_500 = "/";

	@RequestMapping(value = "/")
	public String defaultPage() {
		return "profile";
	}

}
