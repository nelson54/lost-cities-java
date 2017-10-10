module.exports = class PropertyWatchingChangeEventProxy {
    static create(clazz, watchProperties, callback, propertiesObjectName) {
        let propertiesObjectFunction = PropertyWatchingChangeEventProxy.getPropertiesObjectFunc(propertiesObjectName);
        let validator = PropertyWatchingChangeEventProxy
            .buildValidator(watchProperties, callback, propertiesObjectFunction);

        return new Proxy(clazz, validator);
    };

    static buildValidator(watchProperties, callback, propertiesObjectFunction) {
        return {
            set: (obj, prop, value) => {
                let propertiesObject = propertiesObjectFunction(obj);
                propertiesObject[prop] = value;

                if(watchProperties.includes(prop)) {
                    callback(obj);
                }
            },
            get: (obj, prop) => {
                let propertiesObject = propertiesObjectFunction(obj);
                return propertiesObject[prop];
            }
        }
    }

    static getPropertiesObjectFunc(propertiesObjectName) {
        return (obj) => {
            if(!propertiesObjectName) {
                return obj[propertiesObjectName] || (obj[propertiesObjectName] = {});
            } else {
                return obj;
            }
        }
    }
};
