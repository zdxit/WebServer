package com.zdx.domain;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class Response {

	private OutputStream outputStream;
	private BufferedWriter writer;
	private Map<String, Object> paramter;

	public Response(OutputStream outputStream) {
		this.outputStream=outputStream;
		this.writer=new BufferedWriter(new OutputStreamWriter(outputStream));
		this.paramter = new HashMap<String, Object>();

	}

	public void output(byte[] datas) throws IOException {
		outputStream.write(datas);
	}

	
	
	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public Map<String, Object> getParamter() {
		return paramter;
	}

	public void setParamter(Map<String, Object> paramter) {
		this.paramter = paramter;
	}

	public Object addAttribute(String key, Object value) {
		return this.paramter.put(key, value);
	}

	public Object setAttribute(String key, Object value) {
		return this.paramter.put(key, value);
	}

	public Object removeAttribute(String key) {
		return this.paramter.remove(key);
	}
	public void closeWriter() throws IOException{
		this.writer.close();
	}
	public void closeOutPutStream() throws IOException{
		this.outputStream.close();
	}

}
