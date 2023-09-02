import React from "react";
import '../../styles/greetLayoutOut/sideBar.css'

function GreetLayout(props) {
    return (
        <div id='sideBar' className="sideBar">
                <button id='logoutImageButton' className='logoutImageButton'>
                    <img id='logoutImage' className='logoutImage' src={require('../../images/logout.png')} height={35} width={30}></img>
                </button>
        </div>
    )
}

export default GreetLayout;