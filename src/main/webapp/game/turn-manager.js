const CommandService = require('./services/command-service');
const Command = require('./commands/command');
module.exports = class TurnManager {

    constructor(gameId) {
        this.gameId = gameId;
    }

    nextTurn(gameUserId) {
        this.command = new Command(gameUserId);
    }

    finishTurn() {

    }
}
