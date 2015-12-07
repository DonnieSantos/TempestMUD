import java.io.*;
import java.lang.*;
import java.util.*;

public class MessageBoard extends Utility implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 12;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private ArrayList noteList;
  private String boardName;
  private int ID;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setID(int i)           { ID = i;           }
  public void setBoardName(String s) { boardName = s;    }
  public int getID()                 { return ID;        }
  public String getBoardName()       { return boardName; }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public MessageBoard()

  {
    ID = 0;
    boardName = "Generic Message Board";
    noteList = new ArrayList();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public MessageBoard(String bname, int id)

  {
    this();
    ID = id;
    boardName = bname;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addNote(Note newNote)

  {
    noteList.add(0, newNote);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void removeNote(Entity ENT, String input)

  {
    int numNote = 0;

    try

    {
      numNote = Integer.parseInt(input) - 1;
      if (numNote < 0) throw new Exception();
      if (numNote >= noteList.size()) throw new Exception();
    }

    catch (Exception e) { ENT.echo("You can't find that message."); return; }

    Note n = (Note)noteList.get(numNote);

    if (ENT.getLevel() < 100)
    if (!n.getAuthor().equals(ENT.getName())) {
      ENT.echo("You are not the author of that note.");
      return; }

    noteList.remove(numNote);
    ENT.echo("Note removed.");
    FileManager.saveBoard(this);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void displayNoteList(Entity ENT)

  {
    String temp = "The [#G" + boardName + "#N] message board: \r\n\n";

    if (noteList.size() == 0) temp += "No messages.";

    for (int i=0; i<noteList.size(); i++)

    {
      String number = "[#r" + (i+1) + "#N]";
      String author = "[#C" + ((Note)noteList.get(i)).getAuthor() + "#N]";
      String title  = ((Note)noteList.get(i)).getTitle() + "#N";

      temp += String.format("%1$-10s%2$-19s" + title + "\r\n", number, author);
    }

    ENT.getClient().pushPageBreak(temp);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void readNote(Entity ENT, String input)

  {
    int numNote = 0;

    try

    {
      numNote = Integer.parseInt(input) - 1;
      if (numNote < 0) throw new Exception();
      if (numNote >= noteList.size()) throw new Exception();
    }

    catch (Exception e) { ENT.echo("You can't find that message."); return; }

    Note n = (Note)noteList.get(numNote);

    ENT.echo(n.getNoteMessage());
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void writeNote(Entity ENT, String input)

  {
    if (input.length() == 0) {
      ENT.echo("You must provide a note title.");
      return; }

    if (visibleSize(input) > NOTE_TITLE_LENGTH) {
      ENT.echo("Title too long.");
      return; }

    ArrayList ilist = ENT.getRoom().getItemList().getList();
    Item board = null;

    for (int i=0; i<ilist.size(); i++)

    {
      Item item = (Item)ilist.get(i);
      if (item.getBoard() != null) board = item;
    }

    if (board == null) {
      ENT.echo("You don't see any message boards here.");
      return; }

    int previousState = ENT.getClient().getClientState();
    ENT.blindEmote("starts writing a message.");

    Note newNote = new Note(ENT, input);
    Composer newComposer = new Composer(ENT, newNote.getBody());
    newComposer.setBoard(board.getBoard());
    newComposer.setNote(newNote);

    if (previousState == CSTATE_IMM_AT)
      newComposer.setReturnClientState(CSTATE_NORMAL);

    newComposer.displayInfo();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void readBoard(Entity ENT)

  {
    ArrayList ilist = ENT.getRoom().getItemList().getList();
    int size = ilist.size();

    for (int i=0; i<size; i++)

    {
      Item item = (Item)ilist.get(i);
      if (item.getBoard() != null) {
        item.getBoard().displayNoteList(ENT);
        return; }
    }

    ENT.echo("You don't see any message boards here.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("ID");   out.writeObject(new Integer(ID));
      out.writeObject("NAME"); out.writeObject(boardName);
      out.writeObject("LIST"); out.writeObject(noteList);
      out.writeObject("BOARD END");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
    try

    {
      String tag = "";

      while (!tag.equals("BOARD END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("NAME"))         boardName = (String)in.readObject();
        else if (tag.equals("ID"))      ID = ((Integer)in.readObject()).intValue();
        else if (tag.equals("LIST"))    noteList  = (ArrayList)in.readObject();
        else if (!tag.equals("BOARD END")) in.readObject();
      }
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}