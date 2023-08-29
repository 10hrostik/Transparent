import apiServer from "../../utils/ApiServer";
import React from "react";

function LoginForm(props) {
    const loginURL = apiServer + 'public/auth/login/credential';

    const login = (event) => {
        event.preventDefault();
        fetch(loginURL + "?username=" + event.target.username.value
            + "&password=" + event.target.password.value, {
            method: 'POST'
        }).then((response) => response.json())
            .then(user => {
                localStorage.setItem('user', JSON.stringify(user))
                localStorage.setItem('token', user.token)
                props.setUser(user);
            })
            .catch(e => {
                    let el = document.createElement("b");
                    el.id = 'wrongInput';
                    el.className = 'wrongInput';
                    el.textContent = 'Incorrect username or password';
                    document.getElementById('loginForm')
                        .appendChild(document.createElement('br')).appendChild(el);
                    document.getElementById('loginForm').appendChild(el)
                    console.log(e)
                }
            )
    }

    const handleAuthLayout = () => {
        props.toggleToRegister(true);
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
                <b className={'doubleSlash'}>{ ' // ' } </b>
                <b className={'forgotPasswordLabel'}>Forgot password?</b>
                <br/>
                <button id="loginButton" className={'loginButton'} type="submit">Login</button>
            </form>
        </div>
    )
}

export default LoginForm