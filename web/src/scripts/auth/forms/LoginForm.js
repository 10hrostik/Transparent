import apiServer from "../../utils/ApiServer";
import React from "react";
import generateStatusTag from "../../utils/GenerateStatusTag";

function LoginForm(props) {
  const loginURL = apiServer + 'public/auth/login';

  const login = (event) => {
    event.preventDefault();
    fetch(loginURL + "?username=" + event.target.username.value
        + "&password=" + event.target.password.value, {
      method: 'POST'
    }).then((response) => response.json())
        .then(user => {
          if (user.error) throw new Error("Something went wrong");
          sessionStorage.setItem('user', JSON.stringify(user))
          sessionStorage.setItem('token', user.token)
          props.setUser(JSON.stringify(user));
        })
        .catch(e => {
          if (!document.getElementById('wrongLoginInput')) {
            generateStatusTag('wrongLoginInput',
                'wrongInput', 'Incorrect username or password!', 'loginForm');
            console.log(e)
          }
        })
  }

  const handleAuthLayout = () => {
    props.toggleToRegister(true);
  }

  const handleRestorePasswordLayout = () => {
    props.toggleToRestore(true);
  }

  return (
      <div className={'authFormPopUp'}>
        <h1>Transparent</h1>
        <form id='loginForm' onSubmit={login} method="POST">
          <input id="username" className={'authInput'} type="text" placeholder={'username // email'}
                 name="username" required></input>
          <br/>
          <input id="password" className={'authInput'} style={{marginBottom: 5}}
                 type="password" placeholder={'password'} name="password" required></input>
          <br/>
          <b onClick={handleAuthLayout} className={'forgotPasswordLabel'}>Sign up</b>
          <b className={'doubleSlash'}>{' // '} </b>
          <b onClick={handleRestorePasswordLayout} className={'forgotPasswordLabel'}>Forgot password?</b>
          <br/>
          <button id="loginButton" className={'loginButton'} type="submit">Login</button>
        </form>
      </div>
  )
}

export default LoginForm