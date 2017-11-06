const Game = require('phaser').Game;

class LostCitiesGame extends Game {

    constructor() {
        super(window.innerWidth, window.innerHeight, Phaser.AUTO, '', null);
    }

};


module.exports = new LostCitiesGame();
