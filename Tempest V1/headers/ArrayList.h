#ifndef ARRAYLIST_H
#define ARRAYLIST_H

template <class Type>
class ArrayList

{
  public:
    ArrayList();
    ArrayList(int);

    int   size();
    void  clear();
    int   isEmpty();
    void  ensureCapacity(int);
    Type* toArray();
    Type  get(int);
    Type  remove(int);
    int   indexOf(Type);
    void  add(Type);
    int   contains(Type);

  private:
    Type* list;
    int   length;
    int   numElements;
};

template <class Type>
ArrayList<Type>::ArrayList()

{
  length = 10;
  numElements = 0;
  list = new Type[10];
}

template <class Type>
ArrayList<Type>::ArrayList(int i)

{
  length = i;
  numElements = 0;
  list = new Type[i];
}

template <class Type>
void ArrayList<Type>::clear()

{
  for (int i=0; i<length; i++)
    list[i] = NULL;

  length = 0;
  numElements = 0;
}

template <class Type>
int ArrayList<Type>::isEmpty()

{
  if (numElements == 0)
    return 1;

  return 0;
}

template<class Type>
void ArrayList<Type>::ensureCapacity(int i)

{
  if (i > length)

  {
    int k = (length * 3) / 2 + 1;

    if (k < i) k = i;

    Type* newList = new Type[k];

    for (int j=0; j<numElements; j++)
      newList[j] = list[j];

    delete [] list;
    list = newList;
    length = k;
  }
}

template<class Type>
Type* ArrayList<Type>::toArray()

{
  return list;
}

template<class Type>
Type ArrayList<Type>::get(int i)

{
  return list[i];
}

template<class Type>
Type ArrayList<Type>::remove(int i)

{
  Type element = list[i];

  for (int j=i; j<numElements; j++)
    if (j+1 <= numElements)
      list[j] = list[j+1]

  numElements--;
  return element;
}

template<class Type>
void ArrayList<Type>::add(Type element)

{
  ensureCapacity(numElements + 1);
  list[numElements++] = element;
}

template<class Type>
int ArrayList<Type>::contains(Type element)

{
  for (int i=0; i<numElements; i++)
    if (list[i] == element)
      return 1;

  return 0;
}

template<class Type>
int ArrayList<Type>::indexOf(Type element)

{
  for (int i=0; i<numElements; i++)
    if (list[i] == element)
      return i;

  return -1;
}

template<class Type>
int ArrayList<Type>::size()

{
  return numElements;
}

#endif