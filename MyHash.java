

import java.io.Serializable;
import java.util.ArrayList;

public class MyHash implements Serializable {

    static class Entry implements Serializable {

        final String key;
        int value;
        Entry next;
        int hash;

        public Entry(String k, int v, Entry n, int h) {
            key = k;
            value = v;
            next = n;
            hash = h;
        }
    }

    Entry[] tab;
    int count = 0;
    int totalWordCount = 0;

    public MyHash() {
        tab = new Entry[64];
    }

    public void put(String key, int value) {
        totalWordCount++;

        int h = key.hashCode();
        Entry[] t = tab;
        int i = h & (t.length - 1);

        for (Entry e = t[i]; e != null; e = e.next) {
            if (e.hash == h && key.equals(e.key)) {
                e.value = e.value + 1;
                return;
            }
        }

        Entry p = new Entry(key, value, t[i], h);
        t[i] = p;
        int n = t.length;
        int c = ++count;

        if ((c / (float) t.length) < 0.75) {
            return;
        }
        
        int newN = n << 1;
        Entry[] newTab = new Entry[newN];

        for (int q = 0; q < n; ++q) {
            Entry e;
            while ((e = t[q]) != null) {
                t[q] = e.next;
                int j = e.hash & (newN - 1);
                e.next = newTab[j];
                newTab[j] = e;
            }
        }
        tab = newTab;
    }

    public void printHash() {
        int size = tab.length;
        Entry e;
        Entry[] t = tab;
        for (int i = 0; i < size; i++) {

            if (t[i] != null) {
                e = t[i];
                System.out.println("--" + e.key + "--  :" + e.value);
                while (e.next != null) {
                    e = e.next;
                    System.out.println("--" + e.key + "-- :" + e.value);
                }
            }
        }

        System.out.println("Your count is equal to:" + count);
    }

    public WordValuePair getWordValuePair() {
        WordValuePair pairs = new WordValuePair();
        ArrayList<String> words = new ArrayList<>();
        ArrayList<Integer> frequency = new ArrayList<>();

        int size = tab.length;
        Entry e;
        Entry[] t = tab;
        for (int i = 0; i < size; i++) {

            if (t[i] != null) {
                e = t[i];
                words.add(e.key);
                frequency.add(e.value);

                while (e.next != null) {
                    e = e.next;
                    words.add(e.key);
                    frequency.add(e.value);
                }
            }
        }
        pairs.setFrequencies(frequency);
        pairs.setWords(words);

        return pairs;
    }

    public int getVal(String key) {
        int size = tab.length;
        Entry e;
        Entry[] t = tab;
        for (int i = 0; i < size; i++) {

            if (t[i] != null) {
                e = t[i];
                if (e.key.equals(key)) {
                    return e.value;
                }
                while (e.next != null) {
                    e = e.next;
                    if (e.key.equals(key)) {
                        return e.value;
                    }
                }
            }

        }
        return 0;
    }

    public void clearHash() {
        tab = new Entry[64];
    }

}
