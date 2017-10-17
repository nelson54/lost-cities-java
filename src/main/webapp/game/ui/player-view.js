const game = require('../game');
const cardLayoutTool = require('../utils/card-layout-tool');
const TurnManager = require('../turn-manager');

module.exports = class PlayerView {

    constructor(playerData) {
        this.id = playerData.user.id;
        this.login = playerData.user.login;
        this.turnManager = new TurnManager();
        this._deck = null;
        this._hand = null;
        this._discard = null;
        this._play = null;

        game.scale.onSizeChange.add(() => {
            this.hand.properties.maxWidth = window.innerWidth;
            this.updateLayout();
        });
    }

    updateLayout() {
        this.hand.updateLayout();
        this.play.updateLayout();
        this.deck.x = cardLayoutTool.xByIndex(5);
        this.deck.width = cardLayoutTool.width;
        this.deck.height = cardLayoutTool.height;
    }

    draw() {

    }

    play(card) {

    }

    get deck() {
        return this._deck;
    }

    set deck(value) {
        this._deck = value;
        this._deck.onDraw.add(()=>{
            console.log('Drew a card!')
        })
    }

    get hand() {
        return this._hand;
    }

    set hand(value) {
        let hand = this._hand = value;

        hand.onPlay.add((card)=> {
            hand.remove(card);
            this.play.play(card);
            hand.updateLayout();
        });

        hand.onDiscard.add((card)=> {
            hand.remove(card);
            game.add.existing(card);

            game.add.tween(card)
                .to({x: game.world.centerX, y: game.world.centerY}, 600, "Linear", true)
                .onComplete.add(()=> {
                this.updateLayout();
            })
        });
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
