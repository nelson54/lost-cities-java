const GameService = require('./services/game-service');
const Command = require('./commands/command');
module.exports = class TurnManager {

    constructor(playerView, game, gameId) {
        this.playerView = playerView;
        this.gameId = gameId;
        this.gameService = game.gameService;
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
        return this.gameService.execute(this.command)
            .then((playerView) => {
                this.nextTurn(this.command.gameUserId);
                return playerView.commands.pop();
            })
    }
};
