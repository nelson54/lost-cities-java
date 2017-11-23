const game = require('../game');
const Button = require('../button');
module.exports = class TurnMenu extends Phaser.Group {
    constructor() {
        super(game);
        this.doneButton = new Button(0, 0, 'done-button');
        this.doneButton.anchor.set(1, 1);
        this.doneButton.x = game.world.width;
        this.doneButton.y = game.world.centerY;
        this.addChild(this.doneButton);

        this.undoButton = new Button(0, 0, 'undo-button');
        this.undoButton.anchor.set(1, 0);
        this.undoButton.x = game.world.width;
        this.undoButton.y = game.world.centerY + 15;
        this.addChild(this.undoButton);

        this.onDone = this.doneButton.events.onInputUp;
        this.onUndo = this.undoButton.events.onInputUp;
    }
};
