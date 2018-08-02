package com.hh.projectxx.server.config;

import org.apache.log4j.PropertyConfigurator;
import org.ho.yaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class DuopaiConfig {

	private static Logger logger = LoggerFactory.getLogger(DuopaiConfig.class);

	private static DuopaiConfig yamlConfig = null;

	private static String configFolder;
	private static String log4jConfig;
	private static long lastModified;

	static {
		configFolder = System.getProperty("projectxx-config");

		//config log
		log4jConfig = configFolder + File.separator + "log4j.properties";
		PropertyConfigurator.configure(log4jConfig);

		loadYaml();
		
		Timer t = new Timer("yaml-config-updater");
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				try {
					loadYaml();
				} catch (RuntimeException e) {
					logger.error("fail to load yaml config", e);
				}
			}
		}, 60 * 1000, 60 * 1000);
	}
	
	private static void loadYaml() {
		String cfgFileName = configFolder + File.separator + "duopai-conf.yaml";
		File cfgFile = new File(cfgFileName);
		if (cfgFile.lastModified() <= lastModified) {
			return;
		}
		logger.info("loading yaml config...");
		lastModified = cfgFile.lastModified();
		try {
			yamlConfig = Yaml.loadType(cfgFile, DuopaiConfig.class);
		} catch (FileNotFoundException e) {
			logger.error("Fatal error: YAML config file is not found. Could not start up server.");
			throw new IllegalArgumentException(e);
		}
	}

	public String jdbc_url;
	public String jdbc_user;
	public String jdbc_password;
	
	public String redis_cache;
	
	public String httpListenAddress;
	public int httpListenPort;
	public String httpServerWorkerName;

	public int jettyMaxThreads;
	public int jettyMinThreads;
	public int jettyMaxQueued;

	public String portal_domain;
	
	
	public String isNotifyServer;

	public String data_dir;
	
	public String env;
	
	public String photo_log_path;
	
	private String android_version;
	
	public String resource_url;
	
	public String photo_dir;
	
	
	public String getAndroid_version() {
		return android_version;
	}

	private String ios_version;
	
	public String getIos_version() {
		return ios_version;
	}

	public static String getConfigFolder() {
		return configFolder;
	}

	public static String getLog4jConfig() {
		return log4jConfig;
	}

	public static String getJdbcUrl() {
		return yamlConfig.jdbc_url;
	}

	public static String getJdbcUser() {
		return yamlConfig.jdbc_user;
	}
	
	public static String getJdbcPassword() {
		return yamlConfig.jdbc_password;
	}
	
	public static String getRedisCache() {
		return yamlConfig.redis_cache;
	}
	
	public static String getHttpListenAddress() {
		return yamlConfig.httpListenAddress;
	}

	public static int getHttpListenPort() {
		return yamlConfig.httpListenPort;
	}

	public static String getHttpServerWorkerName() {
		return yamlConfig.httpServerWorkerName;
	}
	
	public static int getJettyMaxThreads() {
		return yamlConfig.jettyMaxThreads;
	}
	
	public static int getJettyMinThreads() {
		return yamlConfig.jettyMinThreads;
	}
	
	public static int getJettyMaxQueued() {
		return yamlConfig.jettyMaxQueued;
	}
	
	
	
	public static boolean isNotifyServer() {
		return Boolean.parseBoolean(yamlConfig.isNotifyServer);
	}
	
	
	public static boolean isProductionEnv() {
		return "production".equals(yamlConfig.env);
	}
	
	public static String getPortalDomain() {
		return yamlConfig.portal_domain;
	}
	
	public static String getDataDir() {
		return yamlConfig.data_dir;
	}
	
	public static String getPhotoLocationLog(){
		return yamlConfig.photo_log_path;
	}

	public static String getResourceUrl() {
		return yamlConfig.resource_url;
	}

	public static String getPhotoDir(){
		return yamlConfig.photo_dir;
	}
	
}
