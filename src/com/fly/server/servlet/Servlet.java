package com.fly.server.servlet;

import java.io.IOException;

import com.fly.server.http.Request;
import com.fly.server.http.Response;

public abstract class Servlet {

	public void service(Request req, Response res) {
		String method = req.getMethod();
		if ("GET".equalsIgnoreCase(method)) {
			try {
				doGet(req, res);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("POST".equalsIgnoreCase(method)) {
			try {
				doPost(req, res);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected abstract void doGet(Request req, Response res) throws IOException;

	protected abstract void doPost(Request req, Response res) throws IOException;

}
