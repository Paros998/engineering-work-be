package pg.search.store.infrastructure.common.exception;

import org.springframework.core.NestedRuntimeException;

public class CurrencyNotExistException extends NestedRuntimeException {

    public CurrencyNotExistException(final String currency) {
        super(String.format("Currency %s not exists", currency));
    }
}
