import {Navigate, Outlet, useLocation} from "react-router-dom";
import {refreshToken} from "../DataSource/backendUtils";
import {useEffect} from "react";

function RefreshAuthentication({redirectPath="/home", children}){
    const location = useLocation();

    useEffect(() => {
        refreshToken().then((response) => {
            sessionStorage.setItem("isLoggedIn", "true");
            sessionStorage.setItem("id", response.data.userId);
            sessionStorage.setItem("token", response.data.token);
            sessionStorage.setItem("username", response.data.username);


        }).catch(() => {
            console.log("Refresh Token Failed")
        })

    }, []);

    if(sessionStorage.getItem("isLoggedIn")){
        return <Navigate to={redirectPath} replace state={{from:location}} />
    }else {
        return children || <Outlet />;
    }

}

export default RefreshAuthentication;
