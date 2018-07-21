
import java.util.*;

public class ChatRoom
{
  String roomId;
  String rPass;
  int nUsers;
  String [] Messages;
  int [] read;
  int empty;
  int N;
  int nMessages;
  List<String> userList = new ArrayList<String>();

  public ChatRoom(String rid, String id, String pass)
  {
    this.nMessages = 0;
    this.nUsers = 1;
    this.roomId = rid;
    this.rPass = pass;
    this.N = 0;
    this.empty = 10;
    this.Messages = new String[10];
    this.read = new int[10];
    userList.add(id);
  }

  public int getMessageNumber()
  {
    return nMessages;
  }
  public String getPass()
  {
    return rPass;
  }
  public void addUser(String uid)
  {
    userList.add(uid);
    nUsers = nUsers + 1;
  }

  public void deleteUser(String uid)
  {
    for(String s:userList)
    {
      if(s == uid)
      {
        userList.remove(s);
        nUsers = nUsers - 1;
      }
    }
  }

  synchronized public void writeMessage(String Message)
  {
    while(empty == 0)
    {
      try
      {
        wait();
      }
      catch(InterruptedException e)
      {
        System.out.println(e);
      }
    }
    Messages[N] = Message;
    N = (N + 1) % 10;
    empty = empty - 1;
    nMessages = nMessages + 1;
  }

  synchronized public String readMessage(int msgno)
  {
    while(empty == 10)
    {
      try
      {
        wait();
      }
      catch(InterruptedException e)
      {
        System.out.println(e);
      }
    }
    read[msgno] = read[msgno] + 1;
    if(read[msgno] == nUsers)
    {
      read[msgno] = 0;
      empty = empty + 1;
    }
    
    return Messages[msgno];
  }
}
