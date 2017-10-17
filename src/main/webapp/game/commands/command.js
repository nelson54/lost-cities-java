module.exports = class Command {
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
            play: this._play.toString(),
            discard: this._discard.toString()
        }
    }
};
