#ifndef SCHEDULE_H
#define SCHEDULE_H

class schedule

{
  public:
    schedule();
    void schedule_event(int, long);
    void set_event(int, long);
    void cancel_event(int);
    int check_event(int);
    string get_schedule();

  private:
    long* Calendar;
};

schedule::schedule()

{
  Calendar = new long [SCHEDULE_SIZE];

  for (int i=0; i<SCHEDULE_SIZE; i++)
    Calendar[i] = 0;
}

void schedule::schedule_event(int ID, long minutes)

{
  Calendar[ID] = get_gametime() + minutes;
}

void schedule::set_event(int ID, long deadline)

{
  Calendar[ID] = deadline;
}

void schedule::cancel_event(int ID)

{
  Calendar[ID] = 0;
}

int schedule::check_event(int ID)

{
  if (Calendar[ID] == 0) return 1;

  if (get_gametime() >= Calendar[ID])

  {
    Calendar[ID] = 0;
    return 1;
  }

  return 0;
}

string schedule::get_schedule()

{
  int count = 0;
  string temp = "";

  for (int i=0; i<SCHEDULE_SIZE; i++)
  if (Calendar[i] != 0)

  {
    count++;
    temp = temp + "\r\nSCHEDULE: " + intconvert(i) + " " + intconvert(Calendar[i]);
  }

  temp = "Schedule Size = " + intconvert(count) + temp;

  return temp;
}

#endif