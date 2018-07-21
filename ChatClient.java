
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.io.Console;

class ChatClient
{
  public static void main(String [] args) throws IOException
  {
    Socket s = new Socket("localhost", 6666);
    Scanner sc = new Scanner(System.in);
    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
    PrintWriter output = new PrintWriter(s.getOutputStream(), true);

    String cmd = input.readLine();
    String [] cmdlist = cmd.split(" ");

    System.out.println("Enter your username");
    String username = sc.next();

    System.out.println("This is the list of current running rooms.");
    System.out.println(cmdlist.length);
    if(cmdlist.length == 0)
    {
      System.out.println("No rooms available");
    }
    for(int i = 1; i < cmdlist.length; i++)
    {
      System.out.println(cmdlist[i]);
    }

    System.out.println("1.Enter the roomId and password to join");
    System.out.println("2.Create a new room");
    int choice = sc.nextInt();
    System.out.println("Enter the room Id");
    String rid = sc.next();
    System.out.println("Enter the password");
    String pass = sc.next();

    switch(choice)
    {
      case 1 : output.print("JOIN ");
               output.print(username);
               output.print(" ");
               output.print(rid);
               output.print(" ");
               output.println(pass);
               break;

      case 2 : output.print("CREATE ");
               output.print(username);
               output.print(" ");
               output.print(rid);
               output.print(" ");
               output.println(pass);
               break;
      default : System.out.println("BAKA!");
    }

    while(true)
    {
      cmd = input.readLine();

      if(cmd.startsWith("AUTH_ERROR"))
      {
        System.out.println("Authentication Failed");
        break;
      }
      else if(cmd.startsWith("ROOMJOINED"))
      {
        System.out.println("Room joined Succesfully");
      }
      else if(cmd.startsWith("CREATED"))
      {
        System.out.println("Room Created Succesfully");
      }
      else if(cmd.startsWith("MSG"))
      {
        System.out.println(cmd.substring(4));

      }

      else
      {
        Console console = System.console();
        System.out.print("Enter message:_");
        cmd = console.readLine();
        if(cmd.startsWith("quit"))
        {
          output.println("QUIT");
          break;
        }
        output.print("MSG ");
        output.print(username);
        output.print(": ");
        output.println(cmd);
      }
    }
  }
}
