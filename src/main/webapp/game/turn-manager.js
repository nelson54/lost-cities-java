const GameService = require('./services/game-service');
const Command = require('./commands/command');
module.exports = class TurnManager {

    constructor(gameId) {
        this.gameId = gameId;
        this.gameService = new GameService();
    }

    nextTurn(gameUserId) {
        this.command = new Command(this.gameId, gameUserId);
    }

    draw() {
        this.command.draw();
    }

    play(card) {
        this.command.play(card)
    }

    discard(card) {
        this.command.discard(card)
    }

    drawFromDiscard(color) {
        this.command.drawFromDiscard(color);
    }

    apply() {
        this.gameService.execute(this.command);
    }
};
