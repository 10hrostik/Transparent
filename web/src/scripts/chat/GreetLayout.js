import React, {useState} from "react";
import '../../styles/greetLayoutOut/sideBar.css';
import generateLogoutPopUp from "../utils/GenerateLogoutPopUp";

function GreetLayout(props) {
    const handleLogout = () => {
        generateLogoutPopUp(props.setUser);
    }

    return (
        <div id='sideBar' className="sideBar">
                <button id='logoutImageButton' className='logoutImageButton'>
                    <img onClick={handleLogout} id='logoutImage' className='logoutImage'
                         src={require('../../images/logout.png')} height={35} width={30} alt={''}></img>
                </button>
        </div>
    )
}

export default GreetLayout;