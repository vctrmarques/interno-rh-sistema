package com.rhlinkcon.payload.generico;


public class ApiResponse {
    private Boolean success;
    private String message;
    private Object obj;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    public ApiResponse(Boolean success, String message, Object obj) {
        this.success = success;
        this.message = message;
        this.obj = obj;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
