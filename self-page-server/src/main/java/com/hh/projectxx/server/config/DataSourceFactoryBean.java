package com.hh.projectxx.server.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

public class DataSourceFactoryBean implements FactoryBean<BasicDataSource>, DisposableBean {

	private static final int DBCP_INITIAL_SIZE = 5;
	private static final int DBCP_MAX_ACTIVE = 30;
	private static final int DBCP_MIN_IDLE = 5;
	private static final int DBCP_MAX_IDLE = 10;
	private static final int DBCP_MAX_WAIT = 20000;
	private static final int DBCP_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 300000;
	private static final int DBCP_MIN_EVICTABLE_IDLE_TIME_MILLIS = 320000;
	private static final int DBCP_REMOVE_ABANDONED_TIMEOUT = 150;

	private static Logger log = LoggerFactory.getLogger(DataSourceFactoryBean.class);

	private BasicDataSource dataSource;

	@Override
	public BasicDataSource getObject() throws Exception {
		if (dataSource != null) {
			log.info("already inited, ");
			return dataSource;
		}
		final String driver = "com.mysql.jdbc.Driver";

		final String url = DuopaiConfig.getJdbcUrl();
		final String username = DuopaiConfig.getJdbcUser();
		final String password = DuopaiConfig.getJdbcPassword();
		
		dataSource = new BasicDataSource();
		
		log.info("jdbc init by: " + this.toString() + ", " + dataSource.toString() + ", url : {}, username : {}", new String[] { url, username });
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		dataSource.setInitialSize(DBCP_INITIAL_SIZE);
		dataSource.setMaxActive(DBCP_MAX_ACTIVE);
		dataSource.setMinIdle(DBCP_MIN_IDLE);
		dataSource.setMaxIdle(DBCP_MAX_IDLE);
		dataSource.setMaxWait(DBCP_MAX_WAIT);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setValidationQuery("select 1");
		dataSource.setTestOnBorrow(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis(DBCP_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
		dataSource.setMinEvictableIdleTimeMillis(DBCP_MIN_EVICTABLE_IDLE_TIME_MILLIS);
		dataSource.setRemoveAbandoned(true);
		dataSource.setRemoveAbandonedTimeout(DBCP_REMOVE_ABANDONED_TIMEOUT);
		dataSource.setLogAbandoned(true);

		return dataSource;
	}

	@Override
	public Class<BasicDataSource> getObjectType() {
		return BasicDataSource.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void destroy() throws Exception {
		if (dataSource != null) {
			dataSource.close();
		}
	}

}
