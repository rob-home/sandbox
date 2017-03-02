package secret.santa.mailer;

import java.util.ArrayList;

public class FluentList<T> extends ArrayList<T> {

    private static final long serialVersionUID = 1L;

    public static <T> FluentList<T> with() {
        return new FluentList<T>();
    }
    
    public FluentList<T> item(T item) {
        this.add(item);
        return this;
    }

}
