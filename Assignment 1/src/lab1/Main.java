package lab1;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author henry
 *
 *
 * To be able to send different messages, check the Transport class
 */


public class Main {
	public static void main(String[] args){

		ExecutorService exec = Executors.newCachedThreadPool(); // pool of threads that have no set size
		System.out.println("start"); // just to tell user that the connections have started
			
		for(int i = 0; i < 1; i++){ //multiple connections
			exec.execute(new User(args, i)); // create a new user and execute its run method
		}
		
		exec.shutdown(); // don't allow more threads to be connected
	}
}

class User implements Runnable {
	
	private int index = 0; // the index of this thread
	private String[] args = null; // a bit unorthodox way.. but the args is not a heavy burdon in this case
	public User(String[] args, int index){
		this.index = index;
		this.args = args;
	}
	
	@Override
	public void run(){
		try {
			Transport transport = new TCPEchoClient(args, index); // create TCP or UDP connection
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
