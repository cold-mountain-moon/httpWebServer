package com.fly.server;

import java.io.IOException;
import java.net.Socket;

import com.fly.server.http.Request;
import com.fly.server.http.Response;
import com.fly.server.servlet.Servlet;
import com.fly.server.servlet.context.Webapp;
import com.fly.util.IOUtil;

public class DispatcherThread implements Runnable {

	private Request req;
	private Response res;
	private Socket socket;

	private int code = 200;

	public DispatcherThread(Socket socket) {
		this.socket = socket;
		req = new Request(this.socket);
		this.res = new Response(this.socket);
	}

	@Override
	public void run() {
		Servlet servlet;
		try {
			servlet = Webapp.getServlet(req.getUrl());
			if (servlet == null) {
				code = 404;
			} else {
				servlet.service(req, res);
			}
			res.write(code);
		} catch (InstantiationException e2) {
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			e2.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e) {
			code = 500;
			try {
				res.write(code);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			IOUtil.close(socket);
		}

	}

}
