package pg.search.store.application.cqrs.product.command.laptop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.command.Command;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
public class AddLaptopCommand implements Command<UUID> {
    private String title;
    private String producentCode;
    private String producentSite;
    private String dateOfProduction;

    private UUID gpuCardId;
    private UUID processorId;

    private Float ramAmount;
    private String ramType;
    private Float ramClock;
    private Map<String, Integer> hddDrives;
    private Map<String, Integer> ssdDrives;
    private String chipset;
    private Boolean diskDrive;
    private String sound;
    private List<String> connectivity;
    private Map<String, Integer> leftPanelConnectors;
    private Map<String, Integer> rightPanelConnectors;
    private String psu;
    private Integer psuPower;
    private String psuEfficiency;
    private Integer maxTDP;
    private Map<String, Integer> additionalAccessories;
    private String system;
    private String height;
    private String width;
    private String depth;
    private Boolean touchableScreen;
    private String screenType;
    private String screenSize;
    private String screenResolution;
    private Integer screenRefreshRate;
    private String matrixLightness;
    private String batteryType;
    private String batteryCapacity;
    private Boolean fingerPrintReader;
    private String weight;
}
