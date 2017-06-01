package com.sdhsie.base.util;

public class Json implements java.io.Serializable{
	private static final long serialVersionUID = -4540167775871168514L;
	
	private boolean success = false;
	private String msg = "";
	private Object myobject;
	private String code = "";
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	private String sessionId = "";
	private boolean verifySessionId = false;
	private String flag="";
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getMyobject() {
		return myobject;
	}
	public void setMyobject(Object myobject) {
		this.myobject = myobject;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public boolean isVerifySessionId() {
		return verifySessionId;
	}
	public void setVerifySessionId(boolean verifySessionId) {
		this.verifySessionId = verifySessionId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
}
