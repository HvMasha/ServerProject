import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Host implements Runnable{//создает Session-ы???
    private ServerSocket serverSocket;
    private Channel channel;
    private Thread thread;
    private Object lock = new Object();
    int sessionCount;
    int maxSessionCount;

    public Host(int maxSessionCount, Channel channel){
        this.maxSessionCount = maxSessionCount;
        this.channel = channel;
    }

    public void run(){
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                while (sessionCount == maxSessionCount) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                channel.put(new Session(socket));//Кладем в Channel Session Socket-a
                sessionCount++;
            }

        }
    }

    public void close(){

    }
}
