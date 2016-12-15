package demo8;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class HighLevelSynchTechnique {
	private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10); //thread safe queue, no need for synchronization 
	
	
	private static void produce() throws InterruptedException {
		Random random = new Random();
		
		while(true) {
			queue.put(random.nextInt(100)); //if size is 10, put() waits until consume has taken one element out
		}
	}
	
	private static void consume() throws InterruptedException {
		Random random = new Random();

		while(true) {
			Thread.sleep(100);

			if(random.nextInt(10) == 0) {
				System.out.print("Queue size is: "+queue.size());
				Integer value = queue.take(); // if size == 0, take() waits until produce has put one element in
				System.out.println("; Taken value: "+ value + "; Queue size now is: " + queue.size());
			}
			
		}
	}
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable(){

			public void run() {
				try {
					produce();
				} catch (InterruptedException e) {
				}
			}			
		});
		
		Thread t2 = new Thread(new Runnable(){

			public void run() {
				try {
					consume();
				} catch (InterruptedException e) {
				}
			}			
		});
		t1.start();
		t2.start();
		

	}
}
