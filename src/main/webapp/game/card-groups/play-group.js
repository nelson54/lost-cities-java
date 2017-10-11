const game = require('../game');
const PlayStack = require('./play-stack');
module.exports = class PlayGroup extends Phaser.Group {
    constructor() {
        super(game);
        this.colors = ['YELLOW', 'BLUE', 'WHITE', 'GREEN', 'RED'];
        this.stacks = {
            'YELLOW': new PlayStack(0),
            'BLUE': new PlayStack(1),
            'WHITE': new PlayStack(2),
            'GREEN': new PlayStack(3),
            'RED': new PlayStack(4)
        };
        Object.keys(this.stacks)
            .forEach((color) => this.addChild(this.stacks[color]));
    }

    updateLayout() {
        Object.keys(this.stacks)
            .forEach((color) => this.stacks[color].updateLayout())
    }

    play(card) {
        this.stacks[card.color].addChild(card);
        this.animateCard(card);
    }

    animateCard(card) {
        game.add.tween(card)
            .to({x: game.world.centerX, y: game.world.centerY}, 600, "Linear", true)
            .onComplete.add(()=> this.stacks[card.color].updateLayout())
    }
};

