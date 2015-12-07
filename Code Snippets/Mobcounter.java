public class Mobcounter

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private int size;
  private int done;
  private int count;
  private int[][] ids;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Mobcounter()

  {
    ids = new int[100][2];
    size = 0;

    for (int i=0; i<100; i++)
    for (int j=0; j<2; j++)
      ids[i][j] = 0;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void add(int num)

  {
    count = 0;
    done = 0;

    while (!done)

    {
      if (count == size) {
        ids[size][0] = num;
        ids[size][1]++;
        size++;
        done = 1; }

      else if (ids[count][0] == num) {
        ids[count][1]++;
        count++;
        done = 1; }

      else count++;
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void clear()

  {
    for (int i=size-1; i>=0; i--) {
      ids[i][0] = 0;
      ids[i][1] = 0; }

    size = 0;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String stack()

  {
    String str = "";

    for (int i=0; i<size; i++)

    {
      if (ids[i][1] == 1)
        str = str + "\r\n" + Moblist.get_mobdesc(ids[i][0]).title;

      else if ((ids[i][1] > 1) && (ids[i][1] < 10))
        str = str + "\r\n" + "[ " + ids[i][1] + "] " + Moblist.get_mobdesc(ids[i][0]).title;

      else if ((ids[i][1] > 9) && (ids[i][1] < 100))
        str = str + "\r\n" + "[" + ids[i][1] + "] " + Moblist.get_mobdesc(ids[i][0]).title;
    }

    clear();

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String shortStack(int n)

  {
    String str = "";
    String index = "" + n;

    for (int i=0; i<size; i++)

    {
      if (ids[i][1] == 1)
        str = str + "\r\n    " + index + " - " + Moblist.get_mobdesc(ids[i][0]).name;

      else if ((ids[i][1] > 1) && (ids[i][1] < 10)) {
        str  = str + "\r\n    " + index + " - " + "[ " + ids[i][1] + "] ";
        str += Moblist.get_mobdesc(ids[i][0]).name; }

      else if ((ids[i][1] > 9) && (ids[i][1] < 100)) {
        str  = str + "\r\n    " + index + " - " + "[" + ids[i][1] + "] ";
        str += Moblist.get_mobdesc(ids[i][0]).name; }
    }

    clear();

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}