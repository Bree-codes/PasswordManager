import {Navigate, Outlet, useLocation} from "react-router-dom";

function ProtectedRoutes({redirectPath="/login", children}){
    const location = useLocation();

    if(!sessionStorage.getItem("isLoggedIn")){
        return <Navigate to={redirectPath} replace state={{from:location}} />;
    }
    /*return children || <Outlet />;*/
}

export default ProtectedRoutes;