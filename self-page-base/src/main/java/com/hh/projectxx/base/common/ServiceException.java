package com.hh.projectxx.base.common;


/**
 * 异常类
 * 
 * @author Frank
 *
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = -7184995207444580132L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public ServiceException(Throwable throwable) {
		super(throwable);
	}

	public ServiceException(String message) {
		super(message);
	}
}
