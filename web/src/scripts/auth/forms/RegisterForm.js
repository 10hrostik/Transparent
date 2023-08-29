import React from "react";
import apiServer from "../../utils/ApiServer";

function RegisterForm(props) {
    const registerUrl = apiServer + 'public/auth/register';

    const register = (event) => {
        event.preventDefault();
        let registerData;
        const credential = event.target.credential.value;
        if (Number(credential)) {
            registerData = { number: Number(credential),
                password: event.target.password.value };
        } else {
            registerData = { credential: credential,
                password: event.target.password.value };
        }

        fetch(registerUrl ,{
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: 'POST',
            body : JSON.stringify(registerData)
        }).then((response) => response.json())
            .then(user => {
                localStorage.setItem('user', JSON.stringify(user))
                localStorage.setItem('token', user.token)
                props.setUser(user);
            })
            .catch(e => console.log(e))
    }

    const handleAuthLayout = () => {
        props.toggleToLogin(false);
    }

    return (
        <div className={'authFormPopUp'}>
            <h1>Transparent</h1>
            <form onSubmit={register} method="POST">
                <input id="registerCredential" className={'authInput'} type="text" placeholder={'phone // email'}
                       name="credential" required></input>
                <br/>
                <input id="registerPassword" className={'authInput'} style={{marginBottom: 5}}
                       type="password" placeholder={'password'} name="password" required></input>
                <br/>
                <b onClick={handleAuthLayout} className={'forgotPasswordLabel'}>Back to login</b>
                <br/>
                <button id="registerButton" className={'registerButton'} type="submit">Register</button>
            </form>
        </div>
    )
}

export default RegisterForm