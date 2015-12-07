public static class mobList extends Utility

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private ArrayList mobList;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public mobList()

  {
    mobList = new ArrayList();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Mobdesc getDesc(int n)     { return (Mobdesc) mobList.get(n); }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Mobdesc getMobdesc(int n)

  {
    for (int i=0; i<mobList.size(); i++)
      if (getDesc(i).getID() == n) return getDesc(i);

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Mobdesc getMobdesc(String s)

  {
    if (number(s))

    {
      int index = Integer.parseInt(s);
      if ((index < 0) || (index >= size)) return null;
      return getDesc(index);
    }

    s = remove_colors(s);
    int target_num = clipNumber(s);
    s = clipString(s);
    if (s.length() < 3) return null;

    for (int i=0; i<mobList.size(); i++)

    {
      String nm = getDesc(i).getName();

      if ((abbreviation(s, lowercase(remove_colors(nm))))
      || (getDesc(i).checkTargets(s)))

      {
        target_num--;
        if (!target_num) return getDesc(i);
      }
    }

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}