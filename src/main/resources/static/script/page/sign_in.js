let usernameField = $('#usernameField');
let passwordField = $('#passwordField');

$('#sign_in_button').click(authenticate);


function authenticate() {
    let values = [usernameField.val(), passwordField.val()];
    if (isEmpty(values)) {
        showError('Fill required fields!');
        return;
    }
    sendData();
}

function sendData() {
    $.ajax({
        url: '/auth/authenticate',
        type: 'POST',
        data: {
            username: usernameField.val(),
            password: passwordField.val()
        },
        success: () => window.location.replace("/workspace/page"),
        error: xhr => showError(JSON.parse(xhr.responseText).message)
    });
}