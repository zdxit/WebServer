package com.zdx.sever;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.zdx.domain.Request;
import com.zdx.domain.Response;
import com.zdx.util.Constant;
import com.zdx.util.RequestUtil;

public class HandlerRunnable implements Runnable {

	private Request request;
	private Response response;

	public HandlerRunnable(Request request, Response response) {
		this.request = request;
		this.response = response;
	}

	@Override
	public void run() {
		try {
			requestHandler();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void requestHandler() throws IOException {

		String requestType = request.getRequestType();
		if (requestType.equals("GET")) {
			String fileName = request.getRequestUrl();
			String filePath = Constant.WEB_ROOT + fileName;
			System.out.println(filePath);

			// GET the OutputSteam object
			OutputStream outputStream = response.getOutputStream();
			// 进行文件的读取
			File file = new File(filePath);
			if (file.exists()) {
				FileInputStream fileInputStream = new FileInputStream(file);
				byte[] bufferByte = new byte[1024];
				int read = fileInputStream.read(bufferByte);
				while (read != -1) {
					outputStream.write(bufferByte, 0, read);
					read = fileInputStream.read(bufferByte, 0, 1024);
				}
			} else {
				outputStream.write("404!!  can not find the file".getBytes());
			}

			outputStream.flush();
			outputStream.close();
			System.out.println("over!!");
		}

	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

}
