import React, {useState} from "react";
import '../../styles/greetLayoutOut/sideBar.css';
import '../../styles/greetLayoutOut/topBar.css';
import generateLogoutPopUp from "../utils/GenerateLogoutPopUp";
import generateUserPopUp from "../utils/GenerateEditProfilePopUp";

function GreetLayout(props) {
    const handleLogout = () => {
        generateLogoutPopUp(props.setUser);
    }

    const handleProfileButton = () => {
        generateUserPopUp();
    }

    const toggleSideBar = () => {
        let sidebar = document.querySelector('.sideBar');
        let sidebarWidth = sidebar.offsetWidth;
        if (sidebar.style.left === '0px') {
            sidebar.style.left = `-${sidebarWidth}px`;
        } else {
            sidebar.style.left = '0px';
        }
    }

    return (
        <>
            <div id='topBar' className='topBar'>
                <button onClick={toggleSideBar} id='menuButton' className='topBarButton'>
                    <img id='menuImage' className='topBarImage'
                         src={require('../../images/menu.png')} height={35} width={30} alt={''}></img>
                </button>
            </div>
            <div id='sideBar' className="sideBar">
                <div id='profilePanel' className='profilePanel'>
                    <button onClick={handleProfileButton} id='logoutImageButton' className='sideBarImageProfileButton'>
                        <img id='logoutImage' className='sideBarImage'
                             src={require('../../images/no-photo-profile.png')} height={35} width={30} alt={''}></img>
                    </button>
                </div>
                <div id='controlPanel' className="controlPanel">
                    <button id='logoutImageButton' className='sideBarImageButton'>
                        <img id='logoutImage' className='sideBarImageImage'
                             src={require('../../images/settings.png')} height={35} width={30} alt={''}></img>
                    </button>
                    <button onClick={handleLogout} id='logoutImageButton' className='sideBarImageButton'>
                        <img id='logoutImage' className='sideBarImage'
                             src={require('../../images/logout.png')} height={35} width={30} alt={''}></img>
                    </button>
                </div>
            </div>
        </>
    )
}

export default GreetLayout;