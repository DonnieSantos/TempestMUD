#ifndef SYSTEMLOG_H
#define SYSTEMLOG_H

class systemlog

{
  public:
    systemlog();
    ~systemlog() { }
    void add_log(client*, string);
    FILE* open_log_file();

  private:
    SYSTEMTIME logdate;
};

#endif