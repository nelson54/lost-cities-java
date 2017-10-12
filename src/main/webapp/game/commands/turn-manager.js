module.exports = class TurnManager {
    constructor(player) {
        this.player = player;
        this.reset();
    }

    undo() {

    }

    submit() {

    }

    draw(color) {
        if(color) {

        }
    }

    reset() {
        this.play = null;
        this.discard = null;

        this.draw = null;
    }
};
