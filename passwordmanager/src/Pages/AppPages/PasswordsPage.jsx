import "./../styling/HomePage.css"
import PasswordLink from "../Components/PasswordLink";
import PasswordView from "../Components/PasswordView";
import {useState} from "react";

const PasswordsPage = () => {
    const [websiteName, setWebSiteName] = useState("");
    const [webSiteUsername, setWebSiteUsername] = useState("");
    const [webSitePassword, setWebSitePassword] = useState("");



    return(
        <div className={"view-passwords"}>
            <div className={"passwords-page"}>
                <PasswordLink />
            </div>
            <div className={"password-display"}>
               <PasswordView />
            </div>
        </div>);
}

export default PasswordsPage;
