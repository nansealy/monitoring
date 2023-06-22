let runButton = document.getElementById('runButton');
let deleteItem = document.getElementById('deleteItem');
let saveButton = document.getElementById('saveButton');
let editItem = document.getElementById('editItem');
let exportItem = document.getElementById('exportItem');
let queryInput = document.getElementById('queryInput');


if (runButton != null) {
    editItem.onclick = () => {
        runButton.hidden = true
        saveButton.hidden = false
        queryInput.hidden = false
    }

    saveButton.onclick = () => {
        runButton.hidden = false
        saveButton.hidden = true
        queryInput.hidden = true

        saveQuery()
    }

    runButton.addEventListener('click', () => {
        $.ajax({
            url: '/dashboard/run?id=' + getParam("dashboard_id"),
            type: 'POST'
        });

        reloadContent()
    });

    exportItem.onclick = () => {
        window.location.replace("/export?id=" + getParam("dashboard_id"))
    }
}

deleteItem.addEventListener('click', () => {
    infoField.style.display = 'block';

    for (let item of itemContainer.children) {
        item.addEventListener('click', () => {
            removeItem(item.id, item.getAttribute('type'));
            infoField.style.display = 'none';
        });
    }
});

function removeItem(id, type) {
    $.ajax({
        url: '/dashboard/delete?id=' + id,
        type: 'POST',
        success: reloadContent
    });
}

function saveQuery() {
    $.ajax({
        url: '/dashboard/updateQuery?id=' + getParam("dashboard_id"),
        data: {
            "query": queryInput.value
        },
        type: 'POST'
    });
}