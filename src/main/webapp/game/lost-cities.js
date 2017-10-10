const game = require('./game');
const Card = require('./card');
const HandGroup = require('./card-groups/hand-group');
const PlayStack = require('./card-groups/play-stack');

class LostCities extends Phaser.State {

    constructor() {
        super();
        this.gameId = document.getElementsByTagName('body')[0].dataset.gameId;
    }

    preload() {
        game.scale.scaleMode = Phaser.ScaleManager.RESIZE;
        this.load.json('gameInfo', `/api/game/${this.gameId}`);

        this.game.load.image('image-2', '/' + require('../content/images/cards/2.png'));
        this.game.load.image('image-3', '/' + require('../content/images/cards/3.png'));
        this.game.load.image('image-4', '/' + require('../content/images/cards/4.png'));
        this.game.load.image('image-5', '/' + require('../content/images/cards/5.png'));
        this.game.load.image('image-6', '/' + require('../content/images/cards/6.png'));
        this.game.load.image('image-7', '/' + require('../content/images/cards/7.png'));
        this.game.load.image('image-8', '/' + require('../content/images/cards/8.png'));
        this.game.load.image('image-9', '/' + require('../content/images/cards/9.png'));
        this.game.load.image('image-10', '/' + require('../content/images/cards/10.png'));
        this.game.load.image('image-mult', '/' + require('../content/images/cards/mult.png'));
        this.game.load.image('image-blue', '/' + require('../content/images/cards/blue.png'));
        this.game.load.image('image-green', '/' + require('../content/images/cards/green.png'));
        this.game.load.image('image-red', '/' + require('../content/images/cards/red.png'));
        this.game.load.image('image-white', '/' + require('../content/images/cards/white.png'));
        this.game.load.image('image-yellow', '/' + require('../content/images/cards/yellow.png'));

        this.game.load.image('play-button', '/' + require('../content/images/play-button.png'));
        this.game.load.image('discard-button', '/' + require('../content/images/discard-button.png'));
    }

    create() {

        window.playStack = this.playStack = new PlayStack(2);
        window.handGroup = this.handGroup = new HandGroup();

        let gameInfo = this.cache.getJSON('gameInfo');

        this.buildHand(gameInfo.players[0]);

        game.scale.onSizeChange.add(() => {
            this.handGroup.properties.maxWidth = window.innerWidth;
            this.updateLayout();
        });

        this.handGroup.onPlay.add((card)=> {
            this.handGroup.remove(card);
            this.playStack.addChild(card);

            game.add.tween(card)
                .to({x: game.world.centerX, y: game.world.centerY}, 600, "Linear", true)
                .onComplete.add(()=> {
                    this.updateLayout();
                })
        });

        this.handGroup.onDiscard.add((card)=> {
            this.handGroup.remove(card);
            game.add.existing(card);

            game.add.tween(card)
                .to({x: game.world.centerX, y: game.world.centerY}, 600, "Linear", true)
                .onComplete.add(()=> {
                    this.updateLayout();
                })
        });
    }

    updateLayout() {
        this.handGroup.updateLayout();
        this.playStack.updateLayout();
    }

    buildHand(player) {
        let prev = null;
        player.hand.forEach((next) => {
            prev = this.addCard(next, prev);
        });

        this.updateLayout();
    }

    addCard(nextCardData, prevCard) {
        let nextCard = new Card(nextCardData.color, nextCardData.value, nextCardData.multiplier);

        if(prevCard) {
            nextCard.x = prevCard.x + prevCard.width + 10;
        }

        this.handGroup.addChild(nextCard);

        return nextCard;
    }
};

module.exports = new LostCities();
