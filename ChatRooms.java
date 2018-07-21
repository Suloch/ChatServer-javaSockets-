
import java.util.*;

public class ChatRooms
{
  int nRooms;
  List<ChatRoom> rooms = new ArrayList<ChatRoom>();
  List<String> users = new ArrayList<String>();

  public void ChatRooms()
  {
    nRooms = 0;
  }

  public String printRooms()
  {
    if(nRooms == 0)
    {
      return " ";
    }
    String acc =  "";
    for(ChatRoom c:rooms)
    {
      acc = acc.concat(c.roomId);
      acc = acc.concat(" ");
    }
    return acc;
  }

  public boolean addUser(String s)
  {
    for(String user:users)
    {
      if(user.equals(s))
      {
        return false;
      }
    }
    return true;
  }
  public ChatRoom createRoom(String rid, String uid, String pass)
  {
    ChatRoom c = new ChatRoom(rid, uid, pass);
    rooms.add(c);
    nRooms = nRooms + 1;
    return c;
  }

  public ChatRoom joinRoom(String rid, String uid, String pass)
  {
    for(ChatRoom c:rooms)
    {
      if(c.roomId.equals(rid))
      {
        if(c.getPass().equals(pass))
        {
          c.addUser(uid);
          return c;
        }
        else
        {
          return null;
        }
      }
    }
    return null;
  }

  public void leaveRoom(String uid, String rid)
  {
    for(ChatRoom c:rooms)
    {
      if(c.roomId == rid)
      {
        c.deleteUser(uid);
      }
    }
  }
  public void deleteRoom()
  {
    for(ChatRoom c:rooms)
    {
      if(c.nUsers == 0)
      {
        rooms.remove(c);
      }
    }
  }
}
