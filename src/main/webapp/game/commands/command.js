module.exports = class Command {
    constructor(gameId, gameUserId) {
        this.gameId = gameId;
        this.gameUserId = gameUserId;
    }

    play(card) {
        if(this._discard) delete this._discard;
        this._play = card;
    }

    discard(card) {
        if(this._play) delete this._play;
        this._discard = card;
    }

    draw() {
        this.draw = true;
    }

    drawFromDiscard(color) {
        this._color = color;
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
