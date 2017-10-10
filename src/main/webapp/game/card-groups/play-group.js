const game = require('./game');
const CONSTANTS = require('./constants');
const PropertyWatchingChangeEventProxy = require('./utils/property-watching-change-event-proxy');
const clone = require('./utils/clone');
const baseWidth = 278;
const baseHeight = 351;
const defaultProperties = {
    baseX: 0,
    baseY: 0,
    maxWidth: null,
    padding: 10,
    margin: 20,
    distanceFromBottom: 30
};

class HandGroup extends Phaser.Group {
    constructor() {
        super(game);

        this.properties = clone(defaultProperties);
        this.properties.maxWidth = window.innerWidth || 1028;

        this.onChildInputOver.add((card) => {
            game.add.tween(card).to(
                { y: this.properties.baseY - (this.properties.height *.66) },
                CONSTANTS.CARD_HIGHLIGHT_ANIMATION_SPEED,
                "Linear",
                true);
        });

        this.onChildInputOut.add((card) => {
            if(!card.getBounds().contains(game.input.x, game.input.y)) {
                game.add.tween(card)
                    .to({y: this.properties.baseY}, CONSTANTS.CARD_HIGHLIGHT_ANIMATION_SPEED, "Linear", true);
            }
        });
    }

    updateLayout () {
        let prev = null;
        let scale = this.calculateCardScale();

        this.properties.width = baseWidth * scale;
        this.properties.height = baseHeight * scale;

        if(this.properties.distanceFromBottom) {
            this.properties.baseY = window.innerHeight - (this.properties.height * .33) - this.properties.distanceFromBottom;
        }

        this.children.forEach((next) => {
            if(prev) {
                next.x = prev.x + prev.width + this.properties.padding;
            } else {
                next.x = this.properties.baseX + this.properties.margin;
            }

            next.y = this.properties.baseY;

            next.width =  this.properties.width;
            next.height = this.properties.height;
            prev = next;
        })
    }

    calculateCardScale () {
        return ((this.properties.maxWidth - ((this.properties.padding * 6) + (this.properties.margin * 2))) / 8) / baseWidth;
    }
}

module.exports = PropertyWatchingChangeEventProxy.create(HandGroup, Object.keys(defaultProperties), (obj)=> obj.updateLayout(), 'properties');

