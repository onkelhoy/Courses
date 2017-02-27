package lab1;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class TCPEchoClient extends Transport {
	private InputStream in;
    private OutputStream out;
	private int index = 0;
    
	public TCPEchoClient(String args[], int index) throws IOException, InterruptedException {
		super(args); // start with load
		this.index = index;
		
		Socket socket = new Socket(super.getIP(), super.getPort());
		super.log.append(String.format("[%d] : connected\n", index)); 
		
		in = socket.getInputStream();
		out = socket.getOutputStream();
		
		int rate = super.getRate(), sendTime = 1000/rate;
		
		// main loop
		while(rate > 0){ // as long as there's messages to send
			if(socket.isClosed()) { // is the connection lost?
				super.log.append(String.format("connection lost [%d]", index));
				break; // abort 
			}
			Thread.sleep(sendTime); //no measurement on time..

			out.write(super.getMSG().getBytes()); // sends the message
			out.flush(); // then flush the stream
			
			int read = 0; // the total read-length of bytes
			byte[] buffer = new byte[super.getBufSize()];
			StringBuilder receive = new StringBuilder(); // the string that will be read
			
			
			while(true){
				int r = in.read(buffer); // the length of read bytes
				read += r; // add to total read bytes
				
				receive.append( new String(buffer, 0, r) ); // append string that is a part of the buffer
				
				if(read >= super.getMSG().length()) break; // all the bytes are loaded, no point of continue
			}
			
			super.Verify(receive.toString()); // Verifies the received string with the original string
			rate--; //one message sent and received
		}

		super.log.append(String.format("[%d] : closed\n\n", index)); // appends a client ended (to be able to read it)
		in.close();
		out.close();
		socket.close(); //closed when finished
		System.out.print(super.log.toString()); //print the log
	}
	
}