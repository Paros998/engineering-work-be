package pg.search.store.domain.crypto.bitcoin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BitcoinProfitByCard {
    private UUID cardId;
    private String cardName;
    private String cardPhoto;
    private Double cardLowestPrice;
    private String offerUrl;
    private Double bitcoinHashRate;

    private Double dailyBitcoinMiningRate;
    private Double dailyBitcoinRevenue;
    private Double dailyProfit;

    private Double monthlyBitcoinMiningRate;
    private Double monthlyBitcoinRevenue;
    private Double monthlyProfit;

    private InvestmentReturnData investmentData;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class InvestmentReturnData {
        private Integer monthsToReturnInvestedMoney;
        private Map<String, Double> investmentProfit;
    }
}