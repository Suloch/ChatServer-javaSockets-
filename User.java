import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
public class User extends Thread
{
  String UserId;
  Socket connection;
  BufferedReader input;
  PrintWriter output;
  ChatRooms C;
  ChatRoom room;

  public User(Socket s, ChatRooms C)
  {
    this.connection = s;
    this.C = C;
    try
    {
      input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      output = new PrintWriter(connection.getOutputStream(), true);
    }catch(IOException e){System.out.println(e);}
  }

  public void run()
  {
    try
    {
      while(true)
      {
        String roomlist = C.printRooms();
        try
        {
          TimeUnit.SECONDS.sleep(2);
        }catch(InterruptedException e)
        {
          System.out.println(e);
        }
        output.print("ROOMS ");
        output.print(roomlist);
        output.println(".");

        String cmd = input.readLine();
        String []cmdlist = cmd.split(" ");
        UserId = cmdlist[1];
        System.out.println(cmd);
        //int choice = Integer.parseInt(cmdlist[0]);
        
        switch(cmdlist[0])
        {
          case "JOIN"   : room = C.joinRoom(cmdlist[2], UserId, cmdlist[3]);
                     if( room == null)
                     {
                       output.println("AUTH_ERROR");
                     }
                     else
                     {
                       output.println("ROOMJOINED");
                     }
                     break;

          case "CREATE" : room = C.createRoom(cmdlist[2], UserId, cmdlist[3]);
                          output.println("CREATED");
                     break;
        }

        if(cmdlist[0] == "QUIT")
        {
          break;
        }

        int msgno = room.getMessageNumber();
        int N = msgno % 10;

        while(true)
        {


          while(msgno < room.getMessageNumber())
          {
            output.print("MSG ");
            output.println(room.readMessage(N));
            N = (N + 1) % 10;
            msgno = msgno + 1;

          }


          output.println("DONE");
          cmd = input.readLine();
          System.out.println(cmd);
          if(cmd.startsWith("MSG"))
          {
            room.writeMessage(cmd.substring(4));
          }

          else if(cmd.startsWith("QUIT"))
          {
            break;
          }
        }
      }
    }catch(IOException e){System.out.println(e);}
  }
}
