import java.util.*;
import java.lang.*;
import java.io.*;
import java.lang.reflect.Array;

public class Composer extends MudInterface

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private int returnPlayerState;
  private int returnClientState;
  private int maxLineWidth;
  private int editLineNum;
  private int insertLineNum;
  private int maxLines;
  private Entity author;
  private Writable currentWritable;
  private MessageBoard currentBoard;
  private Note currentNote;
  private ArrayList currentWriting;
  private boolean returnAnsi;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int viewMode()                     { return NO_OUTPUT;        }
  public void setReturnPlayerState(int i)   { returnPlayerState = i;   }
  public void setReturnClientState(int i)   { returnClientState = i;   }
  public void setAuthor(Entity e)           { author = e;              }
  public void setWritable(Writable w)       { currentWritable = w;     }
  public void setLineWidth(int i)           { maxLineWidth = i;        }
  public void setBoard(MessageBoard b)      { currentBoard = b;        }
  public void setNote(Note n)               { currentNote = n;         }
  public void setMaxLines(int i)            { maxLines = i;            }
  public void addFightOutput(String s)      { return;                  }
  public void addOutput(String s)           { return;                  }
  public void echo(String s)                { utilityQueue.add(s);     }
  public void focusGained()                 { return;                  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Composer(Entity ENT, Writable w)

  {
    super(ENT.getClient());
    returnPlayerState = ENT.getPlayerState();
    returnClientState = ENT.getClient().getClientState();

    author          = ENT;
    maxLines        = 500;
    editLineNum     = -1;
    insertLineNum   = -1;
    maxLineWidth    = WRITING_WIDTH;
    currentBoard    = null;
    currentWriting  = new ArrayList();
    currentWritable = w;

    myClient.pushInterface(this);
    myClient.setClientState(CSTATE_WRITING);

    if (myClient.getChar().getAnsiMode())

    {
      returnAnsi = true;
      myClient.getChar().setAnsiMode(false);
    }

    myClient.clearScreen(false);
    parseWritten(w.getString());
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private void flushUtilityQueue()

  {
    output = "";

    while (!utilityQueue.isEmpty())
      output += utilityQueue.removeFirst().toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void onDisconnect()

  {
    myClient.popInterface();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private void parseWritten(String s)

  {
    String[] lines = s.split("\r\n");
    int numLines = Array.getLength(lines);

    for (int i=0; i<numLines; i++)
    if (lines[i].length() > 0)
      currentWriting.add(lines[i]);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void displayInfo()

  {
    echo("\r\n" + showCommands());

    String temp = "\r\n     ] ";
    for (int i=0; i<maxLineWidth; i++) temp += "-"; temp += " [";

    echo(temp);
    if (currentWriting.size() > 0) displayMessage(false);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getPrompt()

  {
    Integer num;

    if (editLineNum != -1) num = new Integer(editLineNum);
    else if (insertLineNum != -1) num = new Integer(insertLineNum);
    else num = new Integer(currentWriting.size()+1);
    return String.format("#N%1$4d ] ", num);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void handleInput()

  {
    if (!myClient.commandWaiting()) return;

    String input = myClient.getCommand();

    if (visibleSize(input) > maxLineWidth) {
      echo("     #rLine exceeds max length.  Will not be added.");
      return; }

    if (input.equalsIgnoreCase("::exit"))            { exitComposer(false);      return; }
    if (input.equalsIgnoreCase("::save"))            { exitComposer(true);       return; }
    if (first(input).equalsIgnoreCase("::edit"))     { editLine(input);          return; }
    if (first(input).equalsIgnoreCase("::remove"))   { removeLineWriting(input); return; }
    if (first(input).equalsIgnoreCase("::insert"))   { insertLine(input);        return; }
    if (first(input).equalsIgnoreCase("::display"))  { displayMessage(true);     return; }
    if (first(input).equalsIgnoreCase("::show"))     { displayMessage(true);     return; }
    if (first(input).equalsIgnoreCase("::list"))     { displayMessage(true);     return; }
    if (first(input).equalsIgnoreCase("::clear"))    { clearMessage();           return; }
    if (first(input).equalsIgnoreCase("::commands")) { showCommands();           return; }
    if (first(input).equalsIgnoreCase("::?"))        { showCommands();           return; }
    if (first(input).equalsIgnoreCase("::help"))     { showCommands();           return; }

    if (editLineNum != -1)

    {
      currentWriting.add(editLineNum-1, input);
      currentWriting.remove(editLineNum);
      echo("       #rLine " + editLineNum + " replaced.#N");
      editLineNum = -1;
      return;
    }

   if (insertLineNum != -1)

    {
      currentWriting.add(insertLineNum-1, input);
      echo("       #rLine " + insertLineNum + " inserted.#N");
      insertLineNum = -1;
      return;
    }

    if (currentWriting.size() == maxLines) {
      echo("       #rMaximum number of lines reached.");
      return; }

    currentWriting.add(input);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void handleOutput()

  {
    if (!active) return;

    flushUtilityQueue();
    if (output.length() > 0) output += "\r\n" + getPrompt();
    else output += getPrompt();
    myClient.msg(output);
    active = false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void displayMessage(boolean space)

  {
    String temp;

    if (space) echo("");

    for (int i=0; i<currentWriting.size(); i++)

    {
      temp  = String.format("#N%1$4d", i+1);
      temp += " ] " + (String)currentWriting.get(i);
      echo("\r\n" + temp);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void insertLine(String input)

  {
    input = last(input);
    int lineNum = -1;

    try

    {
      lineNum = Integer.parseInt(input);
      if (lineNum <= 0) throw new Exception();
      if (lineNum > currentWriting.size()) throw new Exception();
      insertLineNum = lineNum;
    }

    catch (Exception e) { echo("       #rInvalid line number.#N"); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void removeLineWriting(String input)

  {
    input = last(input);
    int lineNum = -1;

    try

    {
      lineNum = Integer.parseInt(input);
      if (lineNum <= 0) throw new Exception();
      if (lineNum > currentWriting.size()) throw new Exception();
      currentWriting.remove(lineNum-1);
      echo("       #rLine " + lineNum + " removed.#N");
    }

    catch (Exception e) { echo("       #rInvalid line number.#N"); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void editLine(String input)

  {
    input = last(input);
    int lineNum = -1;

    try

    {
      lineNum = Integer.parseInt(input);
      if (lineNum <= 0) throw new Exception();
      if (lineNum > currentWriting.size()) throw new Exception();
      editLineNum = lineNum;
    }

    catch (Exception e) { echo("       #rInvalid line number.#N"); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String convertWriting()

  {
    String temp = "";
    int size = currentWriting.size();

    for (int i=0; i<size; i++)
      temp += (String)currentWriting.get(i) + "\r\n";

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String showCommands()

  {
    String temp = "";

    temp += "     +--------------------------------+------------------------------+\r\n";
    temp += "     | ::edit   <line number>         | ::commands                   |\r\n";
    temp += "     | ::insert <line number>         | ::display                    |\r\n";
    temp += "     | ::remove <line number>         | ::save                       |\r\n";
    temp += "     | ::clear                        | ::exit                       |\r\n";
    temp += "     +---------------------------------------------------------------+\r\n";
    temp += "\r\n";
    temp += "     These commands must be on a separate line to work.\r\n";
    temp += "     Please write within the boundaries specified.\r\n";

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void clearMessage()

  {
    currentWriting.clear();
    echo("       #rMessage cleared.#N");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void exit()

  {
    exitComposer(false);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void exitComposer(boolean save)

  {
    myClient.popInterface();
    myClient.clearOutputBuffers();
    author.setPlayerState(returnPlayerState);
    myClient.setClientState(returnClientState);
    if (returnAnsi) myClient.getChar().setAnsiMode(true);

    if (save)

    {
      String temp = convertWriting();
      if (currentWritable.getFormat() != Writable.FORMAT_EXACT) {
        temp = temp.replaceAll("\r\n", " ");
        temp = clearWhiteSpace(temp); }
      else if (temp.endsWith("\r\n"))
        temp = temp.substring(0, temp.length()-2);

      currentWritable.write(temp);

      if (currentBoard != null) {
        currentBoard.addNote(currentNote);
        FileManager.saveBoard(currentBoard); }

      if (author.isPlayer()) author.castChar().save();
        myClient.getInterface().goodMsg("Writing completed.");
    }

    else myClient.getInterface().errorMsg("Writing cancelled.");

    if (myClient.getClientState() != CSTATE_BUILDING)
      myClient.getCharInfo().blindEmote("has finished writing.");

    myClient.getInterface().focusGained();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}