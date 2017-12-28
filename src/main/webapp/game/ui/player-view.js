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
        this.play.updateLayout();
        this.discard.updateLayout();
        this.deck.x = cardLayoutTool.xByIndex(5);
        this.deck.width = cardLayoutTool.width;
        this.deck.height = cardLayoutTool.height;
        return this.hand.updateLayout();
    }

    executeCommands(commands) {
        let commandsPromise = Promise.resolve();
        if(commands) {
            commands.forEach((command)=> commandsPromise = this.executeCommand(commandsPromise, command));
        }

        return commandsPromise;
    }

    /**
     * @param {Command} command
     * @returns {Promise}
     */
    executeCommand(lastCommand, command) {
        let card = null;
        let drew = command.drew;

        return lastCommand.then(() => {
            if (command.play && this.hand.hasCard(command.play.toString)) {
                card = this.hand.findCard(command.play.toString);
                return this.playCard(card);
            } else {
                card = this.hand.findCard(command.discard.toString);
                return this.discardCard(card)
            }
        }).then(()=> {
            let card;
            if (command.drawColor) {
                card = this.drawCardFromDiscard(command.drawColor);
            } else {
                card = new Card(drew.color, drew.value, drew.multiplier, drew.instance);
            }


            let promise = this.drawCard(card);
            return Promise.all(promise, this.updateLayout());
        })
    }

    get deck() {
        return this._deck;
    }

    set deck(value) {
        this._deck = value;
        this._deck.onDraw.add(() => {
            console.log('Drew a card!');
            this.turnManager.draw();
            if(this.turnManager.isReady()) {
                this.turnManager.apply()
                    .then(command => this.executeCommand(Promise.resolve(), command));
            }
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

    drawCard(card) {
        return this.hand.draw(card);
    }

    playCard(card) {
        this.hand.remove(card);
        let play = this.play.play(card);
        return play;
    }

    discardCard(card) {
        this.hand.remove(card);
        let discard = this.discard.addCard(card);
        return discard;
    }

    drawCardFromDiscard(color) {
        return this.discard.removeTopCardForColor(color);
    }

    get discard() {
        return this._discard;
    }

    set discard(value) {
        this._discard = value;
        this._discard.onDrawFromDiscard.add((card)=> {
            this.turnManager.drawFromDiscard(card);

            if(this.turnManager.isReady()) {
                this.turnManager.apply()
                    .then(command => this.executeCommand(Promise.resolve(), command));
            }
        })
    }

    get play() {
        return this._play;
    }

    set play(value) {
        this._play = value;
    }
};
