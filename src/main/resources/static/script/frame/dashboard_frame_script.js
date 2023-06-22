let popup = document.getElementById('popup');
let addButton = document.getElementById('addButton');
let editButton = document.getElementById('editButton');
let editMenu = document.getElementById('editMenu');
let titleField = $('#titleField');
let urlField = $('#urlField');
let usernameField = $('#usernameField');
let passwordField = $('#passwordField');
let itemContainer = document.getElementById('listItemContainer');


$('#createButton').click(createDashboard);


reloadContent();


addButton.addEventListener('click', () => {
    showAndHighOnClickOut(popup);
});

function createDashboard() {
    $.ajax({
        url: '/dashboard/create',
        data: {
            "name": titleField.val(),
            "url": urlField.val(),
            "username": usernameField.val(),
            "password": passwordField.val()
        },
        type: 'POST',
        success: () => {
            closePopup();
            reloadContent();
        },
        error: xhr => showError(JSON.parse(xhr.responseText).message)
    });
}

function closePopup() {
    popup.style.display = 'none';
    titleField.val(null);
    hideErrorMessage();
    file = null;
}


function reloadContent() {
    itemContainer.innerHTML = '';

    $.ajax({
        url: '/dashboard/all',
        type: 'GET',
        success: data => show(data)
    });
}

function show(dashboards) {
    for (let i = 0; i < dashboards.length; i++) {
        let div = document.createElement('div');
        div.className = 'gridListItem parentElement';
        div.id = dashboards[i].id;
        div.setAttribute('type', 'session');


        let count = document.createElement('h3');
        count.className = 'gridListItemCount';
        count.textContent = dashboards[i].countRes;

        let h3 = document.createElement('h3');
        h3.className = 'gridListItemTitle';
        h3.textContent = dashboards[i].name;

        div.appendChild(count);
        div.appendChild(h3);
        itemContainer.appendChild(div);

        div.onclick = () => open(div.id);
    }
}

function open(dashboardId) {
    window.parent.postMessage('/frame/content_frame?dashboard_id=' + dashboardId);
}

editButton.addEventListener('click', () => {
    showAndHighOnClickOut(editMenu);
});
