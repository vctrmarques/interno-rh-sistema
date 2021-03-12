package com.rhlinkcon.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RHServiceException extends AppException {
	
	private static final long serialVersionUID = -4971546312599039881L;
	private static final Logger logger = LoggerFactory.getLogger(RHServiceException.class);

    public RHServiceException(String message) {
        super(message);
        logger.error(message);
    }

    public RHServiceException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message,cause);
    }

}
