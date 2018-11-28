package com.fly.server.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fly.server.constant.Constant;

/**
 * http request
 * 
 * @author fly
 *
 */
public class Request {

	private Socket socket;

	/**
	 * 请求访问地址
	 */
	private String url;

	/**
	 * 请求方法
	 */
	private String method;
	// private String httpVersion;

	private Map<String, String> paramMap;

	public Request() {
		paramMap = new HashMap<>();
	}

	public Request(Socket socket) {
		this();
		this.socket = socket;
		parseRequestInfo();
	}

	/**
	 * 根据参数名获取参数值
	 * 
	 * @param key
	 * @return
	 */
	public String getParameter(String key) {
		return paramMap.get(key);
	}

	public String[] getParameterValues(String key) {
		return null;
	}

	/**
	 * 获取request请求信息
	 * 
	 * @return 请求字符串
	 */
	private String getRequestInfo() {
		InputStream in;
		String info = null;
		try {
			in = socket.getInputStream();
			byte[] data = new byte[20480]; // 一次性读取，有待优化
			int len = in.read(data);
			if (len == -1) {
				return null;
			}
			info = new String(data, 0, len);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 解析http请求
	 */
	private void parseRequestInfo() {
		String info = getRequestInfo();
		if (info == null) {
			return;
		}
		String[] split = info.split(Constant.SEPR);
		String line1 = split[0];
		String[] splitFirstRow = line1.split(Constant.BLANK);
		String method = splitFirstRow[0];
		this.method = method;
		String line2 = split[1];
		String urlPos = splitFirstRow[1];
		String url = urlPos.split("\\?")[0];
		this.url = urlPos;
		// 解析参数
		if ("GET".equalsIgnoreCase(method)) {
			String[] splitUrl = urlPos.split("\\?");
			if (splitUrl.length == 2) {
				String kvsStr = splitUrl[1];
				putKV2Map(kvsStr);
			}
		} else if ("POST".equalsIgnoreCase(method)) {
			String[] splitPost = info.split(Constant.SEPR);
			if (splitPost.length == 2) {
				putKV2Map(splitPost[1]);
			}
		}
	}

	/**
	 * 将键值对存入该类的变量map
	 * 
	 * @param paramStr
	 *            形如：k1=v1&k2=v2
	 */
	private void putKV2Map(String paramStr) {
		String[] kvs = paramStr.split("&");
		String[] kvArr = null;
		for (String kvStr : kvs) {
			kvArr = kvStr.split("=");
			if (kvArr.length == 1) {
				kvArr = Arrays.copyOf(kvArr, 2);
			}
			String key = kvArr[0];
			String value = kvArr[1];
			if (!paramMap.containsKey(kvArr[0]) && value != null) {
				paramMap.put(key, value);
			}
		}
	}

	public String getMethod() {
		return this.method;
	}

	public String getUrl() {
		return this.url;
	}

}
