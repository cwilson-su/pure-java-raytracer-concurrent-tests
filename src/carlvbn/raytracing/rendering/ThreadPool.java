package carlvbn.raytracing.rendering;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPool {
	private final ArrayBlockingQueue<Runnable> taskQueue;
	private final List<Thread> workers;
	
	public ThreadPool(int poolSize) {
		taskQueue = new ArrayBlockingQueue<>(1000);
		workers = new ArrayList<>();
		
		for (int i=0; i<poolSize; i++) {
			Thread worker = new Thread(() -> {
				while (true) {
					try {
						Runnable task = taskQueue.take();
						task.run();
					} catch(InterruptedException e) {
						break;
					}
				}
			});
			workers.add(worker);
			worker.start();
		}
	}
	
	public void submit(Runnable task) throws InterruptedException{
		taskQueue.put(task);
	}


}
