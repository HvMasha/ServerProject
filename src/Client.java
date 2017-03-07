import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(args[0],Integer.parseInt(args[1]));
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String str = "";
        while(true)
        {
            str = bufferedReader.readLine();
            dataOutputStream.writeUTF(str);;
            if(str.equals("end"))
                break;
        }
    }
}
