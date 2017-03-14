import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session implements Runnable {
    Socket socket;

    public Session(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while (true) {
                String message = dataInputStream.readUTF();//считываем сообщение от клиента
                System.out.println(message);
                if(message.equals("end"))
                {
                    System.out.println("Socket is disconnected.");
                    dataOutputStream.writeUTF("You're disconnected.");
                    break;
                }
            }

        } catch (IOException e) {
            e.getMessage();
        }
        finally {
            Server.closeSession();
        }
    }
}
