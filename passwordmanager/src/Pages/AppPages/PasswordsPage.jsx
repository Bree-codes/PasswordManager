import "./../styling/HomePage.css"
import PasswordLink from "../Components/PasswordLink";
import PasswordView from "../Components/PasswordView";
import {useState} from "react";

const PasswordsPage = () => {
    const [websiteName, setWebSiteName] = useState("google.com");
    const [webSiteUsername, setWebSiteUsername] = useState("steve");
    const [webSitePassword, setWebSitePassword] = useState("muish!");



    return(
        <div className={"view-passwords"}>
            <div className={"passwords-page"}>
                <PasswordLink />
            </div>
            <div className={"password-display"}>
               <PasswordView username={webSiteUsername} setUsername={setWebSiteUsername}
                             password={webSitePassword} setPassword={setWebSitePassword}
                            websiteName={websiteName} />
            </div>
        </div>);
}

export default PasswordsPage;
