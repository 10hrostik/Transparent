function generateStatusTag(id, classname, message, form) {
    let el = document.createElement("b");
    let htmlForm = document.getElementById(form);
    el.id = id;
    el.className = classname;
    el.textContent = message;
    if (htmlForm.children[htmlForm.children.length - 1].tagName !== 'BR') {
        htmlForm.appendChild(document.createElement('br')).appendChild(el);
    } 
    htmlForm.appendChild(el)
}

export default generateStatusTag