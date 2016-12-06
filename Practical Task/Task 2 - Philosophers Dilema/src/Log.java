import java.io.*;

/**
 * Created by henry on 2016-12-06.
 */
public class Log {
    private PrintWriter writer;
    public Log() throws IOException {
        //setup fileSystem..
        File log = new File("Log.txt");
        if(!log.exists()) log.createNewFile();
        writer = new PrintWriter(new FileWriter(log, false));
    }

    public synchronized void msg(String message){
        writer.println(message);
    }

    public void close() {writer.close();}
}
