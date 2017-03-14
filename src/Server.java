import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static int maxSessionCount;
    static int sessionCount = 0;
    static final Object lock = new Object();
    public static void closeSession() {
        synchronized (lock){
            sessionCount--;
        }
        lock.notifyAll();
    }
    public static void openSession() {
        synchronized (lock){
            sessionCount++;
            if (sessionCount == maxSessionCount) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {
        try{
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));//принимает соединение от клиента
            try {
                maxSessionCount = Integer.parseInt(args[1]);
            }
            catch(IllegalArgumentException e){
                System.out.println("Illegal argument of session count");
            }
            while(true) {
                Socket socket = serverSocket.accept();
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                if (sessionCount >= maxSessionCount) {
                    dataOutputStream.writeUTF("Sorry, server is too busy. Please, wait.");

                } else {
                    openSession();
                    Thread thread = new Thread(new Session(socket));
                    dataOutputStream.writeUTF("");
                    thread.start();

                }
            }
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Введены некорректные значения");
        }
        catch (Exception ex)
        {

        }


    }
}
