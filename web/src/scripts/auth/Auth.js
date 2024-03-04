import '../../styles/forms/auth.css';
import LoginForm from "./forms/LoginForm";
import {useState} from "react";
import RegisterForm from "./forms/RegisterForm";
import RestorePasswordForm from "./forms/RestorePasswordForm";

function Auth(props) {
  const [toRegister, setToRegister] = useState(false);
  const [toRestorePassword, setToRestorePassword] = useState(false);

  return (
      <div className={'rootAuthForm'}>
        {toRegister === false ? toRestorePassword ?
                <RestorePasswordForm toggleToLogin={setToRestorePassword}/>
                : <LoginForm toggleToRestore={setToRestorePassword}
                             toggleToRegister={setToRegister} setUser={props.setUser}/>
            : <RegisterForm toggleToLogin={setToRegister} setUser={props.setUser}/>}
      </div>
  );
}

export default Auth;
