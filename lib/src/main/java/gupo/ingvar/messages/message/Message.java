package gupo.ingvar.messages.message;

import java.io.Serializable;
import java.util.Optional;

import gupo.ingvar.messages.api.Response;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Message<C extends Serializable> implements Response<C> {
    @NonNull
    private Header head;
    private Optional<Content<C>> body;

    public Message(Header head, Content<C> body) {
        this.head = head;
        this.body = Optional.ofNullable(body);
    }

    public Message(Header head) {
        this.head = head;
        this.body = Optional.ofNullable(null);
    }

    public void setBody(Content<C> body) {
        this.body = Optional.ofNullable(body);
    }

    @Override
    public int getStatusCode() {
        return head.getCode();
    }

    @Override
    public boolean hasErrors() {
        return head.hasErrors();
    }

    @Override
    public Iterable<Exception> getErrors() {
        return head.getErrors();
    }

    @Override
    public C getContent() {
        if (body.isEmpty()) {
            return null;
        }

        return body.get().getValue();
    }

    @Override
    public boolean isEmpty() {
        return body.isEmpty() || body.get().isEmpty();
    }
}
