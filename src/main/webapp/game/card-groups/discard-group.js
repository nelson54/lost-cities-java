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

        this.animateCard(card);
    }

    animateCard(card) {
        let stack = this.stacks[card.color];
        game.add.tween(card)
            .to({x: game.world.centerX, y: game.world.centerY}, 600, "Linear", true)
            .onComplete.add(()=> {
                stack.addChild(card);
                stack.updateLayout();
            })
    }

    removeTopCardForColor(color) {
        return this.stacks[color].removeMostRecentCard();
    }
};

