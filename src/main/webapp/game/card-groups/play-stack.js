const game = require('../game');
const cardLayoutTool = require('../utils/card-layout-tool');
const _ = require('lodash');

module.exports = class PlayStack extends Phaser.Group {

    constructor(i, properties) {
        const style = { font: "bold 32px Arial", fill: "#fff" };
        super(game);
        this.i = i;

        this.properties = _.defaults({
            baseX: 0,
            baseY: 0,
            marginY: 20
        }, properties);

        this.multipliers = 1;
        this.points = 0;

        this.scoreText = game.add.text(0, 0, this.score, style);
        this.scoreText.anchor.set(.5, 0);
    }

    get baseScore() {
        if(this.children.length > 0) {
            return -20;
        } else {
            return 0;
        }
    }

    get score() {
        return (this.baseScore + this.points) * this.multipliers;
    }

    /**
     * @param {Card} card
     */
    addChild(card) {
        super.addChild(card);
        card.hideButtons();
        if(card.isMultiplier) {
            this.multipliers++;
        } else {
            this.points += card.value;
        }

        this.scoreText.text = this.score;
    }

    updateLayout() {
        let cardChanges = [];
        let prev = null;
        this.nextYPosition = null;
        this.properties.width = cardLayoutTool.width;
        this.properties.height = cardLayoutTool.height;
        this.properties.baseX = cardLayoutTool.xByIndex(this.i);
        this.properties.baseY = cardLayoutTool.height + this.properties.marginY;
        this.children.forEach((next) => {
            let changes = {};

            changes.y = this.nextYPosition || this.properties.baseY;
            changes.x = this.properties.baseX;
            changes.width = this.properties.width;
            changes.height = this.properties.height;
            cardChanges.push(new Promise((resolve)=> {
                game.add.tween(next)
                    .to(changes, 300, "Linear", true)
                    .onComplete.add(() => {
                        resolve();
                    });
            }));

            prev = next;
            this.nextYPosition = prev.y + (prev.height * .25);
        });

        if (prev) {
            this.scoreText.y = this.properties.baseY + prev.height;
        } else {
            this.nextYPosition = this.properties.baseY;
            this.scoreText.y = this.properties.baseY;
        }

        this.scoreText.x = this.properties.baseX + (this.properties.width / 2)
        return Promise.all(cardChanges);
    }

    getNextCardPosition() {
        return {
            x: cardLayoutTool.xByIndex(this.i),
            y: this.nextYPosition,
            width: cardLayoutTool.width,
            height: cardLayoutTool.height
        }
    }
};
