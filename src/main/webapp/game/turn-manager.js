const GameService = require('./services/game-service');
const Command = require('./commands/command');
module.exports = class TurnManager {

    constructor(gameId) {
        this.gameService = new GameService();
    }

    nextTurn(gameUserId) {
        this.command = new Command(gameUserId);
    }

    apply() {
        let commandData = this.command.toJson();
        this.gameService.execute(commandData);
    }
};
