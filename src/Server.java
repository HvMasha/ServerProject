import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int maxSessionCount;
    private static int sessionCount = 0;
    private static final Object lock = new Object();

    public static void closeSession() {
        synchronized (lock) {
            sessionCount--;
            lock.notifyAll();
        }
    }
    public static void main(String[] args) throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));//Создаем сервер на заданном в арг хосте
                try {
                maxSessionCount = Integer.parseInt(args[1]);
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal argument of session count");
            }
            Dispatcher dispatcher = new Dispatcher(new Chanel(maxSessionCount));//Создаем Диспетчера
            while(true){
                Socket socket = serverSocket.accept();//Ожидаем, когда к нам подключится Client и создаем Socket для него
                dispatcher.putSession(new Session(socket));//Кладем в Диспетчер Session Socket-a
            }
            //Когда запускать диспетчер?
        } catch (IllegalArgumentException e) {
            System.out.println("Введены некорректные значения");
        } catch (Exception ex) {

        }
    }
}

  /* public static void openSession() {
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
     }*/
  /*       while (true) {
                Socket socket = serverSocket.accept();//Ожидаем, когда к нам подключится кто-то, создаем для него сокет
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                synchronized (lock) {
                    while (sessionCount == maxSessionCount) {
                        //dataOutputStream.writeUTF("Sorry, server is too busy. Please, wait.");
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    sessionCount++;
                }
                Thread thread = new Thread(new Session(socket));
                dataOutputStream.writeUTF("");
                thread.start();
            }*/
