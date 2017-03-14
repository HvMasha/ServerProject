import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args)  {
        try {
            Socket socket = new Socket(args[0], Integer.parseInt(args[1]));

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            dataOutputStream.writeUTF("Socket '"+socket.getLocalPort()+"' is connected");

            String str = dataInputStream.readUTF();
            if(str.equals("")) {
                System.out.println("You're connected.");
                while (true) {
                    str = bufferedReader.readLine();
                    dataOutputStream.writeUTF(str);
                    if (str.equals("end")) {
                        System.out.println("You are disconnected.");
                        break;
                    }
                }
            }
            else{
                System.out.println(str);
                socket.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
