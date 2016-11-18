package secret.santa;

import java.util.LinkedHashMap;

import com.google.gson.Gson;

public class FluentMap<K, V> extends LinkedHashMap<K, V>
{
    private static final long serialVersionUID = 1L;

    public static <K, V> FluentMap<K, V> with() {
        return new FluentMap<K, V>();
    }
    
    public static FluentMap<String, Object> withObject() {
        return FluentMap.<String, Object>with();
    }
    
    public FluentMap<K, V> keyValue(K key, V value) {
        this.put(key, value);
        return this;
    }
    
    public String toJson() {
        return new Gson().toJson(this);
    }
}
