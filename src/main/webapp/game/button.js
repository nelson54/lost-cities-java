const game = require('./game');

module.exports = class Card extends Phaser.Sprite {
    constructor(x, y, texture) {
        super(game, x, y, texture);
        this.anchor.set(0.5, 0.5);

        this.height *= .75;
        this.width *= .75;

        this.inputEnabled = true;
        this.input.useHandCursor = true;

        this.events.onInputOver.add(() => {
            game.add.tween(this).to({ alpha: 1 }, 200, "Linear", true);
        });

        this.events.onInputOut.add(() => {
            game.add.tween(this).to({ alpha: .65 }, 200, "Linear", true);
        });
    }

    set active(isActive) {
        this.isActive = isActive;
        this.inputEnabled = isActive;
        this.alpha = isActive ? 1 : .4;
    }

    get active() {
        return this.isActive;
    }
};
