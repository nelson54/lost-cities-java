const game = require('../game');
const cardLayoutTool = require('../utils/card-layout-tool');


module.exports = class DiscardStack extends Phaser.Group {

    constructor(group, i) {
        super(game);
        this.mostRecentCards = [];
        group.addChild(this);
        this.i = i;
        this.properties = {
            baseX: 0,
            baseY: 0
        };
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
        this.mostRecentCards.push(card);
    }

    removeMostRecentCard() {
        let card = this.mostRecentCards.pop();
        this.remove(card);
        return card;
    }

    updateLayout() {
        let prev = null;
        this.properties.width = cardLayoutTool.width;
        this.properties.height = cardLayoutTool.height;
        this.properties.baseX = cardLayoutTool.xByIndex(this.i);
        this.properties.baseY = 0;

        this.nextPosition = {x: this.properties.baseX, y: this.properties.baseY};

        this.children.forEach((next) => {
            next.showDrawFromDiscardButton();
            next.y = this.properties.baseY;
            next.x = this.properties.baseX;

            next.width =  this.properties.width;
            next.height = this.properties.height;

            prev = next;
        });
    }
};
