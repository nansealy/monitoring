let title = $('#title');
let itemContainer = document.getElementById('listItemContainer');
let editButton = document.getElementById('editButton');
let editMenu = document.getElementById('editMenu');
let workspaceHead = document.getElementById('workspaceHead');

reloadContent();


saveButton.addEventListener('click', () => {
    saveButton.hidden = true

});

function reloadContent() {
    itemContainer.innerHTML = '';

    $.ajax({
        url: '/dashboard/find?id=' + getParam('dashboard_id'),
        type: 'GET',
        success: data => show(data)
    });
}

function getParam(title) {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    return urlParams.get(title);
}

function show(dashboard) {
    workspaceHead.setAttribute('dashboard_id', dashboard.id);

    queryInput.value = dashboard.query
    title.html(dashboard.name);

    let dashboardResDtos = dashboard.dashboardResDtos;

    if (dashboardResDtos === null || dashboardResDtos === undefined || dashboardResDtos[0] === null || dashboardResDtos[0] === undefined) {
        return
    }

    let table = createHeaders(dashboardResDtos);

    for (let obj of dashboardResDtos) {
        obj = JSON.parse(obj.result)
        let tr = document.createElement('tr');
        for (let key of Object.keys(obj)) {
            let td = document.createElement('td');
            td.textContent = obj[key]
            tr.appendChild(td)
            table.appendChild(tr)
        }
    }
    itemContainer.appendChild(table);
}

function createHeaders(dashboardResDtos) {
    let resObj = JSON.parse(dashboardResDtos[0].result)

    let table = document.createElement('table');

    let header = document.createElement('tr');
    for (let key of Object.keys(resObj)) {
        let th = document.createElement('th');
        th.textContent = key
        header.appendChild(th)
    }
    table.appendChild(header)
    return table
}


editButton.addEventListener('click', () => {
    showAndHighOnClickOut(editMenu);
    editMenu.addEventListener('click', () => editMenu.style.display = 'none');
});
