import '../../styles/forms/edit.css';
import apiServer from "./ApiServer";

let images;
let user;
let image = [];

function generateUserPopUp (currentUser) {
    user = currentUser;
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
    getPhotoLayout(header).then(() => {
        header.appendChild(getCredentialLayout());
    });
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

async function getPhotoLayout(header) {
    let photoLayout = document.createElement('div');
    let mainPhotoLayout = document.createElement('div');
    let photo = document.createElement('img');
    const userId = JSON.parse(sessionStorage.getItem('user')).id;

    mainPhotoLayout.id = 'mainUserPhotoLayout';
    photoLayout.id = 'photoLayout';
    mainPhotoLayout.className = 'mainUserPhotoLayout';
    photoLayout.className = 'editPhotoContainer';
    photo.className = 'mainUserPhoto';

    images = await getPhotos(userId);
    image = await getMainPhoto(userId);

    if (image == null) {
        photo.src = require('../../images/no-photo-profile.png');
        photo.style.marginLeft = "5px";
        let imageForm = buildUploadImageForm(userId, photo);
        mainPhotoLayout.appendChild(imageForm);
        mainPhotoLayout.onclick = () => document.getElementById('imageInput').click();
    } else {
        photo.src = URL.createObjectURL(image);
    }
    mainPhotoLayout.appendChild(photo);
    photoLayout.appendChild(mainPhotoLayout);
    header.appendChild(photoLayout)
}

function buildUploadImageForm(userId, photo) {
    let imageForm = document.createElement('form');
    imageForm.id = 'imageUploadForm';
    imageForm.encoding = 'multipart/form-data';
    buildUploadImageInput(imageForm, userId, photo);

    return imageForm;
}

function buildUploadImageInput(imageForm, userId, photo) {
    let imageInput = document.createElement('input');
    imageInput.style.display = 'none';
    imageInput.type = 'file';
    imageInput.id = 'imageInput';
    imageInput.name = "image";
    imageInput.accept = 'image/*';
    imageInput.onchange = () => uploadImage(imageForm, userId, photo);
    imageForm.appendChild(imageInput);
}

function uploadImage(form, userId, photo) {
    const formData = new FormData(form);
    const apiUrl = apiServer + `secured/user/${userId}/image`;

    fetch(apiUrl, {
        headers: {
            'Authorization': 'Bearer ' + sessionStorage.getItem('token')
        },
        method: 'POST',
        body: formData,
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.blob();
    })
    .then(image => {
        photo.src = URL.createObjectURL(image);
        photo.style.marginLeft = "0px";
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function getCredentialLayout() {
    let form = '<form id="credentialContainer" class="editCredentialForm">' +
        '<input id="usernameInput" class="editCredentialInput" name="usernameInput" ' +
            'type="text" required value="@' + user.credential + '" placeholder="Create a unique username">' +
        '<input id="emailInput" class="editPhoneInput" name="emailInput" ' +
            'type="text" required value="+' + user.phone + '" placeholder="Insert your email">' +
    '</form>'
    let layout = document.createElement('div');
    layout.className = 'editCredentialContainer';
    layout.innerHTML = form;

    return layout;
}

async function getMainPhoto(userId) {
    return fetch(apiServer + `secured/user/${userId}/image/main`, {
        headers: {
            'Authorization': 'Bearer ' + sessionStorage.getItem('token')
        },
        method: 'GET'
    })
    .then(response => {
        if (response.status === 204) return null;

        return response.blob();
    })
}

async function getPhotos (id) {
    return await fetch(apiServer + `secured/user/${id}/image/all`, {
        headers: {
            'Authorization': 'Bearer ' + sessionStorage.getItem('token')
        },
        method: 'GET'
    })
    .then(response => response.json());
}

export default generateUserPopUp;