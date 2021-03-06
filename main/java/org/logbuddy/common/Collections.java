package org.logbuddy.common;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Collections {
  public static List<Object> arrayToList(Object array) {
    int length = Array.getLength(array);
    List<Object> list = new ArrayList<>(length);
    for (int i = 0; i < length; i++) {
      list.add(Array.get(array, i));
    }
    return list;
  }

  public static <E> E removeAny(Iterable<E> iterable) {
    Iterator<E> iterator = iterable.iterator();
    E element = iterator.next();
    iterator.remove();
    return element;
  }

  public static <E> Stream<E> stream(Iterable<E> iterable) {
    return StreamSupport.stream(iterable.spliterator(), false);
  }
}
