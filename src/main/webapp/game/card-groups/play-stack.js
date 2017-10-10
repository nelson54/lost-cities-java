const game = require('../game');

module.exports = class PlayStack extends Phaser.Group {

    constructor(i) {
        super(game);
        this.i = i;
        this.properties = {
            baseX: 0,
            baseY: 0
        }
    }

    updateLayout() {
        let prev = null;

        this.properties.baseX = (window.innerWidth / 5) * this.i;
        this.properties.baseY = 0;

        //this.properties.width = cardWidth;
        //this.properties.height = cardHeight;

        this.children.forEach((next) => {
            if(prev) {
                next.y = prev.y + (prev.height * .25);
            } else {
                this.properties.width = next.width;
                this.properties.height = next.height;
                next.y = this.properties.baseY;
            }

            next.x = this.properties.baseX;

            next.width =  this.properties.width;
            next.height = this.properties.height;
            prev = next;
        })
    }
};
