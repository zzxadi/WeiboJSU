package com.jsu.model;


public class WeiBoList {
	private int ret;
	private String msg;
	private int errcode;
	private WeiBoListData data;

	public WeiBoList(int ret, String msg, int errcode, WeiBoListData data) {
		super();
		this.ret = ret;
		this.msg = msg;
		this.errcode = errcode;
		this.data = data;
	}

	public WeiBoList() {
		super();
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public WeiBoListData getData() {
		return data;
	}

	public void setData(WeiBoListData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "WeiBoList [data=" + data + ", errcode=" + errcode + ", msg="
				+ msg + ", ret=" + ret + "]";
	}
	
}
