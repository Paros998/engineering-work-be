package pg.search.store.application.cqrs;

import pg.lib.cqrs.command.CommandHandler;

public class TestCommandHandler implements CommandHandler<TestCommand, Void> {

    public Void handle(TestCommand testCommand) {
        return null;
    }
}
