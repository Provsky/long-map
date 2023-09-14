package de.comparus.opensource.longmap;


import org.junit.*;

import java.util.Random;

import static org.junit.Assert.*;


public class LongMapImplTest {

  static LongMapImpl<String> map;

  @Before
  @Test
  public void initMapAndTestPut() {
    map = new LongMapImpl<>();
    assertNull(map.put(0, "a"));
    assertNull(map.put(1, "b"));
    assertNull(map.put(Long.MAX_VALUE, "c"));
    assertNull(map.put(Long.MIN_VALUE, "d"));
    assertNull(map.put(-18, "e"));

    assertEquals("e", map.put(-18, "f"));
    assertEquals("b", map.put(1, "j"));
    assertEquals("c", map.put(Long.MAX_VALUE, "k"));
    assertEquals("d", map.put(Long.MIN_VALUE, "l"));
  }


  @Test
  public void testGet() {
    assertEquals("a", map.get(0));
    assertEquals("j", map.get(1));
    assertEquals("k", map.get(Long.MAX_VALUE));
    assertEquals("l", map.get(Long.MIN_VALUE));

    assertNull(map.get(56));
    assertNull(map.get(3));
    assertNull(map.get(345234));
    assertNull(map.get(-1443));
  }

  @Test
  public void testRemove() {
    assertEquals("a", map.remove(0));
    assertEquals("f", map.remove(-18));
    assertEquals("k", map.remove(Long.MAX_VALUE));

    assertNull(map.remove(56));
    assertNull(map.remove(12312));
    assertNull(map.remove(4));
    assertNull(map.remove(-14144));
  }

  @Test
  public void testIsEmpty() {
    assertFalse(map.isEmpty());

    map.clear();
    assertTrue(map.isEmpty());

    map.put(0, "a");
    assertFalse(map.isEmpty());
  }

  @Test
  public void testContainsKey() {
    assertTrue(map.containsKey(1));
    assertTrue(map.containsKey(-18));
    assertTrue(map.containsKey(Long.MAX_VALUE));

    assertFalse(map.containsKey(23));
    assertFalse(map.containsKey(153));
    assertFalse(map.containsKey(36512324));
    assertFalse(map.containsKey(-12451542));
  }

  @Test
  public void testContainsValue() {
    assertTrue(map.containsValue("a"));
    assertTrue(map.containsValue("j"));
    assertTrue(map.containsValue("f"));
    assertTrue(map.containsValue("l"));

    assertFalse(map.containsValue("aa"));
    assertFalse(map.containsValue(""));
    assertFalse(map.containsValue("qq"));
    assertFalse(map.containsValue("x"));
  }

  @Test
  public void testGetKeys() {
    map = new LongMapImpl<>();
    assertArrayEquals(null, map.keys());

    map.put(0, "a");
    map.put(-50, "b");
    map.put(228, "c");
    long[] array = {0, -50, 228};
    assertArrayEquals(array, map.keys());
  }

  @Test
  public void testGetValues() {
    map = new LongMapImpl<>();
    assertArrayEquals(null, map.values());

    map.put(0, "a");
    map.put(-50, "b");
    map.put(228, "c");
    String[] array = {"a", "b", "c"};
    assertArrayEquals(array, map.values());
  }

  @Test
  public void testSize() {
    assertEquals(5, map.size());
    map.clear();
    assertEquals(0, map.size());
    map.put(0, "a");
    assertEquals(1, map.size());
    map.put(1234, "qwerty");
    assertEquals(2, map.size());
  }

  @Test
  public void testMediumSeesaw() {
    map = new LongMapImpl<>();

    int n = new Random().nextInt(3000);
    for (int i = 0; i < n; i++) {
      map.put(i, "a");
    }
    assertEquals(n, map.size());

    for (int i = 0; i < n; i++) {
      map.get(i);
    }
    for (int i = 0; i < n; i++) {
      map.remove(i);
    }
    assertEquals(0, map.size());
  }

  @Test
  public void testLargeSeesaw() {
    map = new LongMapImpl<>();

    int n = 1000000;
    for (int i = 0; i < n; i++) {
      map.put(i, "a");
    }
    assertEquals(n, map.size());

    for (int i = 0; i < n; i++) {
      map.get(i);
    }
    for (int i = 0; i < n; i++) {
      map.remove(i);
    }
    assertEquals(0, map.size());
  }
}
