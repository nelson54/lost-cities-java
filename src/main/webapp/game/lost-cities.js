const Card = require('./card');

class LostCities extends Phaser.State {

    constructor() {
        super();
        this.gameId = document.getElementsByTagName('body')[0].dataset.gameId;
    }

    preload() {
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
    }

    create() {
        this.cards = [];
        let gameInfo = this.cache.getJSON('gameInfo');
        let cardData = gameInfo.players[0].hand[0];

        this.cards.push(new Card(cardData.color, cardData.value, cardData.multiplier));

        cardData = gameInfo.players[0].hand[1];

        this.cards.push(new Card(cardData.color, cardData.value, cardData.multiplier));

        let card = this.cards[1];

        card.x = this.cards[0].x + this.cards[0].width + 10;

        cardData = gameInfo.players[0].hand[2];
        this.cards.push(new Card(cardData.color, cardData.value, cardData.multiplier));

        card = this.cards[2];

        card.x = this.cards[1].x + this.cards[1].width + 10;

        cardData = gameInfo.players[0].hand[3];
        this.cards.push(new Card(cardData.color, cardData.value, cardData.multiplier));

        card = this.cards[3];

        card.x = this.cards[2].x + this.cards[2].width + 10;

        cardData = gameInfo.players[0].hand[4];
        this.cards.push(new Card(cardData.color, cardData.value, cardData.multiplier));

        card = this.cards[4];

        card.x = this.cards[3].x + this.cards[3].width + 10;


        //console.log(gameInfo);
    }

    update() {

    }
};

module.exports = new LostCities();
