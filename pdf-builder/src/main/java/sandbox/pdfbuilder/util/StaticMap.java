package sandbox.pdfbuilder.util;

import java.util.HashMap;

public class StaticMap<K, V> extends HashMap<K, V>
{
    public static <K, V> StaticMap<K, V> with()
    {
        return new StaticMap<K, V>();
    }
    
    public StaticMap<K, V> keyValue(K key, V value)
    {
        super.put(key, value);
        return this;
    }

}
