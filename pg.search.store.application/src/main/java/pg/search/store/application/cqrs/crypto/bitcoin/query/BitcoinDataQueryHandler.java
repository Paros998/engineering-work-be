package pg.search.store.application.cqrs.crypto.bitcoin.query;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.crypto.bitcoin.BitcoinData;
import pg.search.store.infrastructure.common.currency.CurrencyProvider;
import pg.search.store.infrastructure.crypto.bitcoin.BitcoinCurrencyProvider;
import pg.search.store.infrastructure.user.UserEntity;
import pg.search.store.infrastructure.user.UserService;

import java.util.Objects;

@Service
@AllArgsConstructor
public class BitcoinDataQueryHandler implements QueryHandler<BitcoinDataQuery, BitcoinData> {
    private final UserService userService;
    private final BitcoinCurrencyProvider bitcoinCurrencyProvider;
    private final CurrencyProvider currencyProvider;

    @Override
    public BitcoinData handle(final BitcoinDataQuery query) {
        double bitcoinValue = bitcoinCurrencyProvider.getBitcoinValueInUSD();

        if (query.getUserId() != null) {
            UserEntity user = userService.getUser(query.getUserId());

            if (!Objects.equals(user.getCurrency(), CurrencyProvider.DEFAULT_CURRENCY)) {
                bitcoinValue = currencyProvider.exchangePrice(CurrencyProvider.DEFAULT_CURRENCY, user.getCurrency(), bitcoinValue);
            }
        }

        return new BitcoinData(bitcoinValue, bitcoinCurrencyProvider.getBitcoinDailyBlockCount(),
                bitcoinCurrencyProvider.getBitcoinNetworkTotalHashRate(), bitcoinCurrencyProvider.getBitcoinBlockRevenue());
    }
}
