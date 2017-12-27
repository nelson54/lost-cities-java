const axios = require('axios');

module.exports = class GameService {

    constructor(root, isVersion3) {
        let jwtToken = window.sessionStorage['jhi-authenticationtoken'];

        this.root = root || '/';

        this.basePath = isVersion3 ? 'api/v3/game/' : 'api/v2/game/';

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
        return axios.put(`${this.root}${this.basePath}${command.gameId}/play`, command.toJson())
            .then((response) => response.data)
            .catch(function (error) {
                console.log(error);
            });
    }
};




