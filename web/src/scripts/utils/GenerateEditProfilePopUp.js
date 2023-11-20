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

async function getPhotos (id) {
    return await fetch(apiServer + "secured/attachments/photos/user/" + id + "/all/", {
        headers: {
            'Authorization': 'Bearer ' + sessionStorage.getItem('token')
        },
        method: 'GET'
    })
        .then(response => response.json());
}

async function getPhotoLayout(header) {
    let photoLayout = document.createElement('div');
    photoLayout.id = 'photoLayout';
    photoLayout.className = 'editPhotoContainer';
    const userId = JSON.parse(sessionStorage.getItem('user')).id;
    images = await getPhotos(userId);
    image = await fetch(apiServer + "secured/attachments/photos/user/" + userId, {
        headers: {
            'Authorization': 'Bearer ' + sessionStorage.getItem('token')
        },
        method: 'GET'
    }).then(response => response.blob())
    let photo = document.createElement('img');
    photo.src = URL.createObjectURL(image);
    photo.className = 'mainUserPhoto';
    photoLayout.appendChild(photo);
    header.appendChild(photoLayout)
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

export default generateUserPopUp;