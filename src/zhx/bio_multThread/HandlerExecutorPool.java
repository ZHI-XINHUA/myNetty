package zhx.bio_multThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
* @Description: 服务器线程池
 */
public class HandlerExecutorPool {
	private ExecutorService executor;
	
	public HandlerExecutorPool(int maxPoolSize,int queueSize){
		//自定义线程池
		this.executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 
				maxPoolSize, 
				120L,
				TimeUnit.SECONDS, 
				new ArrayBlockingQueue<Runnable>(queueSize));
		
	}
	
	/**
	* 执行线程方法
	 */
	public void execute(Runnable runnable){
		this.executor.execute(runnable);
	}
	

}
