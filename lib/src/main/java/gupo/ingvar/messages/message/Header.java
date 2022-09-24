package gupo.ingvar.messages.message;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Header implements Serializable {
    private int code;
    private Collection<Exception> errors;

    public Header(int code) {
        this(code, null);
    }

    public boolean hasErrors() {
        return errors != null && !CollectionUtils.isEmpty(errors);
    }
}
