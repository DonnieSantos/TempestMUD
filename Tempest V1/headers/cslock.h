#ifndef CSLOCK_H
#define CSLOCK_H
#include "windows.h"

class cslock

{
  public:

    cslock(CRITICAL_SECTION *cs)

    {
      CS = cs;
      EnterCriticalSection(CS);
    }

    ~cslock()

    {
      LeaveCriticalSection(CS);
    }

  private:
    CRITICAL_SECTION *CS;
};

#endif