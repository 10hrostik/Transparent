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
        let fileForm = document.createElement('form');
        fileForm.id = 'fileForm';
        fileForm.encoding = 'multipart/form-data';
        let fileInput = document.createElement('input');
        fileInput.style.display = 'none';
        fileInput.type = 'file';
        fileInput.id = 'fileInput';
        fileInput.name = "image";
        fileInput.accept = 'image/*';
        fileInput.onchange = () => uploadImage(fileForm, userId);
        fileForm.appendChild(fileInput)
        mainPhotoLayout.appendChild(fileForm);
        mainPhotoLayout.onclick = () => document.getElementById('fileInput').click();
    } else {
        photo.src = URL.createObjectURL(image);
    }
    mainPhotoLayout.appendChild(photo);
    photoLayout.appendChild(mainPhotoLayout);
    header.appendChild(photoLayout)
}

function uploadImage(form, userId) {
    const formData = new FormData(form);
    const apiUrl = apiServer + "secured/attachments/photos/user/" + userId;

    fetch(apiUrl, {
        headers: {
            'Authorization': 'Bearer ' + sessionStorage.getItem('token'),
            'Content-Type': 'multipart/form-data'
        },
        method: 'POST',
        body: formData,
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('API Response:', data);
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
    return fetch(apiServer + "secured/attachments/media/user/" + userId, {
        headers: {
            'Authorization': 'Bearer ' + sessionStorage.getItem('token')
        },
        method: 'GET'
    }).then(response => {
        if (response.status === 204) return null;

        return response.blob();
    })
}

async function getPhotos (id) {
    return await fetch(apiServer + "secured/attachments/media/user/" + id + "/all", {
        headers: {
            'Authorization': 'Bearer ' + sessionStorage.getItem('token')
        },
        method: 'GET'
    })
        .then(response => response.json());
}

export default generateUserPopUp;