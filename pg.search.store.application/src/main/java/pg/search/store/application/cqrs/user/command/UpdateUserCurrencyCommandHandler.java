package pg.search.store.application.cqrs.user.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.common.currency.CurrencyProvider;
import pg.search.store.infrastructure.user.UserService;

@Service
@AllArgsConstructor
public class UpdateUserCurrencyCommandHandler implements CommandHandler<UpdateUserCurrencyCommand, Void> {
    private final UserService userService;
    private final CurrencyProvider currencyProvider;

    @Override
    public Void handle(final UpdateUserCurrencyCommand command) {
        String currency = command.getCurrency();

        if (currencyProvider.isInvalidCurrency(currency))
            throw new IllegalArgumentException(String.format("Currency %s is Invalid", currency));

        userService.updateUserCurrency(command.getUserId(), currency);
        return null;
    }
}
