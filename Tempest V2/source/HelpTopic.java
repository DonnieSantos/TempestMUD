import java.io.*;
import java.util.*;
import java.nio.channels.*;
import java.lang.reflect.Array;

class HelpTopic extends Utility

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String name;
  public String content;
  public ArrayList subtopics;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public HelpTopic getSubTopic(int i)  { return (HelpTopic) subtopics.get(i); }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public HelpTopic(String nm, String ct, boolean recurse)

  {
    name = nm;
    content = ct;
    subtopics = null;

    if (recurse) readSubTopics();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public HelpTopic findTopic(String args, boolean byCategory)

  {
    String s1 = first(args);
    String s2 = first(last(args));

    if (!byCategory) return findPureTopic(s1);

    boolean category = abbreviation(s1, name, 3);

    if (category == true)
    if (subtopics != null)
    for (int i=0; i<subtopics.size(); i++)
    if (getSubTopic(i).findTopic(s2, byCategory) != null)
      return getSubTopic(i);

    if (category == true) return this;

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public HelpTopic findPureTopic(String str)

  {
    if (abbreviation(str, name, 3)) return this;

    if (subtopics != null)
    for (int i=0; i<subtopics.size(); i++)
    if (getSubTopic(i).findPureTopic(str) != null)
      return getSubTopic(i);

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void getAllTopicNames(ArrayList A)

  {
    A.add(name);

    if (subtopics != null)
    for (int i=0; i<subtopics.size(); i++)
      getSubTopic(i).getAllTopicNames(A);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private void readSubTopics()

  {
    String path = FileManager.HELP_PATH + name + "//";

    File temp = new File(path);
    File[] fileList = temp.listFiles();
    int numFiles = fileList.length;

    if (numFiles > 0) subtopics = new ArrayList();

    for (int i=0; i<numFiles; i++)

    {
      String fileName = fileList[i].getName();
      String subName = fileName.replaceAll(".txt", "");
      String subCont = FileManager.fileToString(path + fileName);
      HelpTopic HT = new HelpTopic(subName, subCont, false);
      subtopics.add(HT);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}