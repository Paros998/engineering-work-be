package pg.search.store.domain.product.card;

import java.util.Arrays;
import java.util.List;

public enum PciType {
    PCI_2X16,
    PCI_3X16,
    PCI_4X16;

    public static List<String> toList() {
        return Arrays.stream(PciType.values()).map(PciType::name).toList();
    }

    public static boolean isValidType(final String data) {
        return toList().contains(data);
    }
}
