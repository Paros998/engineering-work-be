package pg.search.store.infrastructure.crypto.bitcoin;

public interface BitcoinCurrencyProvider {

    Float getBitcoinValueInUSD();

    Integer getBitcoinDailyBlockCount();

    Double getBitcoinNetworkTotalHashRate();

    Float getBitcoinBlockRevenue();

}