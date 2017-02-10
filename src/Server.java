import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 14hvostova on 10.02.2017.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        try{
            ServerSocket serverSocket = new ServerSocket( 1500);
            Socket socket = serverSocket.accept();//принимает соединение от клиента
            InputStream inputStream = socket.getInputStream();//получили стрим с байтами
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String message = dataInputStream.readUTF();
            System.out.println(message);

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        }
        catch (Exception ex)
        {

        }


    }
}
