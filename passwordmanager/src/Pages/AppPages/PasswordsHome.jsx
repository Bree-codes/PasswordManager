import {Outlet} from "react-router-dom";

const PasswordsHome = () => {
    return (
        <>
            <div className={"home-header"}>
                Home page header....
            </div>
            <Outlet />
        </>);
}

export default PasswordsHome;