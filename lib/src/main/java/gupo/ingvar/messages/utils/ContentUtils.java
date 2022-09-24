package gupo.ingvar.messages.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

import org.apache.commons.lang3.reflect.MethodUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ContentUtils {
    public static final Function<Object, Boolean> IS_NULL_FUNC = obj -> obj == null;

    @SuppressWarnings("unchecked")
    public static <T> Function<T, Boolean> isNullFunc() {
        return (Function<T, Boolean>) IS_NULL_FUNC;
    }

    public static <T> Function<T, Boolean> getIsEmptyFuncByValue(T value) {
        var isEmptyMeth = MethodUtils.getAccessibleMethod(value.getClass(), "isEmpty");

        if (isEmptyMeth != null) {
            return v -> {
                try {
                    return v == null || (Boolean) isEmptyMeth.invoke(v);
                } catch (IllegalAccessException | InvocationTargetException ignored) {
                    return false;
                }
            };
        } else {
            return isNullFunc();
        }
    }
}
