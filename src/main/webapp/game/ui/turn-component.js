const game = require('../game');
const Signal = Phaser.Signal;

module.exports = class TurnComponent extends Phaser.Group {

    constructor() {
        super(game);

        this.undoButton = new Button(this.centerX, this.centerY + 100, 'discard-button');
        this.undoButton.events.onInputUp.add(()=> {
            this.hideButtons();
            this.onDiscard.dispatch(this);
        });
        this.addChild(this.undoButton);

    }

    done() {

    }

    undo() {

    }
};
