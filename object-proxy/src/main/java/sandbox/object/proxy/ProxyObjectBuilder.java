package sandbox.object.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

@Log
@Data(staticConstructor = "forClass")
@Accessors(fluent = true)
public class ProxyObjectBuilder<T> {
    private final Class<T> forClass;
    private HashMap<String, Object> mock = new HashMap<String, Object>();

    public ProxyObjectBuilder<T> mock(final String method, final Object returnValue) {
        mock.put(method, returnValue);
        return this;
    }

    public T build() {
        final ProxyFactory f = new ProxyFactory();
        f.setSuperclass(forClass);

        f.setFilter(new MethodFilter() {
            @Override
            public boolean isHandled(final Method m) {
                return !EXCLUDE_METHOD.contains(m.getName());
            }
        });

        final Class<?> proxyClass = f.createClass();

        final MethodHandler mi = new MethodHandler() {
            @Override
            public Object invoke(final Object self, final Method m, final Method proceed, final Object[] args) {

                final Debug debug = Debug.method(m);

                try {
                    String mockKey = mock.containsKey(m.getName()) ? m.getName() : "";
                    mockKey = mock.containsKey(m.getDeclaringClass().getSimpleName() + "." + m.getName()) ? m.getDeclaringClass().getSimpleName()
                            + "." + m.getName() : mockKey;

                    Object ret = mock.getOrDefault(mockKey, proceed.invoke(self, args));

                    final Class<?> returnClass = proceed.getReturnType();

                    debug.returnClass(returnClass);

                    if (StringUtils.isBlank(mockKey)) {
                        if (ret == null && !returnClass.equals(Void.TYPE)) {
                            if (DEFAULT_VALUE.containsKey(returnClass)) {
                                ret = DEFAULT_VALUE.get(returnClass);
                                debug.isDefault(true);
                            } else if (Modifier.isFinal(returnClass.getModifiers())) {
                                debug.isFinal(true);
                            } else {
                                ret = ProxyObjectBuilder.forClass(proceed.getReturnType()).mock(mock).build();
                            }
                        }

                        if (ret == null && !returnClass.equals(Void.TYPE) && !Modifier.isFinal(returnClass.getModifiers())) {
                            ret = ProxyObjectBuilder.forClass(proceed.getReturnType()).mock(mock).build();
                        }
                    }

                    debug.returnValue(ret);

                    return ret;

                } catch (final Exception e) {
                    debug.error(e);
                } finally {
                    log.info(debug.toString());
                }

                return null;
            }
        };

        try {
            final Object proxyInstance = proxyClass.newInstance();
            ((Proxy) proxyInstance).setHandler(mi);

            return forClass.cast(proxyInstance);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }

    }

    private static final ArrayList<String> EXCLUDE_METHOD = Lists.<String> newArrayList("finalize", "hashCode", "toString");

    private static final Map<Class<?>, Object> DEFAULT_VALUE = ImmutableMap.<Class<?>, Object> builder().put(Boolean.class, Boolean.TRUE).put(
            Character.class, new Character('\u0000')).put(Byte.class, 0).put(Short.class, 0).put(Integer.class, 0).put(Long.class, 0L).put(
            Double.class, 0.0).put(BigDecimal.class, new BigDecimal(0)).put(BigInteger.class, new BigInteger("0")).put(String.class, "").build();

    @Data(staticConstructor = "method")
    private static class Debug {
        private final Method method;
        private Class<?> returnClass;
        private Object returnValue;
        private Throwable error;
        private boolean isDefault = false;
        private boolean isFinal = false;
    }
}
