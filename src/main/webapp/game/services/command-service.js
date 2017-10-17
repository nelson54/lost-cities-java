const game = require('../game');

module.exports = class CommandService {

    execute(command) {
        console.log(command);
        axios.put(`/api/game/${game.id}/play`, command)
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            });
    }
}


