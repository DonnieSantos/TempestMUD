using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;
using System.IO;

namespace Tempest_Builder

{
  public class Utility

  {
    public Utility() {}

    static public string clear_whitespace(string s)

    {
      int size = s.Length, temp;

      if (size < 1) 
      {
        s = "Invalid_String";
      }

      else if (size == 1) 
      {
        if (s[0] == ' ')
          s = "Invalid_String";
      }

      else

      {
        while (s[size-1] == ' ') {
          s = s.Substring(0, size-1);
          size--; }

        while (s[0] == ' ') {
          s =  s.Substring(1, size-1);
          size--; }

        temp = s.IndexOf("  ");

        while (temp != -1) {
          s = s.Remove(temp, 1);
          temp = s.IndexOf("  "); }
      }

      return s;
    }

    static public string lowercase(string s)

    {
      int n = s.Length;
      char[] str = new char[n];

      for (int i=0; i<n; i++) str[i] = s[i];

      for (int i=0; i<=n-1; i++)

      {
        if ((str[i] >= 65) && (str[i] <= 90))
          str[i] = (char)((int)str[i] + 32);
      }

      s = "";

      for (int i=0; i<n; i++) s += str[i];

      return s;
    }

    static public int number(char c)

    {
      if ((c >= 48) && (c <= 57))
        return 1;

      return 0;
    }

    static public int number(string s)

    {
      int size = s.Length;

      if (size <= 0) return 0;

      for (int i=0; i<size; i++)
        if (number(s[i]) == 0)
          return 0;

      return 1;
    }
  }
}
