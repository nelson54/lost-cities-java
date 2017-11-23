const axios = require('axios');

module.exports = class GameService {

    constructor(root) {
        let jwtToken = window.sessionStorage['jhi-authenticationtoken'].slice(1, 192);

        this.root = root || '';

        this.basePath = '/api/v2/game/';

        axios.defaults.headers.common['Authorization'] = `Bearer ${jwtToken}`;

    }

    getGame(id) {
        return axios.get(`${this.root}${this.basePath}${id}`)
            .then((response) => response.data)
            .catch(function (error) {
                console.log(error);
            });
    }

    execute(command) {
        return axios.put(`${this.root}/api/v2/game/${command.gameId}/play`, command.toJson())
            .then((response) => response.data)
            .catch(function (error) {
                console.log(error);
            });
    }
};




