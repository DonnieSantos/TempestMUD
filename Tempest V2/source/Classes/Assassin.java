public class Assassin extends Thief

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public final static String CLASS_COLOR = "#r";

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getCharClass()      { return "Assassin";                  }
  public String getSClass()         { return "As";                        }
  public String getCharClassColor() { return CLASS_COLOR + "Assassin#N";  }
  public String getSClassColor()    { return CLASS_COLOR + "As#N";        }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Assassin(Client C)

  {
    this();
    myClient = C;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Assassin()

  {
    super();
    restore();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}