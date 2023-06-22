let errorMessageContainer = $('#error-message');
let errorMessageDescriptionField = $('#error-message-description');

function showError(message) {
    errorMessageDescriptionField.text(message);
    errorMessageContainer.css('display', 'inline-block');
}

function hideErrorMessage() {
    errorMessageContainer.css('display', 'none');
}