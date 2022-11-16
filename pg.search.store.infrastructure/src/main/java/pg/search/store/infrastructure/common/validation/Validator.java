package pg.search.store.infrastructure.common.validation;

import org.springframework.core.NestedRuntimeException;

public interface Validator<T> {
    void validate(T object) throws NestedRuntimeException;
}
