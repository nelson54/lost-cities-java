const PlayerView = require('./player-view');
const PlayGroup = require('../card-groups/play-group');
const HandGroup = require('../card-groups/hand-group');
const Card = require('../card');

const game = require('../game');

module.exports = function PlayerViewFactory(playerData) {
    let player = new PlayerView(playerData.gameUser);
    player.play = new PlayGroup();

    let deck = new Card('deck');
    deck.showDrawButton();
    game.add.existing(deck);
    player.deck = deck;

    player.hand = new HandGroup();
    buildHand(player, playerData);

    return player;
};

/**
 * @param {PlayerView} player
 * @param playerData
 */
function buildHand(player, playerData) {
    let prev = null;

    playerData.hand.forEach((next) => {
        prev = addCard(player, next, prev);
    });

    player.updateLayout();
}

/**
 * @param {PlayerView} player
 * @param nextCardData
 * @param {Card} prevCard
 */
function addCard(player, nextCardData, prevCard) {
    let nextCard = new Card(nextCardData.color, nextCardData.value, nextCardData.multiplier);

    if(prevCard) {
        nextCard.x = prevCard.x + prevCard.width + 10;
    }

    player.hand.addChild(nextCard);

    return nextCard;
}
