import "./../styling/HomePage.css"
import PasswordView from "../Components/PasswordView";
import {useEffect, useState} from "react";
import {Button} from "react-bootstrap";
import {getPasswords} from "../DataSource/backendUtils";

const PasswordsPage = () => {
    const [websiteName, setWebSiteName] = useState("google.com");
    const [webSiteUsername, setWebSiteUsername] = useState("steve");
    const [webSitePassword, setWebSitePassword] = useState("muish!");


    useEffect(() => {

        getPasswords((response) => {
            //console.log(response.data);
        }).catch((error) => {
            //console.log(error.response);
        })
    }, []);



    return(
        <div className={"view-passwords"}>
            <div className={"passwords-page"}>
                <Button id={"title-holder"}>Website Name.</Button>
            </div>
            <div className={"password-display"}>
               <PasswordView username={webSiteUsername} setUsername={setWebSiteUsername}
                             password={webSitePassword} setPassword={setWebSitePassword}
                            websiteName={websiteName} />
            </div>
        </div>);
}

export default PasswordsPage;
