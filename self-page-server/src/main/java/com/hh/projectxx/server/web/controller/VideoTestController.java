package com.hh.projectxx.server.web.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hh.projectxx.base.util.MultipartFileSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class VideoTestController{
	
	@RequestMapping(value = "/video", method = RequestMethod.GET)
	   public void index(HttpServletRequest request, HttpServletResponse response) {
	  
	       //File file = new File("/Users/zhangli/Documents/workspace/projectxx/webapp/video/test.mp4");
	       Path path = Paths.get("/home/test/projectxx/webapp/video/test.mp4");
	       try {
			MultipartFileSender.fromPath(path)
			   .with(request)
			   .with(response)
			   .serveResource();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       }
	  
	   
}
