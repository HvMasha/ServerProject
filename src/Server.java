import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        try{
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
            //принимает соединение от клиента

            while(true){
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new Session(socket));
                thread.start();
            }
        }
        catch (Exception ex)
        {

        }


    }
}
