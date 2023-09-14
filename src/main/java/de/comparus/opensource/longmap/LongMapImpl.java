package de.comparus.opensource.longmap;

import java.lang.reflect.Array;

public class LongMapImpl<V> implements LongMap<V> {

  private final int DEFAULT_TABLE_LENGTH = 16;
  private final float MAX_LOAD_FACTOR = 0.75f;

  private int currentMapSize = 0;
  private Entry[] table;

  public LongMapImpl() {
    table = new Entry[DEFAULT_TABLE_LENGTH];
  }
  public V put(long key, V value) {

    int hash = Math.abs((int) (key % table.length));
    int probe = 0;

    while (table[hash] != null) {
      if (table[hash].key == key) {
        V oldValue = (V) table[hash].value;
        table[hash].value = value;
        return oldValue;
      } else {
        probe++;
        hash += probe;
        if (hash >= table.length) {
          hash = 0;
          probe = 0;
        }
      }
    }

    table[hash] = new Entry(key, value);
    currentMapSize++;

    if (currentMapSize > (table.length * MAX_LOAD_FACTOR)) {
      riseTable();
    }

    return null;
  }

  public V get(long key) {
    int keyPosition = searchForKey(key);
    return keyPosition == -1 ? null : (V) table[keyPosition].value;
  }

  public V remove(long key) {
    int keyPosition = searchForKey(key);

    if (keyPosition == -1) {
      return null;
    }

    V result = (V) table[keyPosition].value;
    table[keyPosition] = null;
    currentMapSize--;

    return result;
  }

  public boolean isEmpty() {
    return currentMapSize == 0;
  }

  public boolean containsKey(long key) {
    return searchForKey(key) != -1;
  }

  public boolean containsValue(V value) {
    for (int i = 0; i < table.length; i++) {
      if (table[i] != null && table[i].value.equals(value)) {
        return true;
      }
    }
    return false;

  }

  public long[] keys() {
    if (currentMapSize > 0) {
      long[] keys = new long[currentMapSize];
      for (int i = 0, j = 0; i < table.length; i++) {
        if (table[i] != null) {
          keys[j] = table[i].key;
          j++;
        }
      }
      return keys;
    }
    return null;
  }

  public V[] values() {
    if (currentMapSize > 0) {
      V[] values = (V[]) new Object[currentMapSize];
      for (int i = 0, j = 0; i < table.length; i++) {
        if (table[i] != null) {
          values[j] = (V) (table[i].value);
          j++;
        }
      }
      V[] result = (V[]) Array.newInstance(values[0].getClass(), currentMapSize);
      System.arraycopy(values, 0, result, 0, currentMapSize);
      return result;
    }
    return null;
  }

  public long size() {
    return currentMapSize;
  }

  public void clear() {
    table = new Entry[DEFAULT_TABLE_LENGTH];
    currentMapSize = 0;
  }

  private int searchForKey(long key) {
    int index = Math.abs((int) (key % table.length));
    int probesCounter = 0;
    while (table[index] == null || table[index].key != key) {
      probesCounter++;
      index++;
      if (index >= table.length) {
        index = 0;
      }
      if (probesCounter == table.length) {
        return -1;
      }
    }
    return index;
  }

  private void riseTable() {
    int newTableSize = 2 * table.length;
    Entry[] oldTable = table;
    table = new Entry[newTableSize];
    System.arraycopy(oldTable, 0, table, 0, oldTable.length);
  }

  static class Entry<V> {

    long key;
    V value;

    private Entry(long key, V value) {
      this.key = key;
      this.value = value;
    }
  }
}
