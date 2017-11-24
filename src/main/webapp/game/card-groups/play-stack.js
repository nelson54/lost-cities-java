const game = require('../game');
const cardLayoutTool = require('../utils/card-layout-tool');


module.exports = class PlayStack extends Phaser.Group {

    constructor(i) {
        const style = { font: "bold 32px Arial", fill: "#fff" };
        super(game);
        this.i = i;
        this.properties = {
            baseX: 0,
            baseY: 0
        };

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
        let prev = null;
        this.properties.width = cardLayoutTool.width;
        this.properties.height = cardLayoutTool.height;
        this.properties.baseX = cardLayoutTool.xByIndex(this.i);
        this.properties.baseY = 0;

        this.children.forEach((next) => {
            if(prev) {
                next.y = prev.y + (prev.height * .25);
            } else {
                next.y = this.properties.baseY;
            }

            next.x = this.properties.baseX;

            next.width =  this.properties.width;
            next.height = this.properties.height;

            prev = next;
        });

        if(prev) {
            this.scoreText.y = this.properties.baseY + prev.height;
        } else {
            this.scoreText.y = this.properties.baseY;
        }

        this.scoreText.x = this.properties.baseX + (this.properties.width/2)
    }
};
