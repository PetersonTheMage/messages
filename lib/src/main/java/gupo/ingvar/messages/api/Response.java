package gupo.ingvar.messages.api;

import java.io.Serializable;

public interface Response<V extends Serializable> extends Serializable {
    V getContent();

    boolean isEmpty();

    default boolean isNotEmpty() {
        return !isEmpty();
    }

    boolean hasErrors();

    Iterable<Exception> getErrors();

    int getStatusCode();
}
