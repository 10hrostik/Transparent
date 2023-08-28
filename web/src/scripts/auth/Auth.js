import '../../styles/forms/auth.css'
import LoginForm from "./forms/LoginForm";
import {useState} from "react";
import RegisterForm from "./forms/RegisterForm";

function Auth(props) {
    const [toRegister, setToRegister] = useState(false);

    return (
        <div className={'rootAuthForm'}>
            {toRegister === false ? <LoginForm toggleToRegister = {setToRegister} setUser = {props.setUser}/>
                : <RegisterForm toggleToLogin = {setToRegister} setUser = {props.setUser}/>}
        </div>
    );
}

export default Auth;
