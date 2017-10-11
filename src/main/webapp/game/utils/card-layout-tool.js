module.exports = {
    get height() {
        return this.width * 1.25
    },
    get width() {
        return (window.innerWidth - 30 * 2 ) / 6
    },
    xByIndex(i) {
        return (this.width * i) + 30
    }
};
