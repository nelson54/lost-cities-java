const game = require('../game');
const cardLayoutTool = require('../utils/card-layout-tool');
const TurnManager = require('../turn-manager');
const Card = require('../card');

module.exports = class PlayerView {

    constructor(playerData) {
        this.gameUserId = playerData.gameUserId;
        this.login = playerData.login;

        this.turnManager = new TurnManager(this, game, game.id);
        this.turnManager.nextTurn(this.gameUserId);

        this._deck = null;
        this._hand = null;
        this._discard = null;
        this._play = null;

        game.scale.onSizeChange.add(() => {
            this.hand.properties.maxWidth = window.innerWidth;
            this.updateLayout();
        });

        window.playerView = this;
    }

    updateLayout() {
        this.hand.updateLayout();
        this.play.updateLayout();
        this.deck.x = cardLayoutTool.xByIndex(5);
        this.deck.width = cardLayoutTool.width;
        this.deck.height = cardLayoutTool.height;
    }

    executeCommands(commands) {
        if(commands) {
            commands.forEach((command)=> this.executeCommand(command));
        }
    }

    /**
     * @param {Command} command
     * @returns {Promise}
     */
    executeCommand(command) {
        let card = null;
        let drew = command.drew;
        if(command.play) {
            if(this.hand.hasCard(command.play.toString)) {
                card = this.hand.findCard(command.play.toString);
                this.playCard(card);
            }
        } else {
            card = this.hand.findCard(command.discard.toString);
            this.discardCard(card);
        }

        if(command.color) {

        } else {
            let card = new Card(drew.color, drew.value, drew.multiplier, drew.instance);
            this.draw(card);
        }

        this._hand.updateLayout();
    }

    draw(card) {
        this.hand.addChild(card);
    }

    get deck() {
        return this._deck;
    }

    set deck(value) {
        this._deck = value;
        this._deck.onDraw.add(() => {
            console.log('Drew a card!');
            this.turnManager.draw();
            this.turnManager.apply()
                .then(command => this.executeCommand(command));
        })
    }

    get hand() {
        return this._hand;
    }

    set hand(value) {
        let hand = this._hand = value;

        hand.onPlay.add((card)=> {
            this.turnManager.play(card);
        });

        hand.onDiscard.add((card)=> {
            this.turnManager.discard(card)
        });
    }

    playCard(card) {
        this._hand.remove(card);
        this.play.play(card);
        this._hand.updateLayout();
    }

    discardCard(card) {
        this._hand.remove(card);
        game.add.existing(card);

        game.add.tween(card)
            .to({x: game.world.centerX, y: game.world.centerY}, 600, "Linear", true)
            .onComplete.add(()=> {
            this.updateLayout();
        })
    }

    get discard() {
        return this._discard;
    }

    set discard(value) {
        this._discard = value;
    }

    get play() {
        return this._play;
    }

    set play(value) {
        this._play = value;
    }
};
