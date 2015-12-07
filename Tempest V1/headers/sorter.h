#ifndef SORTER_H
#define SORTER_H

class sorter

{
  public:
    template <class type> void bubble_sort(type*, int, int);
    template <class type> void merge_sort(type*, int, int);
    template <class type> void mergesort(type*, int, int);
    template <class type> void merge(type*, int, int, int);
};

template <class type>
void sorter::bubble_sort(type *List, int start, int length)

{
  int stop = start + length - 1;

  for (int b=start; b<stop; b++)
  for (int i=start; i<stop; i++)
  if (List[i] > List[i+1])

  {
    type temp = List[i];
    List[i] = List[i+1];
    List[i+1] = temp;
  }
}

template <class type>
void sorter::merge_sort(type *List, int start, int length)

{
  int stop = start + length - 1;
  mergesort(List, start, stop);
}

template <class type>
void sorter::mergesort(type *List, int x, int y)

{
  if (x < y)

  {
    int z = (x + y) / 2;
    mergesort(List, x, z);
    mergesort(List, z+1, y);
    merge(List, x, z, y);
  }
}

template <class type>
void sorter::merge(type *List, int x, int z, int y)

{
  int size = y-x+1;
  type *templist = new type [size];
  int count=0;

  int left = x;
  int right = z+1;

  for (int i=x; i<size+x; i++)

  {
    if (right > y) {
      templist[count] = List[left];
      left++; }

    else if (left > z) {
      templist[count] = List[right];
      right++; }

    else if (List[left] <= List[right]) {
      templist[count] = List[left];
      left++; }

    else {
      templist[count] = List[right];
      right++; }

    count++;
  }

  count = 0;

  for (int i=x; i<x+size; i++) {
    List[i] = templist[count];
    count++; }

  delete [] templist;
}

#endif