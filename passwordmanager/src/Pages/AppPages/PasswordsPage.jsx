import "./../styling/HomePage.css"
import PasswordLink from "../Components/PasswordLink";

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
            </div>
            <div className={"password-display"}>
                view ...
            </div>
        </div>);
}

export default PasswordsPage;
