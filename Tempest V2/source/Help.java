import java.io.*;
import java.util.*;
import java.nio.channels.*;
import java.lang.reflect.Array;

class Help extends Utility

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static ArrayList filenames = new ArrayList();
  public static ArrayList directories = new ArrayList();
  public static ArrayList topics = new ArrayList();
  public static ArrayList allTopicNames = new ArrayList();

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String getFileName(int i)   { return (String) filenames.get(i);   }
  public static String getDirectory(int i)  { return (String) directories.get(i); }
  public static HelpTopic getTopic(int i)   { return (HelpTopic) topics.get(i);   }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void load()

  {
    System.out.print("Loading ");

    String path = FileManager.HELP_PATH;
    int numFiles = FileManager.countFiles(path, false);

    readFileNames();

    for (int i=0; i<directories.size(); i++)
      numFiles += FileManager.countFiles(path + getDirectory(i), false);

    String sp = xchars(tenSpace(numFiles,5).length(),".") + " ";

    System.out.print(numFiles + " Help Files....." + sp);

    for (int i=0; i<filenames.size(); i++)

    {
      String name = getDirectory(i);
      String fname = getFileName(i);
      String content = FileManager.fileToString(fname);
      HelpTopic HT = new HelpTopic(name, content, true);
      topics.add(HT);
    }

    allTopicNames = getTopicNames();

    System.out.println("Done.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String request(String args)

  {
    args = stripNonTopics(args);

    HelpTopic HT = findHelpTopic(args);

    if (HT == null) return "Standard Help";

    return HT.content;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static HelpTopic findHelpTopic(String args)

  {
    for (int i=0; i<topics.size(); i++)

    {
      HelpTopic HT = getTopic(i).findTopic(args, true);
      if (HT != null) return HT;
    }

    for (int i=0; i<topics.size(); i++)

    {
      HelpTopic HT = getTopic(i).findTopic(args, false);
      if (HT != null) return HT;
    }

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static String stripNonTopics(String args)

  {
    String s = new String(args.trim());
    ArrayList validTopics = new ArrayList();

    while (s.length() > 0)

    {
      String s1 = first(s);
      if (validTopic(s1)) validTopics.add(s1);
      s = last(s);
    }

    for (int i=0; i<validTopics.size(); i++)
      s += (String) validTopics.get(i) + " ";

    return s.trim();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static void readFileNames()

  {
    String path = FileManager.HELP_PATH;
    File temp = new File(path);
    File[] fileList = temp.listFiles();
    int numFiles = fileList.length;

    for (int i=0; i<numFiles; i++)

    {
      String fileName = fileList[i].getName();
      if (fileName.indexOf(".txt") != -1) filenames.add(path + fileName);
      else if (fileName.indexOf(".") == -1) directories.add(fileName);
    }

    if (filenames.size() != directories.size())

    {
      System.out.println("Help files and subdirectories size mismatch.");
      System.exit(0);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static boolean validTopic(String str)

  {
    for (int i=0; i<allTopicNames.size(); i++)
    if (abbreviation(str, ((String)allTopicNames.get(i)), 3))
      return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static ArrayList getTopicNames()

  {
    ArrayList names = new ArrayList();

    for (int i=0; i<topics.size(); i++)
      getTopic(i).getAllTopicNames(names);

    return names;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String getDisplayTopics()

  {
    ArrayList A = getTopicNames();
    sortAlphabetical(A);
    return boxify(A,2);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}