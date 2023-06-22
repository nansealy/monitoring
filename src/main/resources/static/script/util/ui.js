function showAndHighOnClickOut(element, postAction) {
    element.style.display = 'block';
    document.addEventListener('mouseup', (event) => hideIfClickOnOther(event, element, postAction));
}

function hideIfClickOnOther(event, element, postAction) {
    if (!element.contains(event.target)) {
        element.style.display = 'none';
        document.removeEventListener('click', hideIfClickOnOther);
        if (postAction != undefined && postAction != null) {
            postAction(event);
        }
    }
}

