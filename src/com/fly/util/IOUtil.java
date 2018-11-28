package com.fly.util;

import java.io.Closeable;
import java.io.IOException;

public class IOUtil {

	public static void close(Closeable t) {
		if (t != null) {
			try {
				t.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
