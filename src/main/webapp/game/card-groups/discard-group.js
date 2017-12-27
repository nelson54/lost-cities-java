const game = require('../game');
const PlayStack = require('./play-stack');
const Card = require('../card');
module.exports = class PlayGroup extends Phaser.Group {
    constructor(board) {
        super(game);
        this.colors = ['YELLOW', 'BLUE', 'WHITE', 'GREEN', 'RED'];
        this.stacks = {
            'YELLOW': [],
            'BLUE': [],
            'WHITE': [],
            'GREEN': [],
            'RED': []
        };
        Object.keys(this.stacks)
            .forEach((color) => this.addChild(this.stacks[color]));
    }

    updateLayout() {
        Object.keys(this.stacks)
            .forEach((color) => this.stacks[color].updateLayout())
    }

    discard(card) {
        this.stacks[card.color].push(card);
        this.animateCard(card);
    }

    animateCard(card) {
        game.add.tween(card)
            .to({x: game.world.centerX, y: game.world.centerY}, 600, "Linear", true)
            .onComplete.add(()=> {
                this.stacks[card.color].updateLayout();
                this.addChild()
            })
    }

    addChild(card) {
        super.addChild(card)

    }
};

