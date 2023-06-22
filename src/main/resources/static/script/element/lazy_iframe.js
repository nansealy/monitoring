let iframes = document.getElementsByTagName('iframe');

for (let i = 0; i < iframes.length; i++) {
    iframes[i].onload = () => iframes[0].style = 'visibility: visible';
}