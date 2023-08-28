import React, {useEffect, useState} from "react";
import Auth from "./auth/Auth";
import '../styles/MainLayout.css'
import GreetLayout from "./chat/GreetLayout";

function MainLayout() {
    const [user, setUser] = useState(localStorage.getItem('user'));
    const [layout, setLayout] = useState(null);

    useEffect(() => {
        if (user) {
            setLayout(<GreetLayout user = {user}/>)
        } else {
            setLayout(<Auth setUser = {setUser}/>)
        }
    }, [user]);


    return (
        <div id={'mainLayout'} className={'mainLayout'}>
            {layout}
        </div>
    )
}

export default MainLayout;