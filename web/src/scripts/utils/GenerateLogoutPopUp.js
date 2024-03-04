import apiServer from "./ApiServer";
import refreshLoop from "./RefreshLoop";

function generateLogoutPopUp(callback) {
  let body = generateBody();
  generateHeader(body);
  generateBottom(body, callback);
}

function generateHeader(body) {
  let header = document.createElement('div');
  header.className = 'logoutHeader';
  header.id = 'logoutHeader';
  let text = document.createElement('div');
  text.style.fontSize = '24px';
  text.style.fontWeight = 'bold';
  text.style.marginLeft = '5px';
  text.textContent = 'Transparent';
  header.appendChild(text);
  body.appendChild(header)
}

function generateBody() {
  let element = document.createElement('div');
  let background = generateBackground();
  element.className = 'logoutPopUp';
  element.id = 'logoutPopUp';
  document.getElementById('mainLayout').appendChild(element);
  document.getElementById('mainLayout').appendChild(background);

  return element;
}

function generateBottom(body, callback) {
  let container = document.createElement('div');
  container.className = 'logoutBody';
  let text = document.createElement('p');
  text.textContent = 'Are you sure to logout?';
  text.style.fontSize = '18px';
  let cancelButton = document.createElement('button');
  cancelButton.id = 'cancelLogountButton';
  cancelButton.className = 'cancelLogoutButton';
  cancelButton.textContent = 'Cancel';
  cancelButton.onclick = () => {
    document.getElementById('logoutPopUp').remove();
    document.getElementById('backgroundWindow').remove();
  }
  let logoutButton = document.createElement('button');
  logoutButton.id = 'cancelButton';
  logoutButton.className = 'logoutButton';
  logoutButton.textContent = 'Log out';
  logoutButton.onclick = () => logout(callback);
  container.appendChild(text);
  container.appendChild(cancelButton);
  container.appendChild(logoutButton);
  body.appendChild(container);
}

function logout(callback) {
  const logoutURL = apiServer + 'secured/user/logout';
  fetch(logoutURL, {
    headers: {
      'Authorization': 'Bearer ' + sessionStorage.getItem('token')
    },
    method: 'GET'
  }).then((response) => response.text())
      .then(() => {
        document.getElementById('logoutPopUp').remove();
        document.getElementById('backgroundWindow').remove();
        sessionStorage.clear();
        clearInterval(refreshLoop.fun);
        callback(null);
      })
      .catch(e => {
        console.log(e);
        document.getElementById('logoutPopUp').remove();
        document.getElementById('backgroundWindow').remove();
        sessionStorage.clear();
        clearInterval(refreshLoop.fun);
        callback(null);
      })
}

function generateBackground() {
  let windowElement = document.createElement('div');
  windowElement.id = 'backgroundWindow'
  windowElement.className = 'sideBarIconWindow';
  windowElement.onclick = () => {
    windowElement.remove();
    document.getElementById('logoutPopUp').remove()
  }

  return windowElement;
}

export default generateLogoutPopUp;