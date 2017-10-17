const game = require('./game');
const Button = require('./button');

const Sprite = Phaser.Sprite;
const Signal = Phaser.Signal;

module.exports = class Card extends Sprite {
    constructor(color, value, isMultiplier, instance) {
        super(game, 0, 0, `image-${color.toLowerCase()}`);

        let valueSprite = new Sprite(game, 10, 5, `image-${value||'mult'}`);
        valueSprite.anchor.set(0, 0);
        this.addChild(valueSprite);

        this.playButton = new Button(this.centerX, this.centerY + 25, 'play-button');
        this.playButton.events.onInputUp.add(()=> {
            this.hideButtons();
            this.onPlay.dispatch(this);
        });
        this.addChild(this.playButton);


        this.discardButton = new Button(this.centerX, this.centerY + 100, 'discard-button');
        this.discardButton.events.onInputUp.add(()=> {
            this.hideButtons();
            this.onDiscard.dispatch(this);
        });
        this.addChild(this.discardButton);

        this.drawButton = new Button(this.centerX, this.centerY + 25, 'draw-button');
        this.drawButton.events.onInputUp.add(()=> {
            this.hideButtons();
            this.onDraw.dispatch(this);
        });
        this.addChild(this.drawButton);
        this.drawButton.visible = false;

        this.color = color;
        this.value = value;
        this.isMultiplier = isMultiplier;
        this.instance = instance;

        this.inputEnabled = true;

        this.onPlay = new Signal();
        this.onDiscard = new Signal();
        this.onDraw = new Signal();
    }

    hideButtons() {
        this.playButton.visible = false;
        this.discardButton.visible = false;
    }

    showButtons() {
        this.playButton.visible = true;
        this.discardButton.visible = true;
    }

    showDrawButton() {
        this.playButton.visible = false;
        this.discardButton.visible = false;
        this.drawButton.visible = true;
    }

    toString() {
        return `${this.color}{${this.value}}[${this.instance}]instance`
    }
};
