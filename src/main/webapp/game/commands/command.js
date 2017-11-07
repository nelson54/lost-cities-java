module.exports = class Command {
    constructor(gameId, gameUserId) {
        this.gameId = gameId;
        this.gameUserId = gameUserId;
    }

    play(card) {
        this._play = card;
    }

    draw() {
        this.draw = true;
    }

    drawFromDiscard(color) {
        this._color = color;
    }

    discard(card) {
        this._discard = card;
    }

    isValid() {
        return true;
    }

    toJson() {
        return {
            color: this._color,
            play: (this._play)
                ? this._play.toString() : null,
            discard: (this._discard)
                ? this._discard.toString() : null,
            gameUserId: this.gameUserId
        }
    }
};
