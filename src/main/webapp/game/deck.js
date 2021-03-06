const game = require('./game');
const Button = require('./button');

const Sprite = Phaser.Sprite;
const Signal = Phaser.Signal;

module.exports = class Card extends Sprite {
    constructor(color, value, isMultiplier) {
        super(game, 0, 0, `image-${color.toLowerCase()}`);

        let valueSprite = new Sprite(game, 10, 5, `image-${value||'mult'}`);
        valueSprite.anchor.set(0, 0);
        this.addChild(valueSprite);

        this.drawButton = new Button(this.centerX, this.centerY + 25, 'draw-button');
        this.drawButton.events.onInputUp.add(()=> {
            this.hideButtons();
            this.onPlay.dispatch(this);
        });
        this.addChild(this.drawButton);


        this.color = color;
        this.value = value;
        this.isMultiplier = isMultiplier;

        this.inputEnabled = true;

        this.onPlay = new Signal();
        this.onDiscard = new Signal();
    }

    hideButtons() {
        this.playButton.visible = false;
        this.discardButton.visible = false;
    }

    showButtons() {
        this.playButton.visible = true;
        this.discardButton.visible = true;
    }
};
