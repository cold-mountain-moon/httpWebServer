package com.fly.server.servlet;

import java.io.IOException;

import com.fly.server.http.Request;
import com.fly.server.http.Response;

public class LoginServlet extends Servlet {

	@Override
	protected void doGet(Request req, Response res) throws IOException {
		res.appendln("你好啊");
		res.write(200);

	}

	@Override
	protected void doPost(Request req, Response res) throws IOException {
		res.appendln("你好啊");
		res.write(200);

	}

}
