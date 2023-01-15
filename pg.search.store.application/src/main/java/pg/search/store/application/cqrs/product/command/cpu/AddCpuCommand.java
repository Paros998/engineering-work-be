package pg.search.store.application.cqrs.product.command.cpu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.command.Command;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
public class AddCpuCommand implements Command<UUID> {
    private String title;

    private String producentCode;

    private String producentSite;

    private String dateOfProduction;

    private String cpuModel;

    private String producer;

    private Float maxTdp;

    private String technology;

    private Float coreClock;

    private Float boostCoreClock;

    private String totalSpecification;

    private String socket;

    private String series;

    private String version;

    private Integer cores;

    private Integer threads;

    private Integer instructionsPerCycle;

    private boolean onlyLaptopCpu;
}
