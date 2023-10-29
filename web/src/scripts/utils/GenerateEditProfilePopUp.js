import '../../styles/forms/edit.css';
import apiServer from "./ApiServer";

let images;
let image = [];

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
    createForm(element).then();

    return element;
}

async function createForm (header) {
    let photoLayout = document.createElement('div');
    photoLayout.id = 'photoLayout';
    photoLayout.className = 'editPhotoContainer';
    const userId = JSON.parse(sessionStorage.getItem('user')).id;
    images = await getPhotos(userId);
    image = await fetch(apiServer + "secured/attachment/photos/user/" + userId, {
        headers: {
            'Authorization': 'Bearer ' + sessionStorage.getItem('token')
        },
        method: 'GET'
    }).then(response => response.blob())
    let photo = document.createElement('img');
    photo.src = URL.createObjectURL(image);
    photo.className = 'mainUserPhoto';
    photoLayout.appendChild(photo)

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

async function getPhotos (id) {
    return await fetch(apiServer + "secured/attachment/photos/user/all/" + id, {
        headers: {
            'Authorization': 'Bearer ' + sessionStorage.getItem('token')
        },
        method: 'GET'
    })
        .then(response => response.json());
}

export default generateUserPopUp;