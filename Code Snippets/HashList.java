import java.util.Iterator;

public class HashList

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private Link[] buckets;
  private int size;
  private Link head;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public HashList(int bucketsLength)

  {
    this.size = 0;
    this.buckets = new Link[bucketsLength];
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean containsKey(String key)

  {
    int h = key.hashCode();
    if (h < 0) h = -h;
    h = h % buckets.length;

    Link current = buckets[h];

    while (current != null)

    {
      if (current.key.equals(key)) return true;
      current = current.next;
    }

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Data get(String key)

  {
    int h = key.hashCode();
    if (h < 0) h = -h;
    h = h % buckets.length;

    Link current = buckets[h];

    while (current != null)

    {
      if (current.key.equals(key)) return current.value;
      current = current.next;
    }

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean add(String key, Data value)

  {
    int h = key.hashCode();
    if (h < 0) h = -h;
    h = h % buckets.length;

    Link current = buckets[h];

    while (current != null)

    {
      if (current.key.equals(key)) return false;
      current = current.next;
    }

    Link newLink = new Link();
    newLink.key = key;
    newLink.value = value;
    newLink.next = buckets[h];
    buckets[h] = newLink;

    if (head != null)

    {
      head.prevLinkedList = newLink;
      newLink.nextLinkedList = head;
      head = newLink;
    }

    else head = newLink;

    size++;

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean remove(String key)

  {
    int h = key.hashCode();
    if (h < 0) h = -h;
    h = h % buckets.length;

    Link current = buckets[h];
    Link previous = null;

    while (current != null)

    {
      if (current.key.equals(key))

      {
        if (previous == null) buckets[h] = current.next;
        else previous.next = current.next;
        size--;

        if (head == current)

        {
          head = head.next;
          if (head != null) head.prevLinkedList = null;
        }

        else current.prevLinkedList.nextLinkedList = current.nextLinkedList;

        return true;
      }

      previous = current;
      current = current.next;
    }

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void clear()

  {
    for (int i=0; i<buckets.length; i++)
      buckets[i] = null;

    head = null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int size()

  {
    return size;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Iterator <Data> iterator()

  {
    return new HashIterator(head);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private class Link

  {
    public Data value;
    public String key;
    public Link next;
    public Link nextLinkedList;
    public Link prevLinkedList;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private class HashIterator implements Iterator <Data>

  {
    private Link walker;

    public HashIterator(Link head)

    {
      walker = head;
    }

    public boolean hasNext()

    {
      return walker != null;
    }

    public Data next()

    {
      Data value = walker.value;
      walker = walker.nextLinkedList;
      return value;
    }

    public void remove() { }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}