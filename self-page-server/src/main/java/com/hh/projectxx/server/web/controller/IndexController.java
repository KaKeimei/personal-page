package com.hh.projectxx.server.web.controller;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hh.projectxx.base.db.field.UserStatus;

import org.apache.commons.lang3.StringUtils;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hh.projectxx.base.db.entity.User;

import com.hh.projectxx.base.manager.UserManager;
import com.hh.projectxx.base.util.AuthUtil;
import com.hh.projectxx.base.util.CookieUtil;
import com.hh.projectxx.base.util.CryptUtil;
import com.hh.projectxx.base.util.StringUtil;



@Controller
public class IndexController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Resource
	private UserManager userManager;
	
	@RequestMapping(value = "/index")
	public String getIndex(){
		return redirect("/web/article!list");
	}
	
//	@RequestMapping(value = "/signup")
//	public String userSignUp(HttpServletRequest request,HttpServletResponse response, String passport, String name, String pwd1,String pwd2, String captcha, Model model, String ref){
//		String ip = getRealIp();
//		if (StringUtils.isBlank(passport) || StringUtils.isBlank(pwd1) || StringUtils.isBlank(pwd2)) {
//			return ("auth/signup");
//		}
//		String captchaString = (String) request.getSession().getAttribute(AuthUtil.USER_CAPTCHA);
//		if (StringUtil.isEmpty(captcha)) {
//			model.addAttribute("failMsg", "验证码不能为空");
//			return ("auth/signup");
//		}
//		if (captchaString == null || !captchaString.equalsIgnoreCase(captcha)) {
//			model.addAttribute("failMsg", "验证码错误请重试");
//			return ("auth/signup");
//		}
//		if (!pwd1.equals(pwd2)) {
//			model.addAttribute("failMsg", "两次密码输入不一致");
//			return ("auth/signup");
//		}
//		User user = userManager.getUserByPassport(passport);
//		if (user != null) {
//			model.addAttribute("failMsg", "该账号已注册");
//			return ("auth/signup");
//		}
//		user = new User();
//		user.setPassport(passport);
//		user.setPassword(CryptUtil.sha1(pwd1));
//		user.setCreateTime(new Date());
//		user.setName(name);
//		user.setCoinCount(0);
//		user.setStatus(UserStatus.NORMAL.value());
//		if (!userManager.userSignUp(user)) {
//			model.addAttribute("failMsg", "注册失败请重试");
//			return ("auth/signup");
//		}
//		try {
//			adminLogin(request, response, passport, pwd1, ip);
//		} catch (Exception e) {
//			model.addAttribute("failMsg", e.getMessage());
//			return ("auth/login");
//		}
//		try {
//			response.sendRedirect("/web/index");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ("admin/admin_index");
//	}
	
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request,HttpServletResponse response, String username, String pwd, String captcha, Model model, String ref) {
		String ip = getRealIp();
		if (StringUtils.isBlank(username) && StringUtils.isBlank(pwd)) {
			return ("auth/login");
		}
		String captchaString = (String) request.getSession().getAttribute(AuthUtil.USER_CAPTCHA);
		if (StringUtil.isEmpty(captcha)) {
			model.addAttribute("failMsg", "验证码不能为空");
			return ("auth/login");
		}
		if (captchaString == null || !captchaString.equalsIgnoreCase(captcha)) {
			model.addAttribute("failMsg", "验证码错误请重试");
			return ("auth/login");
		}

		try {
			adminLogin(request, response, username, pwd, ip);
		} catch (Exception e) {
			model.addAttribute("failMsg", e.getMessage());
			return ("auth/login");
		}
		
		if (ref == null) {
			return redirect("/web/index");
		}
		return redirect(ref);
	}
	
	@RequestMapping(value = "/no_auth")
	public String noAuth() {
		return ("auth/no_auth");
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.addCookie(new Cookie(CookieUtil.COOKIE_USER_KEY, ""));
		response.addCookie(new Cookie(CookieUtil.COOKIE_USER_ID, "0"));
		AuthUtil.adminLogout(request, response);
		request.setAttribute("adminUser", null);
		response.sendRedirect("/web/index");
		return ("admin/admin_index");
		
	}
	
	@RequestMapping(value = "/captcha")
	public void generateCaptcha(HttpServletResponse response){
		
		response.setContentType("application/x-png");
		ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
		cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
		cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
		ServletOutputStream fos;
		try {
			fos = response.getOutputStream();
			String captchaString = EncoderHelper.getChallangeAndWriteImage(cs, "png", fos);
			request.getSession().setAttribute(AuthUtil.USER_CAPTCHA, captchaString);
			fos.close();
		} catch (FileNotFoundException e) {
			logger.error("cannot generate captcha!", e);
		} catch (IOException e) {
			logger.error("cannot generate captcha!", e);
			
		}	
	}
	
	private int adminLogin(HttpServletRequest request, HttpServletResponse response, String passport, String pwd, String ip) throws Exception {
		if (StringUtils.isEmpty(passport) || StringUtils.isEmpty(pwd)) {
			throw new Exception("用户名密码错误，登录失败");
		}
		User user = userManager.getUserByPassport(passport);
		if (user == null) {
			throw new Exception("用户不存在，请注册");
		}
		String password = user.getPassword();
		if(CryptUtil.sha1(pwd).equals(password)){
			request.getSession().setAttribute(AuthUtil.USER_KEY, user.getId());
			CookieUtil.setCookie(request, response, CookieUtil.COOKIE_USER_ID, String.valueOf(user.getId()), CookieUtil.DEFAULT_AGE); ///cookie有maxage参数，如果不设置此项则关闭浏览器无法继续保持状态
			long date = System.currentTimeMillis();
			long expired = date + 1000 * 3600 * 24 * 10; ///10日过期
			CookieUtil.setCookie(request, response, CookieUtil.COOKIE_EXPIRE_TIME, String.valueOf(expired), CookieUtil.DEFAULT_AGE);
			CookieUtil.setCookie(request, response, CookieUtil.COOKIE_USER_KEY, CookieUtil.generateUserKey(password, String.valueOf(expired)), CookieUtil.DEFAULT_AGE); ///userkey由password,过期时间和服务器私钥合成
			request.setAttribute("adminUser", user);
			return 1;
		}
		throw new Exception("用户名密码错误，登录失败");
	}
}
