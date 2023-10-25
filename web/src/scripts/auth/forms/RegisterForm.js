import React from "react";
import apiServer from "../../utils/ApiServer";
import generateStatusTag from "../../utils/GenerateStatusTag";

function validateEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function validatePhone(phone) {
    return phone.length === 12;
}

function validatePassword(password) {
    if (password.length < 8) {
        return false;
    }
    return /\d/.test(password);
}


function RegisterForm(props) {
    const registerUrl = apiServer + 'public/auth/register';

    const buildRequest = (credential, password) => {
        let registerData;
        if (Number(credential)) {
            if (validatePhone(credential)) {
                registerData = { number: Number(credential) };
            } else {
                if (document.getElementById('wrongRegisterEmailInput')) document.getElementById('wrongRegisterEmailInput').remove();
                if (document.getElementById('wrongRegisterPasswordInput')) document.getElementById('wrongRegisterPasswordInput').remove();
                if (!document.getElementById('wrongRegisterPhoneInput')) 
                    generateStatusTag('wrongRegisterPhoneInput',
                    'wrongInput', 'Wrong phone number format!', 'registerForm');
            
                return;
            }
        } else {
            if (validateEmail(credential)) {
                registerData = { credential: credential }
            } else {
                if (document.getElementById('wrongRegisterPhoneInput')) document.getElementById('wrongRegisterPhoneInput').remove();
                if (document.getElementById('wrongRegisterPasswordInput')) document.getElementById('wrongRegisterPasswordInput').remove();
                if (!document.getElementById('wrongRegisterEmailInput'))
                    generateStatusTag('wrongRegisterEmailInput',
                        'wrongInput', 'Wrong email format!', 'registerForm');
                return;
            }
        }
        if (validatePassword(password)) {
            registerData.password = password;
            return registerData;
        } else {
            if (document.getElementById('wrongRegisterPhoneInput')) document.getElementById('wrongRegisterPhoneInput').remove();
            if (document.getElementById('wrongRegisterEmaildInput')) document.getElementById('wrongRegisterEmailInput').remove();
            if (!document.getElementById('wrongRegisterPasswordInput'))
                generateStatusTag('wrongRegisterPasswordInput',
                    'wrongInput', 'Password should be 8 chars long and contains a digit!',
                    'registerForm');
        }
    }

    const register = (event) => {
        event.preventDefault();
        let registerData = buildRequest(event.target.credential.value, event.target.password.value);
        if (!registerData) return;
        fetch(registerUrl ,{
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: 'POST',
            body : JSON.stringify(registerData)
        }).then((response) => response.json())
            .then(user => {
                sessionStorage.setItem('user', JSON.stringify(user))
                sessionStorage.setItem('token', user.token)
                props.setUser(user);
            })
            .catch(e => {
                if (!document.getElementById('wrongRegisterInput')) {
                    generateStatusTag('wrongRegisterInput',
                        'wrongInput', 'User already exists!', 'registerForm');
                    console.log(e)
                }
            })
    }

    const handleAuthLayout = () => {
        props.toggleToLogin(false);
    }

    return (
        <div className={'authFormPopUp'}>
            <h1>Transparent</h1>
            <form id={'registerForm'} onSubmit={register} method="POST">
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