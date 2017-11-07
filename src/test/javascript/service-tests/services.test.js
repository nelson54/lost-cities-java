const GameService = require('../../../main/webapp/game/services/game-service');
const Command = require('../../../main/webapp/game/commands/command');
const Authenticator = require('./authenticator');
const Card = require('./card');

const USERNAME = 'user';
const PASSWORD = 'user';
const URL = 'http://localhost:8080';
const gameId = 1001;

process.env['NODE_TLS_REJECT_UNAUTHORIZED'] = '0';

class GameServiceTest {

    static get URL() {return  'http://localhost:8080'}


    constructor() {
        this.authenticator = new Authenticator(URL);
        this.gameService = new GameService(URL);
    }

    getGame(gameId) {
        return this.authenticator
            .login(USERNAME, PASSWORD)
            .then((loginResponse)=> {
                return this.gameService.getGame(gameId)
            })
            .then((game)=> {
                game.hand = game.hand.map((card) => new Card(card.color, card.value, card.multiplier, card.instance));
                return game;
            })
    }

    execute(command) {
        return this.authenticator
            .login(USERNAME, PASSWORD)
            .then(()=> {
                return this.gameService.execute(command);
            })
    }

    findRandomCardToPlay(game) {

    }
}


let gameTest = new GameServiceTest();

let playCard;
let discardCard;

gameTest.getGame(gameId)
    .then((game)=> {
        let command = new Command(gameId, game.gameUserId);

        let card = game.hand[0];

        console.log(`Playing card: ${card.toString()}`);

        command.play(card);
        command.draw();

        return gameTest.execute(command)
    }).then(()=> {
        return gameTest.getGame(gameId)
    }).then((game)=> {
        let command = new Command(gameId, game.gameUserId);
        let card = game.hand[0];
        console.log(`Discarding card: ${card.toString()}`);

        command.discard(card);
        command.draw();

        return gameTest.execute(command)
    }).then(()=> {
        return gameTest.getGame(gameId)
    }).then((game)=> {
        return console.log(game)
    });
