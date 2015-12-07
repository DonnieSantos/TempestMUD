#ifndef LEGEND_H
#define LEGEND_H

class legend_mark

{
  public:
    legend_mark() { }
    string mark;
    string date;
    int times;
};

class legend

{
  public:
    legend(entity*);
    int add_mark(string);
    int add_mark(string, string);
    int update_mark(int, string);
    void auto_mark(string, int, string);
    string get_legend();
    string get_fileinfo();

  private:
    int size;
    entity* owner;
    legend_mark** list;
};

legend::legend(entity *ENT)

{
  size = 0;
  owner = ENT;
  list = new legend_mark * [MAX_LEGEND_SIZE];
}

int legend::add_mark(string mark)

{
  string date = "Vallum " + get_gamedate("vallum") + ".";
  return add_mark(mark, date);
}

int legend::add_mark(string mark, string date)

{
  if (size >= MAX_LEGEND_SIZE) return 0;

  int match = -1;

  for (int i=0; i<size; i++)
    if (list[i]->mark == mark)
      match = i;

  if (match >= 0) return update_mark(match, date);

  size++;

  legend_mark *LM = new legend_mark();
  LM->mark = mark;
  LM->date = date;
  LM->times = 1;

  list[size-1] = LM;

  return 1;
}

void legend::auto_mark(string Mark, int Times, string Date)

{
  size++;

  legend_mark *LM = new legend_mark();
  LM->mark = Mark;
  LM->date = Date;
  LM->times = Times;

  list[size-1] = LM;
}

int legend::update_mark(int position, string date)

{
  legend_mark *updated_mark = list[position];

  updated_mark->date = date;

  if (updated_mark->times < 999)
    updated_mark->times++;

  for (int i=position; i<size-1; i++)
    list[i] = list[i+1];

  list[size-1] = updated_mark;

  return 1;
}

string legend::get_legend()

{
  int left_width = 0;
  int right_width = 0;
  int left_size;
  int right_size;
  int bottom_width;
  int width;

  for (int i=0; i<size; i++)

  {
    left_size = visible_size(list[i]->mark);
    if (list[i]->times > 1) left_size += 4;
    if (list[i]->times > 9) left_size += 1;
    if (list[i]->times > 99) left_size += 1;

    right_size = visible_size(list[i]->date);

    if (left_size > left_width) left_width = left_size;
    if (right_size > right_width) right_width = right_size;
  }

  int name_size = visible_size(owner->get_name());
  int clan_size = visible_size(owner->get_clan());
  if (owner->get_clan() == "None") clan_size = 0;
  else clan_size = clan_size + 3;
  int title_width = name_size + clan_size + 1;

  width = left_width + right_width + 6;
  bottom_width = width;
  if (title_width > width) width = title_width;

  string border = "  #n+-";
  for (int i=1; i<=width; i++) border += "-";
  border += "-+#N";

  string temp = border + "\r\n";

  temp += "  #n|#N " + owner->get_name() + ".";

  if (clan_size > 0) temp += " (" + owner->get_clan() + "#N)";

  for (int i=1; i<=width-title_width; i++) temp += " ";
  temp += " #n|#N\r\n" + border + "\r\n";

  int lspaces;
  int rspaces;

  for (int i=0; i<size; i++)

  {
    lspaces = left_width - visible_size(list[i]->mark);
    rspaces = right_width - visible_size(list[i]->date);

    if (list[i]->times > 1) lspaces -= 4;
    if (list[i]->times > 9) lspaces -= 1;
    if (list[i]->times > 99) lspaces -= 1;

    temp += "  #n|#N ";
    temp += list[i]->mark;

    if (list[i]->times > 1) temp += " #N(" + intconvert(list[i]->times) + ")";

    for (int j=1; j<=lspaces; j++) temp += " ";
    temp += "  #N--  ";
    temp += list[i]->date;
    for (int j=1; j<=title_width-bottom_width; j++) temp += " ";
    for (int j=1; j<=rspaces; j++) temp += " ";
    temp += " #n|#N\r\n";
  }

  temp += border;

  return temp;
}

string legend::get_fileinfo()

{
  string temp = "Legend Size = " + intconvert(size) + "\n";

  for (int i=0; i<size; i++)

  {
    temp += "LEGEND: ";
    temp += intconvert(list[i]->times) + " ";
    temp += "[" + list[i]->date + "] ";
    temp += list[i]->mark;
    temp += "\n";
  }

  return temp;
}

#endif