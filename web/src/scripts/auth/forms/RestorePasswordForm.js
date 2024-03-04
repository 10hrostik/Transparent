import React from "react";
import apiServer from "../../utils/ApiServer";
import generateStatusTag from "../../utils/GenerateStatusTag";

function validatePassword(password) {
  if (password.length < 8) {
    return false;
  }
  return /\d/.test(password);
}

function RestorePasswordForm(props) {
  const restorePasswordURL = apiServer + 'public/auth/restore/password';

  const handleRestorePassword = (event) => {
    event.preventDefault();
    if (!validatePassword(event.target.newPassword.value)) {
      let error = document.getElementById('wrongRestorePasswordInput');
      let errorRepeated = document.getElementById('wrongRepeatedPasswordInput');
      if (error) error.remove();
      if (errorRepeated) errorRepeated.remove();
      if (!document.getElementById('wrongRepeatedPasswordInput'))
        generateStatusTag('wrongRepeatedPasswordInput',
            'wrongInput', 'Password should be 8 chars long and contains a digit!',
            'restorePasswordForm');
      return;
    }
    if (document.getElementById('successRestorePasswordInput'))
      return
    if (event.target.newPassword.value !== event.target.repeatNewPassword.value) {
      let error = document.getElementById('wrongRestorePasswordInput');
      let errorPassword = document.getElementById('wrongRepeatedPasswordInput');
      if (errorPassword) errorPassword.remove();
      if (error) error.remove();
      if (!document.getElementById('wrongRepeatedPasswordInput')) {
        generateStatusTag('wrongRepeatedPasswordInput',
            'wrongInput', "Passwords don't match!", 'restorePasswordForm');
      }
      return;
    }
    const request = {
      credential: event.target.credential.value,
      newPassword: event.target.newPassword.value
    }
    sendRequest(request);
  }

  const sendRequest = (request) => {
    fetch(restorePasswordURL, {
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'PATCH',
      body: JSON.stringify(request)
    }).then((response) => {
      if (response.status === 200) return response.text()
      throw 'Check your login name!';
    })
        .then(() => {
          let error = document.getElementById('wrongRestorePasswordInput');
          let errorPassword = document.getElementById('wrongRepeatedPasswordInput');
          if (errorPassword) errorPassword.remove();
          if (error) error.remove();
          generateStatusTag('successRestorePasswordInput',
              'successInput', 'Password restored successfully!', 'restorePasswordForm');
          setTimeout(handleAuthLayout, 4000);
        })
        .catch(e => {
          let errorPassword = document.getElementById('wrongRepeatedPasswordInput');
          if (errorPassword) errorPassword.remove();
          if (!document.getElementById('wrongRestorePasswordInput')) {
            generateStatusTag('wrongRestorePasswordInput',
                'wrongInput', e, 'restorePasswordForm');
            console.log(e)
          }
        });
  }

  const handleAuthLayout = () => {
    props.toggleToLogin(false);
  }

  return (
      <div id={'restorePasswordFormPopUp'} className={'restorePasswordFormPopUp'}>
        <h1>Transparent</h1>
        <form id={'restorePasswordForm'} autoComplete={'off'} onSubmit={handleRestorePassword} method="PATCH">
          <input id="registeredCredential" className={'authInput'} type="text" placeholder={'phone // email'}
                 name="credential" required autoComplete={'off'}></input>
          <br/>
          <input id="newPassword" className={'authInput'} type="password" placeholder={'password'}
                 name="newPassword" required autoComplete={'off'}></input>
          <br/>
          <input id="repeatNewPassword" className={'authInput'} autoComplete={'off'} style={{marginBottom: 5}}
                 type="password" placeholder={'repeat password'} name="repeatedPassword" required></input>
          <br/>
          <b onClick={handleAuthLayout} className={'forgotPasswordLabel'}>Back to login</b>
          <br/>
          <button id="restorePasswordButton"
                  className={'restorePasswordButton'} type="submit">Restore password
          </button>
        </form>
      </div>
  )
}

export default RestorePasswordForm;