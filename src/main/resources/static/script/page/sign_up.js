let usernameField = $('#usernameField');
let emailField = $('#emailField');
let passwordField = $('#passwordField');
let repeatedPasswordField = $('#repeatedPasswordField');

$('#signUp').click(signUp);

function signUp() {
    let values = [usernameField.val(), emailField.val(), passwordField.val(), repeatedPasswordField.val()];

    if (isEmpty(values)) {
        showError('Fill required fields!');
        return;
    }

    if (!isRepeatable([passwordField.val(), repeatedPasswordField.val()])) {
        showError('Passwords is not repeatable!');
        return;
    }
    hideErrorMessage();
    sendData();
}

function sendData() {
    $.ajax({
        url: '/auth/registration',
        type: 'POST',
        data: {
            username: usernameField.val(),
            email: emailField.val(),
            password: passwordField.val()
        },
        success: () => window.location.replace("/auth/sign_in"),
        error: xhr => showError(JSON.parse(xhr.responseText).message)
    });
}