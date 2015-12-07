package tempest.primitives;
import java.util.Iterator;
import java.lang.Iterable;
import tempest.data.Data;

public class HashList implements Iterable <Data>

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
      if (current.key.equalsIgnoreCase(key)) return true;
      current = current.next;
    }

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Data get(String key)

  {
    return get(new MudString(key));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Data get(MudString key)

  {
    int h = key.hashCode();
    if (h < 0) h = -h;
    h = h % buckets.length;

    Link current = buckets[h];

    while (current != null)

    {
      if (current.key.equalsIgnoreCase(key)) return current.value;
      current = current.next;
    }

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean add(MudString key, Data value)

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
      newLink.nextLinkedList = head;
      head.prevLinkedList = newLink;
    }

    head = newLink;
    size++;

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean remove(MudString key)

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

        Link prev = current.prevLinkedList;
        Link next = current.nextLinkedList;

        if (prev != null) prev.nextLinkedList = next;
        if (next != null) next.prevLinkedList = prev;
        if (current == head) head = next;

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
    public MudString key;
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