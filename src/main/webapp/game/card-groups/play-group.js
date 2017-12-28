const game = require('../game');
const PlayStack = require('./play-stack');
const Card = require('../card');
module.exports = class PlayGroup extends Phaser.Group {
    constructor(board) {
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

        if(board && board.cards) {
            Object.keys(board.cards).forEach((color) => {
                let stack = board.cards[color];

                stack
                    .map((card) => new Card(card.color, card.value, card.multiplier, card.instance))
                    .forEach((card) => this.play(card));

            })
        }
    }

    updateLayout() {
        let promises = [];
        Object.keys(this.stacks)
            .forEach((color) => promises.push(this.stacks[color].updateLayout()));

        return Promise.all(promises);
    }

    play(card) {
        this.stacks[card.color].addChild(card);
        return new Promise((resolve, reject)=> {
            let stack = this.stacks[card.color],
                nextPosition = stack.getNextCardPosition();

            game.add.tween(card)
                .to(nextPosition, 300, "Linear", true)
                .onComplete.add(()=> {
                    resolve();
                })
        })
    }
};

