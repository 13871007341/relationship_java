package com.shuwen.kinship_calculator.utils;

import java.util.HashMap;

/**
 * 返回数据
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("code", Constant.SUCC_CODE);
		put("msg",Constant.RES_SUCCESS);
		put("result", null);
	}
	
	public static R error() {
		return error(Constant.ERROR_CODE, Constant.ERROR_MSG);
	}
	
	public static R error(String msg) {
		return error(Constant.ERROR_CODE, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	public static R ok(Object obj) {
		R r = new R();
		r.put("result", obj);
		return r;
	}
	
	/*public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}*/
	
	public static R ok() {
		return new R();
	}

	/**
	 * @description: 支付通知接收
	 * @author tianpeng
	 * @date 2019/7/10
	 **/
	public static R ok(String state, String msg) {
		R r = new R();
		r.put("state", state);
		r.put("msg", msg);
		return r;
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
