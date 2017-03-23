import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args)  {
        try {
            Socket socket = new Socket(args[0], Integer.parseInt(args[1]));

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            /*dataOutputStream.writeUTF("Socket '"+socket.getLocalPort()+"' is connected");
            String str = dataInputStream.readUTF();
            System.out.println("You're connected.");
            */
            String str;
            while (true) {
                str = bufferedReader.readLine();
                dataOutputStream.writeUTF(str);
                if (str.equals("end")) {
                    System.out.println("You are disconnected.");
                    break;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
