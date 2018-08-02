package com.hh.projectxx.server.web.interceptor;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 操作日志记录拦截器
 * @author yankai
 *
 */
public class OperLogInterceptor extends HandlerInterceptorAdapter {
	
	//@Resource
	//private AdminManager adminManager;
	
	@Override  
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		
		//只记录非POST的方法或者包含delete关键字的方法
		if( (!"get".equalsIgnoreCase(request.getMethod()) && !uri.matches(".+(?i)(login|logon|logout|logoff)+"))
				|| (uri.matches(".+(?i)(delete)+"))) {
			Map<String, String[]> params = request.getParameterMap();
			Set<Entry<String, String[]>> entrySet = params.entrySet();
			StringBuilder sb = new StringBuilder(512).append("Params:");
			for(Entry<String, String[]> entry:entrySet) {
				StringBuilder valueSb = new StringBuilder(128);
				String[] values = entry.getValue();
				if(values!=null && values.length>0) {
					for(String value:values) {
						if(valueSb.length()>0) {
							valueSb.append(",");
						}
						valueSb.append(value);
					}
				}
				sb.append(entry.getKey()).append("=");
				if(entry.getKey().indexOf("password") != -1){
					sb.append("******").append(";");
				} else {
					sb.append(valueSb.toString()).append(";");
				}
			}
			String comment = sb.toString();
			if(comment.length()>512) {
				comment = comment.substring(0, 509)+"...";
			}
/*			AdminOperLog operLog = new AdminOperLog();
			operLog.setUid(AuthUtil.getAdminUser(request));
			operLog.setCreateTime(new Date());
			operLog.setOperation(uri);
			operLog.setComment(comment);
			adminManager.insertAdminOperLog(operLog);
*/
		}
		
		return true;
		
	}  
}
