package tempest.interfaces.gameinterface.commands;

import tempest.data.Data;
import tempest.interfaces.gameinterface.EntityCommand;
import tempest.primitives.MudString;

public class ViewZoneCommand extends EntityCommand
{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public ViewZoneCommand()
  {
    super();
    full.set("ViewZone");
    abbreviation.set("v");
    level.set(0);
    help.set("Immortal View Zone.");
    shortcuts.set("v");
    tablePriority.set(0);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private String GetText()
  {
	  String s ="";
	  
      s+= " +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\r\n";
      s+= " |#:r                                                                           #:b|\r\n";
      s+= " |#:r                                                                           #:b|\r\n";
      s+= " |#:r                                                                           #:b|\r\n";
      s+= " |#:r                                                                           #:b|\r\n";
      s+= " |#:r                                                                           #:b|\r\n";
      s+= " |#:r                                                                           #:b|\r\n";
      s+= " |#:r                                                                           #:b|\r\n";
      s+= " |#:r                                                                           #:b|\r\n";
      s+= " |#:r                                                                           #:b|\r\n";
      s+= " |#:r                                                                           #:b|\r\n";
      s+= " |#:r                                                                           #:b|\r\n";
      s+= " |#:r                                                                           #:b|\r\n";
      s+= " |#:r                                                                           #:b|\r\n";
      s+= " |#:r                                                                           #:b|\r\n";
      s+= " |#:r                                                                           #:b|\r\n";
      s+= " |#:r                                                                           #:b|\r\n";
      s+= " |#:r      #:b          #:r                                                           #:b|\r\n";
      s+= " |#:r      #:b #:r                                                                    #:b|\r\n";
      s+= " |#:r      #:b #:r                                                                    #:b|\r\n";
      s+= " |#:r      #:b #:r                                                                    #:b|\r\n";
      s+= " +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\r\n";

    return s;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString execute(Data entity, MudString parameter)
  {
    return new MudString(GetText());
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}