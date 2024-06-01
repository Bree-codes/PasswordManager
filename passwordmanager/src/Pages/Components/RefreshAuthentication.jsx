import {Navigate, Outlet, useLocation, useNavigate} from "react-router-dom";
import {refreshToken} from "../DataSource/backendUtils";
import {useEffect} from "react";

function RefreshAuthentication({redirectPath="/home", children}){
    const navigate = useNavigate();

    useEffect(() => {
        if(!sessionStorage.getItem("isLoggedIn")) {
            refreshToken().then((response) => {
                sessionStorage.setItem("isLoggedIn", "true");
                sessionStorage.setItem("id", response.data.userId);
                sessionStorage.setItem("token", response.data.token);
                sessionStorage.setItem("username", response.data.username);
                navigate(redirectPath);
            }).catch(() => {
                sessionStorage.setItem("isLoggedIn", "null");
                sessionStorage.setItem("id", "null");
                sessionStorage.setItem("token", "null");
                sessionStorage.setItem("username", "null");
                console.log("Refresh Token Failed")
            })
        }
    }, []);

    return children || <Outlet />;
}
export default RefreshAuthentication;
