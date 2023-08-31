function generateStatusTag(id, classname, message, form) {
    let el = document.createElement("b");
    el.id = id;
    el.className = classname;
    el.textContent = message;
    document.getElementById(form)
        .appendChild(document.createElement('br')).appendChild(el);
    document.getElementById(form).appendChild(el)
}

export default generateStatusTag