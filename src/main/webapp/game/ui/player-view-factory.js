const PlayerView = require('./player-view');
const PlayGroup = require('../card-groups/play-group');
const HandGroup = require('../card-groups/hand-group');
const DiscardGroup = require('../card-groups/discard-group');
const Card = require('../card');

const game = require('../game');

/**
 * @param playerData
 * @returns {PlayerView}
 */
module.exports = function PlayerViewFactory(playerData) {
    let player = new PlayerView(playerData);
    player.play = new PlayGroup(playerData.board);

    let deck = new Card('deck');
    deck.showDrawButton();
    game.add.existing(deck);
    player.deck = deck;
    player.discard = new DiscardGroup();
    player.hand = new HandGroup();

    return buildHand(player, playerData)
        .then(()=> {
            return player
        });
};

/**
 * @param {PlayerView} player
 * @param playerData
 */
function buildHand(player, playerData) {
    let prev = null;
    let addCards = [];
    playerData.hand.forEach((next) => {
        addCards.push(prev = addCard(player, next, prev))
    });

    return Promise.all(addCards)
        .then(()=> player.updateLayout())
}

/**
 * @param {PlayerView} player
 * @param nextCardData
 * @param {Card} prevCard
 */
function addCard(player, nextCardData, prevCard) {

    let nextCard = new Card(nextCardData.color, nextCardData.value, nextCardData.multiplier, nextCardData.instance);

    if(prevCard) {
        nextCard.x = prevCard.x + prevCard.width + 10;
    }

    player.hand.draw(nextCard);

    return nextCard;
}
