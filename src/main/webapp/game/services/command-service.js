const clients = require('restify-clients');

const commandService = clients.createJsonClient({
    url: 'http://localhost:8081',
});

module.exports = class CommandService {

    execute(gameId, command) {
        command.gameId = gameId;
        commandService.put('/game/:gameId/play', command)
    }
}


