import java.io.*;
import java.util.*;

class Question

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static final int ACCEPT_MENTOR = 1;
  public static final int LEAVE_MENTOR  = 2;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private Entity mob;
  private Entity responder;
  private int questionType;
  private ArrayList answerParms;
  private int counter;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Entity getResponder()  { return responder; }
  public void decrement()       { counter--;        }
  public int getCounter()       { return counter;   }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Question(Entity Owner, Entity Responder, int QuestionType)

  {
    mob = Owner;
    responder = Responder;
    questionType = QuestionType;
    answerParms = new ArrayList();
    counter = 5;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addAnswerParameter(String str)

  {
    answerParms.add(new String(str));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getFailedMessage(String asker)

  {
    String str = "";

    if (responder.getName().equalsIgnoreCase(asker))

    {
      str  = "I'm still waiting for you to answer my previous question, ";
      str += responder.getName() + ". Why don't you answer that first?";
    }

    else

    {
      str  = asker + ", I am already waiting for " + responder.getName();
      str += " to answer a different question. Please wait until ";
      str += responder.he() + " answers before posing another question.";
    }

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean receiveAnswer(String answer)

  {
    switch(questionType)

    {
      case ACCEPT_MENTOR:  return answerAcceptMentor(answer);
      case LEAVE_MENTOR:   return answerLeaveMentor(answer);
    }

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private boolean answerAcceptMentor(String answer)

  {
    ArrayList words = Utility.getAllWords(answer);
    String mentorName = (String) answerParms.get(0);
    Mobile Mob = mob.castMob();

    if (negativeAnswer(answer, words))

    {
      Mob.act("respond Very well then.");
      return true;
    }

    if (affirmativeAnswer(answer, words))

    {
      Entity E = mob.getRoom().findEntity(mob, mentorName);

      if (E == null) {
        Mob.act("respond Bring your mentor here and try again.");
        return true; }

      boolean added = E.castChar().addStudent(responder.getName());
      responder.castChar().setMentor(E.getName());

      String speech  = "Then so shall it be, from this day forward ";
             speech += responder.getName() + " the " + responder.getCharClass();
             speech += " shall be the student of " + E.getFullName() + ".";
             speech += " May the Gods guide " + responder.his() + " path and";
             speech += " lead " + responder.him() + " into fortunate destiny.";

      if (added) E.addLegendMark("Mentored " + responder.getName() + ".");
      responder.addLegendMark("Mentored By " + E.getName() + ".");

      if (!added) E.castChar().save();

      Mob.act("respond " + speech);
      return true;
    }

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private boolean answerLeaveMentor(String answer)

  {
    ArrayList words = Utility.getAllWords(answer);
    Mobile Mob = mob.castMob();

    if (negativeAnswer(answer, words))

    {
      Mob.act("respond That's probably a wise decision.");
      return true;
    }

    if (affirmativeAnswer(answer, words))

    {
      responder.castChar().setMentor("");
      responder.scheduleEvent(Schedule.LEAVE_MENTOR, SystemTime.ONE_WEEK);
      responder.castChar().save();

      String speech  = "I am sad to see you do it " + responder.getName() + ", ";
             speech += "but you have made your decision. You have abandoned your ";
             speech += "mentor, and are now without a teacher or guide.";

      Mob.act("respond " + speech);
      return true;
    }

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private boolean negativeAnswer(String answer, ArrayList words)

  {
    answer = Utility.makePlain(answer);

    for (int i=0; i<words.size(); i++)

    {
      String S = (String) words.get(i);
      if (S.equals("no")) return true;
    }

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private boolean affirmativeAnswer(String answer, ArrayList words)

  {
    answer = Utility.makePlain(answer);

    for (int i=0; i<words.size(); i++)

    {
      String S = (String) words.get(i);
      if (S.equals("yes")) return true;
    }

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}