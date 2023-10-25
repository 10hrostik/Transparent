import '../../styles/forms/edit.css';
import React from 'react';
import ReactDOMServer from 'react-dom/server';

function generateUserPopUp () {
    let bodyElement = buildPopUp();
    let backgroundElement = generateBackground();

    document.getElementById('mainLayout').appendChild(backgroundElement);
    document.getElementById('mainLayout').appendChild(bodyElement)
}

function buildPopUp () {
    let element = document.createElement('div');
    element.className = 'editProfilePopUp';
    element.id = 'logoutPopUp';
    element.appendChild(getHeader())

    return element;
}

function getHeader () {
    let element = document.createElement('div');
    element.className = 'editPopUpHeader';
    createForm(element);

    return element;
}

function createForm (header) {
    let photoLayout = document.createElement('div');
    photoLayout.id = 'photoLayout';
    photoLayout.className = 'editPhotoContainer'
    header.appendChild(photoLayout);
}

function generateBackground () {
    let windowElement = document.createElement('div');
    windowElement.className = 'sideBarIconWindow';
    windowElement.onclick = () => {
        windowElement.remove();
        document.getElementById('logoutPopUp').remove()
    }

    return windowElement;
}

export default generateUserPopUp;