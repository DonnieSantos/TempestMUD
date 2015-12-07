import java.io.*;
import java.util.*;

class Account extends Utility implements Externalizable, Cloneable

{
  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 18;

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private String username;
  private String password;
  private String emailAddress;
  private String creationStamp;
  private String registerStamp;
  private String lastLoginDate;
  private String lastLoginIP;
  private String[] characterNames;
  private boolean registered;
  private boolean placeHolder;
  private int numChars;
  private int badPasswords;

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public int getSize()                   { return numChars;          }
  public int getBadPasswords()           { return badPasswords;      }
  public String getUsername()            { return username;          }
  public String getPassword()            { return password;          }
  public String getChar(int i)           { return characterNames[i]; }
  public String getLastLoginDate()       { return lastLoginDate;     }
  public String getLastLoginIP()         { return lastLoginIP;       }
  public boolean isPlaceHolder()         { return placeHolder;       }
  public void setUsername(String s)      { username = s;             }
  public void setPassword(String s)      { password = s;             }
  public void setEmailAddress(String s)  { emailAddress = s;         }
  public void setBadPasswords(int i)     { badPasswords = i;         }
  public void setPlaceHolder(boolean b)  { placeHolder = b;          }
  public void setCharacters(String[] s)  { characterNames = s;       }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public Account()

  {
    username = "VOID";
    password = "Empty Password";
    emailAddress = "Not Specified";
    creationStamp = "Not Yet Created";
    registerStamp = "Not Yet Registered";
    lastLoginDate = "Never Logged In";
    lastLoginIP = "Never Logged In";
    registered = false;
    placeHolder = false;
    numChars = 0;

    characterNames = new String [CHARS_PER_ACCOUNT];

    for (int i=0; i<CHARS_PER_ACCOUNT; i++)
      characterNames[i] = "";

    stamp();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public boolean isOnline()

  {
    return (ClientList.findClient(username) != -1);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void stamp()

  {
    creationStamp = SystemTime.getDate();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void loginStamp(String ipAddress)

  {
    lastLoginIP = ipAddress;
    lastLoginDate = SystemTime.getTimeStamp();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void setRegistered(boolean regged)

  {
    if ((!registered) && (regged)) registerStamp = SystemTime.getDate();
    registered = regged;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public String getAddCharacterError(String name)

  {
    if (numChars >= CHARS_PER_ACCOUNT)
      return "You already have the maximum number of characters in this account.";

    if (!validCharacterName(name))
      return "Names are limited to 12 characters with no numbers or spaces.";

    return "Unable to add character.";
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public boolean canAddCharacter(String name)

  {
    if (numChars >= CHARS_PER_ACCOUNT) return false;
    if (!validCharacterName(name)) return false;
    return true;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void addCharacter(String name)

  {
    characterNames[numChars] = properName(name);
    numChars++;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void updateCharacters()

  {
    int num = numChars;
    boolean sort = false;

    for (int i=0; i<num; i++)
    if (!Char.exists(characterNames[i]))

    {
      numChars--;
      characterNames[i] = "";
      sort = true;
    }

    if (sort) sortCharacters();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private void sortCharacters()

  {
    for (int b=0; b<CHARS_PER_ACCOUNT-1; b++)
    for (int i=0; i<CHARS_PER_ACCOUNT-1; i++)
    if (characterNames[i].equals(""))

    {
      characterNames[i] = characterNames[i+1];
      characterNames[i+1] = "";
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void deleteAllCharacters()

  {
    updateCharacters();

    ArrayList charList = new ArrayList();

    for (int i=0; i<numChars; i++)

    {
      Entity ENT = World.findCharacter(characterNames[i]);
      if (ENT != null) charList.add(ENT);
    }

    for (int i=0; i<charList.size(); i++)
      ((Entity)charList.get(i)).castChar().deleteCharacter();

    updateCharacters();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean validCharacterName(String cname)

  {
    if (cname.length() > MAX_CHAR_NAME_LENGTH) return false;

    for (int i=0; i<cname.length(); i++)
    if (!letter(cname.charAt(i)))
      return false;

    return true;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean validAccountName(String aname)

  {
    if (aname.length() > MAX_ACCT_NAME_LENGTH) return false;

    for (int i=0; i<aname.length(); i++)
    if ((!letter(aname.charAt(i))) && (!number(aname.charAt(i))))
      return false;

    return true;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean validPassword(String pass)

  {
    if (pass.length() > MAX_PASSWORD_LENGTH) return false;

    for (int i=0; i<pass.length(); i++)
    if ((!letter(pass.charAt(i))) && (!number(pass.charAt(i))))
      return false;

    return true;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean usableCharacterName(String cname)

  {
    if (Char.exists(cname)) return false;
    if (reservedName(cname)) return false;
    return true;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean usableAccountName(String aname)

  {
    if (FileManager.accountExists(aname)) return false;
    if (reservedName(aname)) return false;
    return true;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean validEmailAddress(String addy)

  {
    if (addy == null) return false;
    if (addy.length() == 0) return false;

    int at = addy.indexOf("@");
    int dt = addy.lastIndexOf(".");

    if ((at == -1) || (dt == -1)) return false;
    if (dt < at) return false;

    String name = addy.substring(0, at);
    String addr = addy.substring(at+1, addy.length());

    if (!validEmailToken(name)) return false;
    if (!validEmailToken(addr)) return false;

    return true;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean validEmailToken(String str)

  {
    for (int i=0; i<str.length(); i++)

    {
      char c = str.charAt(i);

      if ((!number(c)) && (!letter(c)) && (c != '_') && (c != '-') && (c != '.'))
        return false;
    }

    return true;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean reservedName(String cname)

  {
    return false;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void changedUsername()

  {
    try

    {
      for (int i=0; i<CHARS_PER_ACCOUNT; i++)
      if (characterNames[i].length() > 0)

      {
        Entity ENT = World.findCharacter(characterNames[i]);
        ENT.castChar().setAccountName(username);
        ENT.castChar().save();
      }
    }

    catch (Exception e) { e.printStackTrace(); }
  }
  
  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////
  
  public Account replicate()
  
  {
    try 
    
    {
      Account A = (Account) clone();
      String[] charList = new String[characterNames.length];
      
      for (int i=0; i<characterNames.length; i++)
        charList[i] = characterNames[i];
      
      A.setCharacters(charList);
      
      return A;
    }
    
    catch (Exception e) { e.printStackTrace(); }
    
    return new Account();
  }
  
  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////
  
  public void save()
  
  {
    WriteThread.addObject(replicate());
  }
  
  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////
  
  public static boolean exists(String name)
  
  {
    if (World.findAccount(name) == null) return false;
    return true;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public String getAccountMenu(String msg)

  {
    String menu = Ansi.clearScreen + "\r\n" + msg + "#N\r\n\n";

    String top = "#n +-----------------------------------------------------------+#N";
    String mid = "#n +-----------------------+-----------------------------------+#N";

    String name = username + " ";
    if (registered) name += "#n(#gregistered#n)#N";
    else name += "#n(#runregistered#n)#N";

    menu += top + "\r\n" + " #n|#N #YAccount Name#n:#N  " + name;
    menu += xchars(43-visibleSize(name)," ") + "#n|#N\r\n" + mid + "\r\n";
    menu += " #n|#N #YChoose Your Character #n|#N        ";
    menu += "#YManage Your Account        #n|#N\r\n" + mid + "\r\n";

    ArrayList names = new ArrayList();

    for (int i=0; i<CHARS_PER_ACCOUNT; i++)

    {
      String row = " #n| (#C" + (i+1) + "#n)#N" + tenSpace((i+1),1) + " - ";
      if (characterNames[i].length() <= 0) row += "[Unused]       ";
      else row += "#n" + characterNames[i] + xchars(15-visibleSize(characterNames[i])," ");
      row += "#n|#N";
      names.add(row);
    }

    menu += (String)names.get(0) + " #n(#MN#n)#N - Make A New Character.       #n|#N\r\n";
    menu += (String)names.get(1) + " #n(#MD#n)#N - Delete A Character.         #n|#N\r\n";
    menu += (String)names.get(2) + " #n(#MP#n)#N - Change Your Password.       #n|#N\r\n";
    menu += (String)names.get(3) + " #n(#ME#n)#N - Change Your E-mail Address. #n|#N\r\n";
    menu += (String)names.get(4) + " #n(#MS#n)#N - View Account Statistics.    #n|#N\r\n";
    menu += (String)names.get(5) + " #n(#MR#n)#N - Register Your Account.      #n|#N\r\n";
    menu += (String)names.get(6) + " #n(#MX#n)#N - Delete This Account.        #n|#N\r\n";
    menu += (String)names.get(7) + "                                   #n|#N\r\n";
    menu += (String)names.get(8) + " #n(#GC#n)#N - View Game Credits.          #n|#N\r\n";
    menu += (String)names.get(9) + " #n(#RQ#n)#N - Quit the Game.              #n|#N\r\n";

    menu += mid + "\r\n\n Select: ";

    return menu;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public String getAccountInfo(boolean IMM)

  {
    int width = 0;
    ArrayList head = new ArrayList();
    ArrayList info = new ArrayList();

    head.add(" #n|#Y Account Username: #n|#N ");
    head.add(" #n|#Y Account Password: #n|#N ");
    head.add(" #n|#Y Account Created:  #n|#N ");
    head.add(" #n|#Y Last Registered:  #n|#N ");
    if (IMM) head.add(" #n|#Y Last Login Date:  #n|#N ");
    if (IMM) head.add(" #n|#Y Last IP Address:  #n|#N ");
    head.add(" #n|#Y E-Mail Address:   #n|#N ");
    head.add(" #n|#Y Characters:       #n|#N ");
    head.add(" #n|#Y Registered:       #n|#N ");
    head.add(" #n|#Y Blocked:          #n|#N ");

    String cstamp = creationStamp.replaceAll("/","#N/#r");
    String rstamp = registerStamp.replaceAll("/","#N/#r");

    String reg = "#GRegistered#N";
    String blk = "#RBLOCKED#N";

    boolean blocked = AccessManager.manager.isAccountBanned(this);

    if (!registered) reg = "#RUNREGISTERED#N";
    if (!blocked)    blk = "#GOpen Access#N";

    info.add("#n" + username + "#N");
    info.add("#n" + password + "#N");
    info.add("#r" + cstamp + "#N");
    info.add("#r" + rstamp + "#N");
    if (IMM) info.add("#N" + lastLoginDate + "#N");
    if (IMM) info.add("#N" + lastLoginIP + "#N");
    info.add("#g" + emailAddress + "#N");
    info.add("#n" + new String(""+numChars) + "#N");
    info.add(reg);
    info.add(blk);

    if (IMM)
    for (int i=0; i<numChars; i++)

    {
      head.add(" #n|#Y Character " + (i+1) + ":     " + tenSpace((i+1),1) + "#n|#N ");
      info.add("#n" + characterNames[i] + "#N");
    }

    for (int i=0; i<info.size(); i++)
    if (visibleSize((String)info.get(i)) > width)
      width = visibleSize((String)info.get(i));

    String border = " #n+-------------------+-" + xchars(width,"-") + "-+#N";
    String temp = "";

    if (!IMM) temp += "\r\n";
    temp += border + "\r\n";

    for (int i=0; i<info.size(); i++)

    {
      if ((IMM) && (i == info.size()-numChars)) temp += border + "\r\n";
      temp += (String)head.get(i) + (String)info.get(i);
      temp += xchars(width-visibleSize((String)info.get(i))," ") + " #n|#N\r\n";
    }

    temp += border;

    if (!IMM) temp += "\r\n\n [Press #nEnter#N to Continue] : ";

    return temp;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("USERNAME");       out.writeObject(username);
      out.writeObject("PASSWORD");       out.writeObject(password);
      out.writeObject("EMAILADDRESS");   out.writeObject(emailAddress);
      out.writeObject("CREATIONSTAMP");  out.writeObject(creationStamp);
      out.writeObject("REGISTERSTAMP");  out.writeObject(registerStamp);
      out.writeObject("LASTLOGINDATE");  out.writeObject(lastLoginDate);
      out.writeObject("LASTLOGINIP");    out.writeObject(lastLoginIP);
      out.writeObject("CHARNAMES");      out.writeObject(characterNames);
      out.writeObject("NUMCHARS");       out.writeObject(new Integer(numChars));
      out.writeObject("REGISTERED");     out.writeObject(new Boolean(registered));
      out.writeObject("BADPASSWORDS");   out.writeObject(new Integer(badPasswords));

      out.writeObject("ACCOUNT END");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
    try

    {
      String tag = "";

      while (!tag.equals("ACCOUNT END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("USERNAME"))            username = (String)in.readObject();
        else if (tag.equals("PASSWORD"))       password = (String)in.readObject();
        else if (tag.equals("EMAILADDRESS"))   emailAddress = (String)in.readObject();
        else if (tag.equals("CREATIONSTAMP"))  creationStamp = (String)in.readObject();
        else if (tag.equals("REGISTERSTAMP"))  registerStamp = (String)in.readObject();
        else if (tag.equals("LASTLOGINDATE"))  lastLoginDate = (String)in.readObject();
        else if (tag.equals("LASTLOGINIP"))    lastLoginIP = (String)in.readObject();
        else if (tag.equals("CHARNAMES"))      characterNames = (String[])in.readObject();
        else if (tag.equals("NUMCHARS"))       numChars = ((Integer)in.readObject()).intValue();
        else if (tag.equals("REGISTERED"))     registered = ((Boolean)in.readObject()).booleanValue();
        else if (tag.equals("BADPASSWORDS"))   badPasswords = ((Integer)in.readObject()).intValue();
        else if (!tag.equals("ACCOUNT END"))   in.readObject();
      }
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////
}