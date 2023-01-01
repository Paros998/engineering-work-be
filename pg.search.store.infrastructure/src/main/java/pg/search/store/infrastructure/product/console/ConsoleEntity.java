package pg.search.store.infrastructure.product.console;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.awsfiles.entity.FileEntity;

import pg.search.store.domain.game.Platform;
import pg.search.store.domain.product.Console;
import pg.search.store.domain.product.ProductType;
import pg.search.store.domain.product.console.ConsoleProducer;
import pg.search.store.infrastructure.product.ProductEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "consoles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ConsoleEntity extends ProductEntity {
    @Enumerated(EnumType.STRING)
    private ConsoleProducer producer;

    @Enumerated(EnumType.STRING)
    private Console console;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Override
    public ConsoleEntity setProductType(final ProductType productType) {
        this.productType = productType;
        return this;
    }

    @Override
    public ConsoleEntity setTitle(final String title) {
        this.title = title;
        return this;
    }

    @Override
    public ConsoleEntity setProducentCode(final String producentCode) {
        this.producentCode = producentCode;
        return this;
    }

    @Override
    public ConsoleEntity setProductPhoto(final FileEntity productPhoto) {
        this.productPhoto = productPhoto;
        return this;
    }

    @Override
    public ConsoleEntity setProducentSite(final String producentSite) {
        this.producentSite = producentSite;
        return this;
    }

    @Override
    public ConsoleEntity setDateOfProduction(final LocalDate dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
        return this;
    }

    public ConsoleEntity setProducer(final ConsoleProducer producer) {
        this.producer = producer;
        return this;
    }

    public ConsoleEntity setConsole(final Console console) {
        this.console = console;
        return this;
    }

    public ConsoleEntity setPlatform(final Platform platform) {
        this.platform = platform;
        return this;
    }
}