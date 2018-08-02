package com.hh.projectxx.server;

import com.hh.projectxx.server.config.DuopaiConfig;
import com.hh.projectxx.server.web.controller.GeneralController;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.GzipFilter;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.IntrospectorCleanupListener;

import java.io.File;

public class ProjectXXServer extends AbstractServer {

	private static Logger logger = LoggerFactory.getLogger(ProjectXXServer.class);

	private static ProjectXXServer server;

	@Override
	public void setupServer() throws Exception {
		// set thrift property
		System.setProperty("THRIFT_SOCKET_TIMEOUT", "10000");
		System.setProperty("THRIFT_FRAMED_TRANSPORT", "true");

		setupHttpServer(getFileInRoot("webapp").getAbsolutePath());
	}

	@Override
	protected void setupWebContext(WebAppContext webapp) {
		setWebContextParam(webapp);

		// add filter(urlrewriter, gzip)
		webapp.addFilter(GzipFilter.class, "/*", null);
		//webapp.addFilter(UrlRewriteFilter.class, "/*", EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST));

		addSpringContextListener(webapp);
		addSpringCleanupListener(webapp);

		// add default servlet handler for static resources
		addDefaultServlet(webapp);

		// add spring mvc servlet
		addSpringMvcServlet(webapp);

		// redefine error page
		addErrorPage(webapp, 400, GeneralController.URL_400);
		addErrorPage(webapp, 404, GeneralController.URL_404);
		addErrorPage(webapp, 500, GeneralController.URL_500);
		addErrorPage(webapp, Throwable.class, GeneralController.URL_500);


	}

	private void addSpringContextListener(WebAppContext webapp) {
		webapp.addEventListener(new ContextLoaderListener());
	}

	private void addSpringCleanupListener(WebAppContext webapp) {
		webapp.addEventListener(new IntrospectorCleanupListener());
	}

	private void setWebContextParam(WebAppContext webapp) {
		webapp.setDisplayName("DuopaiServer");

		webapp.setInitParameter("contextConfigLocation", "classpath:/root-context.xml");
		webapp.setInitParameter("webAppRootKey", "duopai.server.root");
	}

	private void addDefaultServlet(WebAppContext webapp) {
		ServletHolder defaultServlet = new ServletHolder(DefaultServlet.class);
		defaultServlet.setInitOrder(0);
		defaultServlet.setDisplayName("defaultServlet");
		defaultServlet.setInitParameter("maxCacheSize", "256000000");
		defaultServlet.setInitParameter("maxCachedFileSize", "10000000");
		defaultServlet.setInitParameter("maxCachedFiles", "1000");
		defaultServlet.setInitParameter("cacheType", "both");
		defaultServlet.setInitParameter("gzip", "true");

		webapp.addServlet(defaultServlet, "*.jpg");
		webapp.addServlet(defaultServlet, "*.png");
		webapp.addServlet(defaultServlet, "*.gif");
		webapp.addServlet(defaultServlet, "*.swf");
		webapp.addServlet(defaultServlet, "*.ico");
		webapp.addServlet(defaultServlet, "*.js");
		webapp.addServlet(defaultServlet, "*.css");
	}

	private void addSpringMvcServlet(WebAppContext webapp) {
		ServletHolder servlet = new ServletHolder(DispatcherServlet.class);
		servlet.setDisplayName("SpringMvcDispatcher");
		servlet.setInitParameter("contextConfigLocation", "classpath:/servlet-context.xml");
		servlet.setInitOrder(2);

		// 监听所有的url, spring mvc的action没有任何的后缀
		webapp.addServlet(servlet, "/*");
	}

	@Override
	protected String getHttpListenAddress() {
		return DuopaiConfig.getHttpListenAddress();
	}

	@Override
	protected int getHttpListenPort() {
		return DuopaiConfig.getHttpListenPort();
	}

	@Override
	protected String getHttpServerWorkerName() {
		return DuopaiConfig.getHttpServerWorkerName();
	}
	
	@Override
	protected QueuedThreadPool createQueuedThreadPool() {
		int maxThreads = DuopaiConfig.getJettyMaxThreads();
		int minThreads = DuopaiConfig.getJettyMinThreads();
		int maxQueued = DuopaiConfig.getJettyMaxQueued();
		QueuedThreadPool threadPool = new QueuedThreadPool();
		if (maxThreads > 0) {
			threadPool.setMaxThreads(maxThreads);
		}
		if (minThreads > 0) {
			threadPool.setMinThreads(minThreads);
		}
		if (maxQueued > 0) {
			threadPool.setMaxQueued(maxQueued);
		}
		return threadPool;
	}

	public static void main(String[] args) {
		server = new ProjectXXServer();
		try {
			String configFolder = DuopaiConfig.getConfigFolder();
			server.setup(new File(configFolder).getParent());
			server.startServer();
		} catch (Throwable e) {
			logger.error("Exception encountered during startup.", e);

			System.exit(3);
		}
	}
	
	public static File getTemplateFolder() {
		return server.getFileInRoot("webapp/WEB-INF/templates");
	}
	
	@Override
	public void stopServer() throws Exception {
		super.stopServer();
	}
}
