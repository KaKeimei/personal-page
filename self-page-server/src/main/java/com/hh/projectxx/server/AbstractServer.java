package com.hh.projectxx.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.session.AbstractSessionManager;
import org.eclipse.jetty.server.session.HashSessionIdManager;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public abstract class AbstractServer {

	private static Logger logger = LoggerFactory.getLogger(AbstractServer.class);

	private Server httpServer;
	private String serverRootPath;

	public void setup(String serverRootPath) throws Exception {
		this.serverRootPath = serverRootPath;

		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				logger.error("Fatal exception in thread " + t, e);

				if (e instanceof OutOfMemoryError) {
					System.exit(100);
				}
			}
		});

		setupServer();
	}

	protected abstract void setupServer() throws Exception;

	public void startServer() throws Exception {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					stopServer();
				} catch (Exception e) {
				}
			}
		});

		if (httpServer != null) {
			logger.info("Http server starting up...");

			httpServer.start();
			httpServer.join();
		}
	}

	public void stopServer() throws Exception {
		if (httpServer != null) {
			httpServer.stop();
		}
	}

	protected void setupWebContext(WebAppContext webapp) {

	}
	
	protected void setupHttpServer(String resourceBase) {
		setupHttpServer(resourceBase, "/");
	}

	/**
	 * @param resourceBase
	 * @param contextPath  上下文路径
	 */
	protected void setupHttpServer(String resourceBase, String contextPath) {
		// setup http server
		httpServer = new Server();
		Connector connector = new SelectChannelConnector();

		String listenAddr = getHttpListenAddress(); //SalmonServerDescriptor.getListenAddressString();
		if (listenAddr != null && listenAddr.length() > 0) {
			connector.setHost(listenAddr);
		}
		connector.setPort(getHttpListenPort());
		httpServer.setConnectors(new Connector[] { connector });

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath(contextPath);
		webapp.setResourceBase(resourceBase);

		setupWebContext(webapp);

		AbstractSessionManager sessionManager = getHttpSessionManager();
		if (sessionManager != null) {
			// disable ;jsessionid=
			sessionManager.setSessionIdPathParameterName(null);
			// sessionManager.setMaxInactiveInterval(0);
			webapp.setSessionHandler(new SessionHandler(sessionManager));
		}

		httpServer.setHandler(webapp);

		// work name
		String workerName = getHttpServerWorkerName(); //SalmonServerDescriptor.getServerWorkerName();
		if (workerName != null) {
			HashSessionIdManager sessionIDManager = new HashSessionIdManager();
			sessionIDManager.setWorkerName(workerName);

			httpServer.setSessionIdManager(sessionIDManager);
		}
		
		httpServer.setThreadPool(createQueuedThreadPool());
	}
	
	protected QueuedThreadPool createQueuedThreadPool() {
		return new QueuedThreadPool();
	}

	protected AbstractSessionManager getHttpSessionManager() {
		return new HashSessionManager();
	}

	protected String getHttpListenAddress() {
		return "0.0.0.0";
	}

	protected int getHttpListenPort() {
		return 8080;
	}

	protected String getHttpServerWorkerName() {
		return null;
	}

	protected File getFileInRoot(String subPath) {
		return new File(serverRootPath, subPath);
	}

	protected void addErrorPage(WebAppContext webapp, int errCode, String errPage) {
		ErrorPageErrorHandler errorPageHandler = (ErrorPageErrorHandler) webapp.getErrorHandler();
		errorPageHandler.addErrorPage(errCode, errPage);
	}

	protected void addErrorPage(WebAppContext webapp, Class<? extends Throwable> exception, String errPage) {
		ErrorPageErrorHandler errorPageHandler = (ErrorPageErrorHandler) webapp.getErrorHandler();
		errorPageHandler.addErrorPage(exception, errPage);
	}
}
