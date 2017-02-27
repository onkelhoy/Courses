package lab1;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class TCPEchoServer {
    public static final int MYPORT= 4950;

    public static void main(String[] args) throws IOException {
	
		ServerSocket serverSocket = new ServerSocket(MYPORT);
		ExecutorService exec = Executors.newCachedThreadPool(); // a pool of threads that are ready to deploy without a fixed size of threads
		System.out.println("server is running"); // indicate user that server is running
		int client_index = 0; // start index
	
		while(true){
			//create new thread
			Socket client_socket = serverSocket.accept(); //a new socket connected
			try {
				exec.execute(new Client(client_socket, client_index)); //creates client then executes the run method
				client_index++;
			}
			catch (Exception e){ // oh no..
				e.printStackTrace();
			}
		}
    }
}

class Client implements Runnable {
	private int BUFSIZE = 1024;
	
	private Socket socket;
	private InputStream in;
    private OutputStream out;
    
    private int index; 			// for nice printouts
    private StringBuilder log;	// prevents synch issues when writing to console
	
	public Client(Socket socket, int index){
		this.socket = socket;
		this.index = index;
		log = new StringBuilder();
		try {
		    out = socket.getOutputStream();
		    in = socket.getInputStream();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

    @Override
	public void run() { // this is executed
		log.append(String.format("new connection [%d]\n", index));
		
		while(true){ 
			try{ // as long as a connection
				StringBuilder receive = new StringBuilder(); // in order to prevent synch issues
				byte[] bytes = new byte[BUFSIZE]; // array with a large size [1024]
				int count = in.read(bytes); //read in the data to the array (spits out the bytes length that has been read)
				if(count != -1) { // important, prevent error if data is empty
					
					String t = new String(bytes, 0, count); // creates a new string from the array (a part)
					
		    	    out.write( t.getBytes() ); // write the trimmed string back
		    	    out.flush(); // then flush the stream
					
		    	    log.append(String.format("# TCP echo request from %d, %dbytes, %s\n", index, count, t));
				}
				else break; // break out of loop
			}
			catch( Exception e ){
				e.printStackTrace();
				log.append(String.format("connection lost [%d]\n", index));
				
				// e.printStackTrace();
				// out.close();
				break;
			}
		}
		
		try { // closing the socket and streams
			log.append(String.format("closing the connection [%d]\n", index));
			socket.close();
			in.close();
			out.close();
		}
		catch (IOException e){
			log.append(String.format("couldnt not terminate socket[%d] connection", index));
		}
		System.out.println(log.toString()); // print out all the info (again.. to prevent synch issues)
		Thread.currentThread().interrupt(); // terminates the client
	}
}
