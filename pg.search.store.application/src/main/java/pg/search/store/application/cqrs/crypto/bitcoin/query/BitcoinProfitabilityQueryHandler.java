package pg.search.store.application.cqrs.crypto.bitcoin.query;

import lombok.AllArgsConstructor;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.crypto.bitcoin.BitcoinProfitByCard;
import pg.search.store.infrastructure.common.CommonData;
import pg.search.store.infrastructure.common.currency.CurrencyProvider;
import pg.search.store.infrastructure.common.pageable.PageMapper;
import pg.search.store.infrastructure.crypto.bitcoin.BitcoinCurrencyProvider;
import pg.search.store.infrastructure.product.ProductService;
import pg.search.store.infrastructure.product.card.CardEntity;
import pg.search.store.infrastructure.product.card.CardService;
import pg.search.store.infrastructure.store.offer.OfferEntity;
import pg.search.store.infrastructure.user.UserEntity;
import pg.search.store.infrastructure.user.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BitcoinProfitabilityQueryHandler implements QueryHandler<BitcoinProfitabilityQuery, PageResponse<BitcoinProfitByCard>> {
    private final UserService userService;
    private final CardService cardService;
    private final ProductService productService;
    private final CurrencyProvider currencyProvider;
    private final BitcoinCurrencyProvider bitcoinCurrencyProvider;

    @Override
    public PageResponse<BitcoinProfitByCard> handle(final BitcoinProfitabilityQuery query) {
        final UserEntity user = query.getUserId() == null ? null : userService.getUser(query.getUserId());

        final var cardsResponse = cardService.getCardsApplicableForBitcoinMining(PageMapper.toSpringPageRequest(query.getPageRequest()), query.getQueryString());

        String currency = user != null ? user.getCurrency() : CurrencyProvider.DEFAULT_CURRENCY;

        Double exchangeRate = Objects.equals(currency, CurrencyProvider.DEFAULT_CURRENCY) ? 1.0 :
                Double.valueOf(currencyProvider.getExchangeRate(CurrencyProvider.DEFAULT_CURRENCY, currency));

        return PageMapper.toPageResponse(cardsResponse,
                card -> toBitcoinProfitByCard(card, query.getElectricityCost(), currency, exchangeRate)
        );
    }

    private BitcoinProfitByCard toBitcoinProfitByCard(final CardEntity card, final Double electricityCost, final String currency,
                                                      final Double bitcoinValueExchangeRate) {
        final Pair<Double, String> lowestOffer = card.getOnlineOfferList().stream()
                .min(Comparator.comparingDouble(OfferEntity::getPrice))
                .map(entity -> Pair.of(currencyProvider.exchangePrice(entity.getCurrency(), currency, entity.getPrice()), entity.getOfferWebsite()))
                .orElse(Pair.of(0d, ""));

        final Double lowestPrice = lowestOffer.getFirst();

        final Double dailyBitcoinMiningRate = (card.getBitcoinHashRate() / bitcoinCurrencyProvider.getBitcoinNetworkTotalHashRate())
                * bitcoinCurrencyProvider.getBitcoinDailyBlockCount() * bitcoinCurrencyProvider.getBitcoinBlockRevenue();

        final Double dailyEnergyCosts = card.getPowerConsumption() * 24 / 1000.0 * electricityCost;

        final Double dailyBitcoinRevenue = (dailyBitcoinMiningRate * bitcoinCurrencyProvider.getBitcoinValueInUSD()) * bitcoinValueExchangeRate;

        final double dailyProfit = dailyBitcoinRevenue - dailyEnergyCosts;

        final BitcoinProfitByCard.InvestmentReturnData returnData = (lowestPrice == 0d || dailyProfit <= 0d) ? null :
                calculateInvestment(lowestPrice,
                        dailyProfit);

        return BitcoinProfitByCard.builder()
                .cardId(card.getProductId())
                .cardName(card.getTitle())
                .cardPhoto(productService.getProductPhoto(card))
                .cardLowestPrice(lowestPrice)
                .offerUrl(lowestOffer.getSecond())
                .bitcoinHashRate(Double.valueOf(card.getBitcoinHashRate()))

                .dailyBitcoinMiningRate(dailyBitcoinMiningRate)
                .dailyBitcoinRevenue(dailyBitcoinRevenue)
                .dailyProfit(dailyProfit)

                .monthlyBitcoinMiningRate(dailyBitcoinMiningRate * 31)
                .monthlyBitcoinRevenue(dailyBitcoinRevenue * 31)
                .monthlyProfit(dailyProfit * 31)

                .investmentData(returnData)

                .build();
    }

    private BitcoinProfitByCard.InvestmentReturnData calculateInvestment(Double lowestOffer, double dailyProfit) {
        Map<String, Double> investmentProfit = new LinkedHashMap<>();

        LocalDate today = LocalDate.now();
        double moneyDifference = -lowestOffer;
        LocalDate lastDate;
        int months = 0;

        investmentProfit.put(today.format(DateTimeFormatter.ofPattern(CommonData.DATE_FORMAT)), moneyDifference);

        do {
            lastDate = today;
            today = today.plusMonths(1);
            moneyDifference += (dailyProfit * ChronoUnit.DAYS.between(lastDate, today));
            investmentProfit.put(today.format(DateTimeFormatter.ofPattern(CommonData.DATE_FORMAT)), moneyDifference);
            months++;
            if (months >= 12 * 5)
                return null;
        } while (moneyDifference < 0);


        for (int i = 0; i < 12; i++) {
            lastDate = today;
            today = today.plusMonths(1);
            moneyDifference += (dailyProfit * ChronoUnit.DAYS.between(lastDate, today));
            investmentProfit.put(today.format(DateTimeFormatter.ofPattern(CommonData.DATE_FORMAT)), moneyDifference);
        }

        return BitcoinProfitByCard.InvestmentReturnData.builder()
                .monthsToReturnInvestedMoney(months)
                .investmentProfit(investmentProfit)
                .build();
    }
}
