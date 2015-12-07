import java.io.*;
import java.util.*;

class Learning extends Utility

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static String learn(Entity Student, String str)

  {
    if ((str == null) || (str.indexOf(" from ") == -1) || (str.length() <= 0))
      return "Learn Usage#n:#N #R[#Nlearn ability_name from teacher_name#R]#N";

    str = str.replaceAll(" from ", " ");

    String tname = getLastWord(str);
    String aname = clipLastWord(str);
    String sname = Student.getName();

    Entity Teacher = Student.getRoom().findEntity(Student, tname);

    if (Teacher == null)
      return "Nobody around by that name.";
    if (Teacher == Student)
      return "You cannot teach yourself.";
    if (!Teacher.isPlayer())
      return Teacher.He() + " cannot teach you anything.";
    if (!Student.isPlayer())
      return "Mobiles cannot be taught anything.";
    if (Student.castChar().getMentor().length() <= 0)
      return Teacher.getName() + " must first accept you as a student.";
    if (!Student.castChar().getMentor().equals(Teacher.getName()))
      return "You may only learn new abilities from your current mentor.";

    boolean found = false;

    Ability TA = Teacher.findAbility(aname);
    Ability SA = Student.findAbility(aname);

    if ((SA != null) && (SA.isSkill()))
      return "You already know that skill.";
    if ((SA != null) && (SA.isSpell()))
      return "You already know that spell.";
    if (TA == null)
      return Teacher.He() + " doesn't know anything about that.";

    tname = Teacher.getName();
    aname = TA.getName();

    boolean classCanLearn = false;
    String myClass = Student.getSClass();
    if (TA.getClasses().indexOf("All") != -1) classCanLearn = true;
    if (TA.getClasses().indexOf(myClass) != -1) classCanLearn = true;

    if ((!classCanLearn) && (TA.isSkill()))
      return classPlural(Student.getCharClass()) + " cannot learn that skill.";
    if ((!classCanLearn) && (TA.isSpell()))
      return classPlural(Student.getCharClass()) + " cannot learn that spell.";

    if (Student.getSTR() < TA.getSTR())
      return "You lack the strength to learn " + aname + ".";
    if (Student.getDEX() < TA.getDEX())
      return "You lack the dexterity to learn " + aname + ".";
    if (Student.getCON() < TA.getCON())
      return "You lack the constitution to learn " + aname + ".";
    if (Student.getINT() < TA.getINT())
      return "You lack the intelligence to learn " + aname + ".";
    if (Student.getWIS() < TA.getWIS())
      return "You lack the wisdom to learn " + aname + ".";

    ArrayList prereqs = TA.getPrereqs();
    ArrayList levreqs = TA.getLevreqs();

    for (int i=0; i<prereqs.size(); i++)

    {
      String RQname = (String) prereqs.get(i);
      int RQlevel = ((Integer)levreqs.get(i)).intValue();
      Ability PR = Student.findAbility(RQname);

      if ((PR == null) || (PR.getLevel() < RQlevel))
        return "To learn " + aname + " requires " + RQname + " level " + RQlevel + ".";
    }

    Teacher.castChar().setStudent(Student.getName());
    Teacher.castChar().setRequest(aname);
    Teacher.echo(sname + " asks you to teach " + Student.him() + " the art of " + aname + ".");

    return "You ask " + tname + " to teach you the art of " + aname + ".";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static String unlearn(Entity ENT, String str)

  {
    if (str.indexOf("YES") == -1) {
      String temp = "Unlearn Usage#n:#R [#Nunlearn ability_name YES#R]#N\r\n\n";
      temp += "WARNING: You will lose current ability level permanently!";
      return temp; }

    str = str.replaceAll("YES","");
    str = clearWhiteSpace(str);

    Ability A = ENT.removeAbility(str);

    if (A == null)
      return "You do not possess an ability by that name.";
    else if (A.isSkill())
      return "#rYou have forgotten the skill " + A.getName() + "!#N";
    else
      return "#rYou have forgotten the spell " + A.getName() + "!#N";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static String teach(Entity Teacher, String rem)

  {
    if ((rem.length() <= 0) || (last(rem).length() <= 0))
      return "Teach Usage#n:#N #R[#Nteach student_name ability_name#R]#N";

    String target = first(rem);
    String ability = last(rem);

    Entity Student = Teacher.getRoom().findEntity(Teacher, target);

    if (Student == null)
      return "Nobody around by that name.";
    if (Student == Teacher)
      return "You cannot teach yourself.";
    if (!Student.getName().equals(Teacher.castChar().getStudent()))
      return Student.getName() + " has not asked you to teach " + Student.him() + " anything.";
    if (!Teacher.castChar().getRequest().equalsIgnoreCase(ability))
      return Student.getName() + " would rather learn " + Teacher.castChar().getRequest() + ".";

    Ability SA = Student.findAbility(ability);
    Ability TA = Teacher.findAbility(ability);

    if (TA == null)
      return "You don't possess knowledge of that ability.";
    if (SA != null)
      return "Your student already knows that ability!";

    // ******************************************************************************** //
    // The following is redundant in that these checks are performed when the student   //
    // requests to be taught the skill or spell.  However, just in case some unforseen  //
    // case occurs in which the student asks to learn the skill, and then much later at //
    // a time when their characters have changed considerably they no longer meet the   //
    // requirements, these checks are performed again, just to be sure.                 //
    // ******************************************************************************** //

    boolean classCanLearn = false;
    String myClass = Student.getSClass();
    if (TA.getClasses().indexOf("All") != -1) classCanLearn = true;
    if (TA.getClasses().indexOf(myClass) != -1) classCanLearn = true;

    if ((!classCanLearn) && (TA.isSkill()))
      return classPlural(Student.getCharClass()) + " cannot learn that skill.";
    if ((!classCanLearn) && (TA.isSpell()))
      return classPlural(Student.getCharClass()) + " cannot learn that spell.";

    if (Student.getSTR() < TA.getSTR())
      return Student.getName() + " lacks the strength to learn " + TA.getName() + ".";
    if (Student.getDEX() < TA.getDEX())
      return Student.getName() + " lacks the dexterity to learn " + TA.getName() + ".";
    if (Student.getCON() < TA.getCON())
      return Student.getName() + " lacks the constitution to learn " + TA.getName() + ".";
    if (Student.getINT() < TA.getINT())
      return Student.getName() + " lacks the intelligence to learn " + TA.getName() + ".";
    if (Student.getWIS() < TA.getWIS())
      return Student.getName() + " lacks the wisdom to learn " + TA.getName() + ".";

    ArrayList prereqs = TA.getPrereqs();
    ArrayList levreqs = TA.getLevreqs();
    String aName = TA.getName();

    for (int i=0; i<prereqs.size(); i++)

    {
      String RQname = (String) prereqs.get(i);
      int RQlevel = ((Integer)levreqs.get(i)).intValue();
      Ability PR = Student.findAbility(RQname);

      if ((PR == null) || (PR.getLevel() < RQlevel))
        return "To learn " + aName + " requires " + RQname + " level " + RQlevel + ".";
    }

    // ******************************************************************************** //
    // ******************************************************************************** //
    // ******************************************************************************** //

    Ability A = null;
    Class C = null;

    try

    {
      C = Class.forName(getProperClassName(Teacher.castChar().getRequest()));
      A = (Ability) C.newInstance();
    }

    catch (Exception e) { return "There is no such skill or spell."; }
    catch (Throwable t) { return "There is no such skill or spell."; }

    try

    {
      if (A.isSkill())

      {
        Skill Sk = (Skill) A;
        Sk.init(Student, 0);

        Student.addSkill(Sk);

        Student.echo("#y" + Teacher.getName() + " teaches you the skill " + Sk.getName() + "!");
        return "#yYou teach " + Student.getName() + " the skill " + Sk.getName() + "!";
      }

      if (A.isSpell())

      {
        Spell Sp = (Spell) A;
        Sp.init(Student, 0);

        Student.addSpell(Sp);

        Student.echo("#m" + Teacher.getName() + " teaches you the spell " + Sp.getName() + "!");
        return "#mYou teach " + Student.getName() + " the spell " + Sp.getName() + "!";
      }
    }

    catch (Exception e) { return "Something went wrong."; }

    return "Something went very wrong.";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}