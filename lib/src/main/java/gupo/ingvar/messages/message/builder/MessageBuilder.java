package gupo.ingvar.messages.message.builder;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;

import gupo.ingvar.messages.message.Content;
import gupo.ingvar.messages.message.Header;
import gupo.ingvar.messages.message.Message;
import gupo.ingvar.messages.utils.ContentUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageBuilder<C extends Serializable> {
    private int statusCode;
    private Collection<Exception> errors;
    private C content;
    private Function<C, Boolean> isEmptyFunc;

    public static <V extends Serializable> MessageBuilder<V> builder() {
        return new MessageBuilder<>();
    }

    public MessageBuilder<C> statusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public MessageBuilder<C> errors(Collection<Exception> errors) {
        this.errors = errors;
        return this;
    }

    public MessageBuilder<C> addErrors(Collection<Exception> errors) {
        if (CollectionUtils.isEmpty(errors)) {
            return this;
        }

        if (CollectionUtils.isEmpty(this.errors)) {
            this.errors = errors;
        } else {
            this.errors = Stream.concat(this.errors.stream(), errors.stream()).collect(Collectors.toSet());
        }

        return this;
    }

    public MessageBuilder<C> addError(Exception error) {
        if (error == null) {
            return this;
        }

        if (CollectionUtils.isEmpty(this.errors)) {
            this.errors = Set.of(error);
        } else {
            this.errors = Stream.concat(errors.stream(), Stream.of(error)).collect(Collectors.toSet());
        }

        return this;
    }

    public MessageBuilder<C> content(C content) {
        this.content = content;
        return this;
    }

    public MessageBuilder<C> isEmptyFunc(Function<C, Boolean> isEmptyFunc) {
        this.isEmptyFunc = isEmptyFunc;
        return this;
    }

    public Message<C> build() {
        return new Message<>(buildHead(), buildBody());
    }

    private Header buildHead() {
        return new Header(statusCode, errors);
    }

    private Content<C> buildBody() {
        return new Content<C>(content, getContentIsEmptyFunc());
    }

    private Function<C, Boolean> getContentIsEmptyFunc() {
        if (isEmptyFunc != null) {
            return isEmptyFunc;
        }

        return ContentUtils.isNullFunc();
    }
}
