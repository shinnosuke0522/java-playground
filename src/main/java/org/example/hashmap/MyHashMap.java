package org.example.hashmap;

import java.util.LinkedList;
import java.util.Objects;

public class MyHashMap<K, V> {
    // 初期容量と負荷係数の定数
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private int size;
    private int threshold;

    // コリジョン対策: 同じインデックスに複数のエントリが存在できるようにする
    private LinkedList<Entry<K, V>>[] table;

    // エントリクラス：キーと値のペアを保持
    static class Entry<K, V> {
        final K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public MyHashMap() {
        table = new LinkedList[INITIAL_CAPACITY];
        threshold = (int) (INITIAL_CAPACITY * LOAD_FACTOR);
    }

    public V get(Object key) {
        int hash = hash(key);
        if (table[hash] == null) {
            return null;
        }
        // バケット内を検索してキーに対応するエントリを探す
        for (Entry<K, V> entry : table[hash]) {
            if (Objects.equals(entry.key, key)) {
                return entry.value;
            }
        }
        return null;
    }

    public void put(K key, V value) {
        int hash = hash(key);
        // バケットが空の場合、新しいLinkedListを作成
        if (table[hash] == null) {
            table[hash] = new LinkedList<>();
        }
        // キーが既に存在するかチェック
        for (Entry<K, V> entry : table[hash]) {
            if (Objects.equals(entry.key, key)) {
                entry.value = value;
            }
        }
        // キーが存在しない場合、新しいエントリを追加
        // コリジョン対策が発生しても、LinkedListに登録することで物理的な衝突を防ぐ
        table[hash].add(new Entry<>(key, value));
        size++;

        // サイズが閾値を超えた場合、キャパを増やす
        // 実際にはキャパを超えないように１つ余裕を持たせる
        if (size + 1 >= threshold) {
            upgradeCapacity();
        }
    }

    public void remove(Object key) {
        int hash = hash(key);

        // ハッシュにキーがなければ、Entryが存在しないため例外を投げる
        if (table[hash] == null) {
            throw new IllegalArgumentException();
        }

        // バケット内を検索してキーに対応するエントリを探し、削除
        for (Entry<K, V> entry : table[hash]) {
            if (Objects.equals(entry.key, key)) {
                table[hash].remove(entry);
                size--;

                // サイズが閾値の半分を下回った時、キャパを減らす
                if (size < table.length / 2) {
                    downgradeCapacity();
                }
            }
        }
        // コリジョンが発生していた場合
        // ハッシュにあたいがあってもキーが一致しない場合があり、その場合も例外を投げる
        throw new IllegalArgumentException();
    }

    /**
     * ハッシュ関数：キーをハッシュ化して配列のインデックスに変換
     */
    private int hash(Object key) {
        return (key == null) ? 0 : (key.hashCode() & 0x7FFFFFFF) % table.length;
    }

    /**
     * テーブルの容量を倍増し、既存のエントリを再配置
     */
    private void upgradeCapacity() {
        LinkedList<Entry<K, V>>[] oldTable = table;
        table = new LinkedList[oldTable.length * 2];
        threshold = (int) (table.length * LOAD_FACTOR);
        size = 0;
        // 古いテーブルのすべてのエントリを新しいテーブルに再配置
        for (LinkedList<Entry<K, V>> bucket : oldTable) {
            if (bucket != null) {
                bucket.forEach(this::assignEntry);
            }
        }
    }

    /**
     * テーブルの容量を倍増し、既存のエントリを再配置
     */
    private void downgradeCapacity() {
        LinkedList<Entry<K, V>>[] oldTable = table;
        table = new LinkedList[oldTable.length / 2];
        threshold = (int) (table.length * LOAD_FACTOR);

        // 古いテーブルのすべてのエントリを新しいテーブルに再配置
        for (LinkedList<Entry<K, V>> bucket : oldTable) {
            if (bucket != null) {
                bucket.forEach(this::assignEntry);
            }
        }
    }

    /**
     * Entryをテーブルに配置する
     * この時キーが衝突することを考慮しないので
     * 呼び出し側でする必要がある
     */
    private void assignEntry(Entry<K, V> entry) {
        table[hash(entry.key)].add(entry);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();

        var index = 0;
        String[] entryStrArr = new String[size];
        for (LinkedList<Entry<K, V>> record : table) {
            if (record == null || record.isEmpty()) {
                continue;
            }
            for (Entry<K, V> entry : record) {
                entryStrArr[index++] = entry.key + ":" + entry.value;
            }
        }
        return "{" + String.join(",", entryStrArr) + "}";

    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        System.out.println(map.get("one")); // 1
        System.out.println(map.get("two")); // 2
        System.out.println(map.get("one")); // null
        System.out.println(map.size()); // 1
        System.out.println(map);
    }
}

