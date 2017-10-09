const game = require('./game');

module.exports = class Card extends Phaser.Sprite {
    constructor(color, value, isMultiplier) {
        super(game, 0, 0, `image-${color.toLowerCase()}`);

        game.add.existing(this);

        let valueSprite = new Phaser.Sprite(game, 10, 5, `image-${value||'mult'}`);
        valueSprite.anchor.set(0, 0);
        this.addChild(valueSprite);

        this.color = color;
        this.value = value;
        this.isMultiplier = isMultiplier;


    }
};
