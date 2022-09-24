package gupo.ingvar.messages.message;

import java.io.Serializable;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Content<V extends Serializable> implements Serializable {
    @Getter
    @Setter
    private V value;

    @NonNull
    private Function<V, Boolean> isEmptyFunc = v -> v == null;

    public boolean isEmpty() {
        return isEmptyFunc.apply(value);
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }
}
