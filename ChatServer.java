import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatServer
{
  public static void main(String [] args) throws Exception
  {
    ServerSocket listener = new ServerSocket(6666);
    User u;
    ChatRooms C = new ChatRooms();
    System.out.println("Listening on the port 6666....");
    try
    {
      while(true)
      {
        Socket s = listener.accept();

        u = new User(s, C);

        u.start();
      }
    }finally
    {
      listener.close();
    }
  }
}
