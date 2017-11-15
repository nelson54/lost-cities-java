const axios = require('axios');

module.exports = class Authenticator {

    constructor(basePath) {
        this.basePath = basePath;
    }

    login(username, password) {
        return axios.post(`${this.basePath}/api/authenticate`, {username, password})
            .then((response)=> {
                axios.defaults.headers.common['Authorization'] = 'Bearer ' + response.data.id_token;

                return response;
            })
    }
};


