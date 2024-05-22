import "./../styling/HomePage.css"
import PasswordLink from "../Components/PasswordLink";
import PasswordView from "../Components/PasswordView";

const PasswordsPage = () => {
    return(
        <div className={"view-passwords"}>
            <div className={"passwords-page"}>
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
                <PasswordLink />
            </div>
            <div className={"password-display"}>
               <PasswordView />
            </div>
        </div>);
}

export default PasswordsPage;
