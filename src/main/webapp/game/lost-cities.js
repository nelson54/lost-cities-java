const game = require('./game');

const HandGroup = require('./card-groups/hand-group');
const PlayGroup = require('./card-groups/play-group');
const PlayerViewFactory = require('./ui/player-view-factory');
const cardLayoutTool = require('./utils/card-layout-tool');
const GameService = require('./services/game-service');

class LostCities extends Phaser.State {

    constructor() {
        super();
        this.gameService = new GameService('http://localhost:8080');
        game.id = document.getElementsByTagName('body')[0].dataset.gameId;


    }

    preload() {
        game.scale.scaleMode = Phaser.ScaleManager.RESIZE;


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
        this.game.load.image('image-deck', '/' + require('../content/images/cards/deck.png'));

        this.game.load.image('draw-button', '/' + require('../content/images/draw-button.png'));
        this.game.load.image('play-button', '/' + require('../content/images/play-button.png'));
        this.game.load.image('undo-button', '/' + require('../content/images/undo-button.png'));
        this.game.load.image('done-button', '/' + require('../content/images/done-button.png'));

        this.game.load.image('discard-button', '/' + require('../content/images/discard-button.png'));
    }

    create() {
        this.gameService.getGame(this.game.id)
            .then((playerView) => {
                this.player = PlayerViewFactory(playerView);
            });
    }
}

module.exports = new LostCities();
