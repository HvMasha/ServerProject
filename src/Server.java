import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int maxSessionCount;
    private static int sessionCount = 0;
    private static final Object lock = new Object();

    /*public static void closeSession() {
        synchronized (lock) {
            sessionCount--;
            lock.notifyAll();
        }
    }*/


    /*
     * Изменения:
     * 1)Server
     * a. Create Channel<Runnable>
     * b. Run Host in new Thread (pass to host created channel)
     * c. Create ThreadPool
     * d. Create Dispatcher, start it in new thread (pass to dispatcher channel and threadPool)
     * 2)Host:
     * a. Accept connection
     * b. Put new Session with this connection to channel
     * 3)Dispatcher
     * a. Take session from channel
     * b. Pass it for execution to threadPool
     *
     */
    public static void main(String[] args) throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));//Создаем сервер на заданном в арг хосте
            try {
                maxSessionCount = Integer.parseInt(args[1]);
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal argument of session count");
            }
            //Create Channel<Runnable>
            Channel channel = new Channel(maxSessionCount);
           // Dispatcher dispatcher = new Dispatcher(channel);//Создаем Диспетчера ?????????А как тут вставить threadPool?

            //Run Host in new Thread (pass to host created channel)
            Host host = new Host(maxSessionCount, channel);
            Thread hostThread = new Thread();
            hostThread.start();

            //Create ThreadPool
            ThreadPool threadPool = new ThreadPool(maxSessionCount);//Или мы сюда другое значение должны ставить?


            //Create Dispatcher, start it in new thread (pass to dispatcher channel and threadPool)
            Dispatcher dispatcher1 = new Dispatcher(channel,threadPool);
            Thread threadDispatcher = new Thread(dispatcher1);
            threadDispatcher.start();

            /*
            Thread thread = new Thread(dispatcher);
            thread.start();//Запускаем диспетчер
            while (true) {
                Socket socket = serverSocket.accept();//Ожидаем, когда к нам подключится Client и создаем Socket для него
                synchronized (lock) {
                    while (sessionCount == maxSessionCount) {
                        //dataOutputStream.writeUTF("Sorry, server is too busy. Please, wait.");
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
*/
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
