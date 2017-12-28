const {Group, Signal} = require('phaser');
const game = require('../game');
const DiscardStack = require('./discard-stack');
const Card = require('../card');
module.exports = class DiscardGroup extends Group {
    constructor() {
        super(game);
        this.colors = ['YELLOW', 'BLUE', 'WHITE', 'GREEN', 'RED'];
        this.stacks = {
            'YELLOW': new DiscardStack(this, 0),
            'BLUE': new DiscardStack(this, 1),
            'WHITE': new DiscardStack(this, 2),
            'GREEN': new DiscardStack(this, 3),
            'RED': new DiscardStack(this, 4)
        };

        this.onDrawFromDiscard = new Signal();
    }

    updateLayout() {
        Object.keys(this.stacks)
            .forEach((color) => this.stacks[color].updateLayout())
    }

    addCard(card) {
        return new Promise((resolve) => {
            let stack = this.stacks[card.color];
            stack.addChild(card);
            let changes = {
                x: stack.nextPosition.x,
                y: stack.nextPosition.y,
                height: stack.properties.height,
                width: stack.properties.width
            };
            game.add.tween(card)
                .to(changes, 600, "Linear", true)
                .onComplete.add(() => {
                    resolve(card);
                });
        })
    }

    removeTopCardForColor(color) {
        return this.stacks[color].removeMostRecentCard();
    }
};

