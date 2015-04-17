package com.zdx.sever;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.zdx.domain.Request;
import com.zdx.domain.Response;

/**
 * 这里使用到了线程池来处理客户端发的多次请求，以节约系统资源，减少线程的创建从而提高系统性能
 * @author zdx
 *
 */
public class MainServer {

	private final int port = 8091;
	// 线程池维护的最小线程数量
	private static int corePoolSize = 3;
	// 线程池维护的最大线程数
	private static int maximumPoolSize = 10;
	// 线程池维护线程所允许的空闲时间
	private static int keepAliveTime=10;
	// 线程池维护线程所允许的空闲时间的单位
	private static TimeUnit timeUnit = TimeUnit.SECONDS;
	// 线程池所使用的缓冲队列
	private BlockingQueue<Runnable> blockingQueue;
	// 线程池
	private ExecutorService executor;

	public MainServer() {

		blockingQueue = new ArrayBlockingQueue<>(5);
		this.executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
				keepAliveTime, timeUnit, blockingQueue);
	}

	public  void dealRequest() {

		try {
			ServerSocket serverSocket = new ServerSocket(port);
			Socket socket = null;
			InetAddress inetAddress;
			InputStream inputStream = null;
			OutputStream outputStream = null;
			while (true) {
				socket = serverSocket.accept();
				//创建Request对象
				inputStream = socket.getInputStream();
				inetAddress = socket.getInetAddress();
				Request request=new Request(inputStream, inetAddress);
				
				//创建Response;
				outputStream = socket.getOutputStream();
				Response response=new Response(outputStream);
				
				//创建处理线程
				HandlerRunnable handlerRunnable=new HandlerRunnable(request, response);
				//将线程提交到线程池中进行处理
				this.executor.submit(handlerRunnable);
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void main(String[] args) {
		MainServer mainServer=new MainServer();
		mainServer.dealRequest();
	}

}

