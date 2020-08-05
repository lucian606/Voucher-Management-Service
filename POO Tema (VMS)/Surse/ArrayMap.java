import java.util.*;

public class ArrayMap<K, V> extends AbstractMap<K, V> {
    protected Vector<Entry<K, V>> entries;

    public class Entry<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;

        public Entry (K key, V value) {
            this.key=key;
            this.value=value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return value;
        }

        @Override
        public String toString() {
            return "KEY: "+key+" VALUE: "+value+"\n";
        }

        @Override
        public boolean equals(Object o) {
            if(o == this) {
                return true;
            }
            if(!(o instanceof Entry)) {
                return false;
            }
            Entry<V, K> e=(Entry) o;
            return (key.equals(e.getKey()) && value.equals(e.getValue()));
        }

        public int hashCode() {
            return super.hashCode();
        }
    }

    public ArrayMap() {
        entries=new Vector<>();
    }

    @Override
    public V put(K key, V value) {
        for(Entry entry : entries) {
            if(entry.getKey() == key) {
                return value;
            }
        }
        entries.add(new Entry<>(key, value));
        return value;
    }

    @Override
    public boolean containsKey(Object key) {
        for(Entry entry : entries) {
            if(entry.getKey().equals(key))
                return true;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        for(Entry entry : entries) {
            if(entry.getKey().equals(key))
                return (V) entry.getValue();
        }
        return null;
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K,V>> set=new HashSet<>();
        int i;
        for(i=0; i < entries.size();i++) {
            set.add(entries.get(i));
        }
        return set;
    }

    public Vector<Entry<K, V>> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        String rez="";
        for(Entry entry : entries) {
            rez=rez+entry.getValue()+" ";
        }
        return rez;
    }
}
