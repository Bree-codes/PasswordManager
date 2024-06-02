import {Navigate, Outlet, useLocation, useNavigate} from "react-router-dom";
import {refreshToken} from "../DataSource/backendUtils";
import {useEffect} from "react";

function RefreshAuthentication({redirectPath="/home/view/passwords", children}){
    const navigate = useNavigate();

    useEffect(() => {
        if(!sessionStorage.getItem("isLoggedIn")) {

            refreshToken().then((response) => {
                sessionStorage.setItem("token", response.data.token);
                sessionStorage.setItem("id", response.data.userId);
                sessionStorage.setItem("username", response.data.username);
                sessionStorage.setItem("isLoggedIn", "true");
                navigate(redirectPath);
            }).catch(() => {
                sessionStorage.setItem("isLoggedIn", "");
                sessionStorage.setItem("id", "");
                sessionStorage.setItem("token", "");
                sessionStorage.setItem("username", "");
            })
        }
    }, []);

    return children || <Outlet />;
}
export default RefreshAuthentication;
