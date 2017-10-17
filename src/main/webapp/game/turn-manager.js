const CommandService = require('./services/command-service');
const Command = require('./commands/command');
module.exports = class TurnManager {

    constructor(gameId) {
        this.commandService = new CommandService();
    }

    nextTurn(gameUserId) {
        this.command = new Command(gameUserId);
    }

    apply() {
        let commandData = this.command.toJson();
        this.commandService.execute(commandData);
    }
}
