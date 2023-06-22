$('#logOutItem').click(logout);
let iframe = document.getElementById('workspace');
window.addEventListener('message', (path) => navigateIframe(path.data));
window.addEventListener('onbeforeunload ', () => {
    clearIntervals();
    audio.pause();
});


function clearIntervals() {
    var interval_id = window.setInterval("", Number.MAX_VALUE); // Get a reference to the last
    // interval +1
    for (var i = 1; i < interval_id; i++)
        window.clearInterval(i);
}

function logout() {
    $.ajax({
        url: '/auth/logout',
        type: 'POST',
        success: () => location.reload()
    });
}

function navigateIframe(path) {
    iframe.style = 'visibility: hidden';
    iframe.src = path;
}