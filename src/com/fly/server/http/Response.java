package com.fly.server.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

import com.fly.server.constant.Constant;

public class Response {

	private Socket socket;

	private StringBuilder header;
	private StringBuilder content;
	private int length;

	public Response() {
		this.header = new StringBuilder();
		this.content = new StringBuilder();
		this.length = 0;
	}

	public Response(Socket socket) {
		this();
		this.socket = socket;
	}

	private void createResponseHeader(int code) {
		// 响应头
		header.append("HTTP/1.1").append(Constant.BLANK).append(code).append(Constant.BLANK);
		switch (code) {
		case 200:
			header.append("OK");
			break;
		case 404:
			header.append("NOT FOUND");
			break;
		case 500:
			header.append("SERVER EXCEPTION");
			break;
		default:
			header.append("OK");
			break;
		}
		header.append(Constant.SEPR);
		header.append("Server:").append(Constant.BLANK).append("Fly http server/0.0.1");
		header.append(Constant.SEPR);
		header.append("Date:").append(Constant.BLANK).append(new Date().toString());
		header.append(Constant.SEPR);
		header.append("Content-Type:").append(Constant.BLANK).append("text/html;charset=utf-8");
		header.append(Constant.SEPR);
		header.append("Content-Length:").append(Constant.BLANK).append(length);
		header.append(Constant.SEPR).append(Constant.SEPR).append(content.toString());
	}

	public Response append(String msg) {
		if (msg != null) {
			content.append(msg);
			length += msg.getBytes().length;
		}
		return this;
	}

	public Response appendln(String msg) {
		if (msg != null) {
			content.append(msg).append(Constant.SEPR);
			length += msg.getBytes().length;
		}
		return this;
	}

	/**
	 * 向客户端发送信息
	 * 
	 * @param code
	 *            http响应状态码
	 * @throws IOException
	 */
	public void write(int code) throws IOException {
		OutputStream out = socket.getOutputStream();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
		createResponseHeader(code);
		bw.append(header);
		bw.append(content);

		bw.flush();
	}

}
