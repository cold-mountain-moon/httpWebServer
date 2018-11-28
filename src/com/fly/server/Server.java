package com.fly.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.fly.server.constant.Constant;
import com.fly.util.IOUtil;

public class Server {

	private boolean on = false;

	private ServerSocket serverSocket = null;

	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}

	/**
	 * 启动方法
	 */
	public void start() {
		start(Constant.PORT);
	}

	/**
	 * 指定端口的启动方法
	 */
	public void start(int port) {
		try {
			on = true;
			this.serverSocket = new ServerSocket(port);
			receive();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 接收处理请求
	 * 
	 */
	public void receive() {

		InputStream in = null;
		OutputStream out = null;

		while (on) {
			try {
				Socket socket = serverSocket.accept();
				new Thread(new DispatcherThread(socket)).start();
			} catch (IOException e) {
				e.printStackTrace();
				shutdown();
			}
		}
	}

	/**
	 * 关闭方法
	 */
	public void shutdown() {
		on = false;
		IOUtil.close(serverSocket);
	}
}
