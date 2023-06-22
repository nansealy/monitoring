function isEmpty(values) {
    for (let i = 0; i < values.length; i++) {
        if (typeof values[i] == "undefined" || values[i] === '') {
            return true;
        }

    }
    return false;
}

function isRepeatable(values) {
    if (values.length !== 0) {
        for (let i = 0; i < values.length; i++) {
            if (!(values[i] === values[0])) {
                return false;
            }
        }
    }
    return true;
}