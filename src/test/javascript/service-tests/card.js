module.exports = class Card {
    constructor(color, value, isMultiplier, instance) {
        this.color = color;
        this.value = value || 1;
        this.isMultiplier = isMultiplier;
        this.instance = instance;
    }

    toString() {
        return `${this.color}{${this.value.toString()}}[${this.instance.toString()}]instance`
    }
};
