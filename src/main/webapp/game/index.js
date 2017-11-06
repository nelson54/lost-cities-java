const GameService = require('./services/game-service');

window.gameService = new GameService();



let callback = function(){
    const game = require('./game');

    const lostCities = require('./lost-cities');
    game.state.add('LostCities', lostCities, false);
    game.state.start('LostCities');
};

if (
    document.readyState === "complete" ||
    (document.readyState !== "loading" && !document.documentElement.doScroll)
) {
    callback();
} else {
    document.addEventListener("DOMContentLoaded", callback);
}
