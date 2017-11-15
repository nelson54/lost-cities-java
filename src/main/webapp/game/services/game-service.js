const axios = require('axios');

module.exports = class GameService {

    constructor(root) {
        this.root = root || '';
    }

    login(username, password) {
        return axios.post(`${this.root}/api/authenticate`, {username, password})
            .then((response)=> {
                axios.defaults.headers.common['Authorization'] = 'Bearer ' + response.data.id_token;

                return response;
            })
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




