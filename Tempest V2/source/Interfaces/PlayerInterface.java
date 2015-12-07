public class PlayerInterface extends MudInterface

{
  ////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////

  private static String tempStr;
  private static Entity tempEnt;

  ////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////

  public PlayerInterface(Client C) { super(C); }

  ////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////

  public int viewMode()     { return ALL_OUTPUT; }
  public void exit()        {                    }

  ////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////

  public void handleInput()

  {
    if (!myClient.commandWaiting()) return;
    if (myClient.commandsLocked())  return;

    Interpreter.interpretCommand(getCharInfo(), myClient.getCommand());
  }

  ////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////

  public void handleOutput()

  {
    for (int i=0; i<QList.size(); i++)
      sendQueue(getQueue(i));

    QList.clear();
  }

  ////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////

  public void onDisconnect()

  {
    Entity E = getChar();
    String nm = E.getName();
    String ip = myClient.getIPAddress();
    ClientList.addLinkdead(myClient);

    E.smsg(nm + " has gone linkdead.");
    E.sendToAwake(nm + " has gone linkdead.");
    SystemLog.addLog(ip + " " + nm + " went linkdead.");

    if (myClient.getClientState() == CSTATE_POSSESS)
      Immortal.unpossess(myClient, false);
  }

  ////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////

  public void focusGained()

  {
    myClient.getCharInfo().look(menuMsg);
  }

  ////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////

  public String getPrompt()

  {
    tempEnt = myClient.getCharInfo();

    if (getChar().getAnsiMode())
      return Ansi.getAnsiBar(getChar(), getChar().getScreenSize());

    if (tempEnt.isPlayer())

    {
      tempStr = "#N" + tempEnt.castChar().getPrompt();
      tempStr = tempStr.replaceFirst("%hp", tempEnt.getCurrentHP()+"");
      tempStr = tempStr.replaceFirst("%mn", tempEnt.getCurrentMN()+"");
      tempStr = tempStr.replaceFirst("%mv", tempEnt.getCurrentMV()+"");
      tempStr = tempStr.replaceFirst("%mhp", tempEnt.getMaxHP()+"");
      tempStr = tempStr.replaceFirst("%mmn", tempEnt.getMaxMN()+"");
      tempStr = tempStr.replaceFirst("%mmv", tempEnt.getMaxMV()+"");
      
      if (tempStr.indexOf("%e") != -1)
         
      {
         String exitList = "";
         Room RM = tempEnt.getRoom();
         if (RM.getN() != 0) exitList += "n";
         if (RM.getS() != 0) exitList += "s";
         if (RM.getE() != 0) exitList += "e";
         if (RM.getW() != 0) exitList += "w";
         if (RM.getU() != 0) exitList += "u";
         if (RM.getD() != 0) exitList += "d";
         if (exitList.length() == 0) exitList += "none";
         tempStr = tempStr.replaceFirst("%e", exitList);
       }

      tempStr += " ";
    }

    else

    {
      tempStr = "#c< #n" + tempEnt.getName()
        + " #g" + tempEnt.getCurrentHP()
        + "#Ghp #c>#N ";
    }

    return tempStr;
  }

  ////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////

  public void addOutput(String s)

  {
    if (combatRedirect) putOutput(fightOutput, s);
    else if (skillRedirect) putOutput(skillOutput, s);
    else putOutput(normalOutput, s);
  }

  ////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////

  public void addFightOutput(String s)

  {
    if (skillRedirect) putOutput(skillOutput, s);
    else if (normalRedirect) putOutput(normalOutput, s);
    else putOutput(fightOutput, s);
  }

  ////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////
}