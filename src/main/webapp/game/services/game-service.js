const axios = require('axios');

//const game = require('../game');

module.exports = class GameService {

    constructor(root) {
        this.root = root || '';
    }

    getGame(id) {
        return axios.get(`${this.root}/api/v2/game/${id}`)
            .then((response) => response.data)
            .catch(function (error) {
                console.log(error);
            });
    }

    execute(command) {
        axios.put(`${this.root}/api/v2/game/${command.gameId}/play`, command.toJson())
            .then((response) => response.data)
            .catch(function (error) {
                console.log(error);
            });
    }
};


