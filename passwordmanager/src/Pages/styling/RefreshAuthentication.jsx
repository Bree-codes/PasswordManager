import {Outlet, useLocation} from "react-router-dom";

const RefreshAuthentication = ({redirectPath="/home", children}) => {
    const location = useLocation();

    if(false){
        console.log("token refresh")
    }

    return <Outlet/> || children;
}