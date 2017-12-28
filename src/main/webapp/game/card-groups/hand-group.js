const game = require('../game');
const CONSTANTS = require('../constants');
const PropertyWatchingChangeEventProxy = require('../utils/property-watching-change-event-proxy');
const clone = require('../utils/clone');
const Signal = Phaser.Signal;
const baseWidth = 278;
const baseHeight = 351;
const defaultProperties = {
    baseX: 0,
    baseY: 0,
    maxWidth: null,
    padding: 0,
    margin: 20,
    distanceFromBottom: 30
};

class HandGroup extends Phaser.Group {
    constructor() {
        super(game);

        this.cardsByToString = {};

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

        this.onPlay = new Signal();
        this.onDiscard = new Signal();
    }

    hasCard(cardString) {
        return this.cardsByToString.hasOwnProperty(cardString)
    }

    findCard(cardString) {
        return this.cardsByToString[cardString];
    }

    /**
     *
     * @param {Card} child
     */
    draw(child, skipAnimation) {
        super.addChild(child);
        this.cardsByToString[child.toString()] = child;
        child.inputEnabled = true;
        child.showButtons();
        child.onPlay.add((card)=> {
            this.onPlay.dispatch(card);
        });

        child.onDiscard.add((card)=> {
            this.onDiscard.dispatch(card);
        });
        if(skipAnimation) {
            return Promise.resolve(child);
        } else {
            return new Promise((resolve) => {
                let changes = {
                    x: this.nextPosition.x,
                    y: this.nextPosition.y,
                    height: this.properties.height,
                    width: this.properties.width
                };
                game.add.tween(child)
                    .to(changes, 200, "Linear", true)
                    .onComplete.add(() => {
                        resolve(child);
                    });
            })
        }
    }

    updateLayout () {
        if(!this.tween || !this.tween.isRunning) {
            let prev = null;
            let scale = this.calculateCardScale();

            this.properties.width = baseWidth * scale;
            this.properties.height = baseHeight * scale;

            if (this.properties.distanceFromBottom) {
                this.properties.baseY = window.innerHeight - (this.properties.height * .33) - this.properties.distanceFromBottom;
            }

            this.nextPosition = {
                x: this.properties.baseX + this.properties.margin,
                y: this.properties.baseY
            };

            let cardChanges = [];

            this.children.forEach((next) => {
                let changes = {};
                changes.x = this.nextPosition.x;
                changes.y = this.nextPosition.y;
                changes.width = this.properties.width;
                changes.height = this.properties.height;
                cardChanges.push(new Promise((resolve) => {
                    game.add.tween(next)
                        .to(changes, 300, "Linear", true)
                        .onComplete.add(resolve)
                }));


                prev = next;

                this.nextPosition = {
                    x: changes.x + prev.width + this.properties.padding,
                    y: changes.y
                }
            });

            return Promise.all(cardChanges);
        }
    }

    calculateCardScale () {
        let size = Math.max(8, this.children.length);
        return ((this.properties.maxWidth - ((this.properties.padding * (size - 2)) + (this.properties.margin * 2))) / size) / baseWidth;
    }
}

module.exports = HandGroup;

