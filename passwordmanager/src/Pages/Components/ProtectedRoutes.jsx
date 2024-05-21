import {useNavigate} from "react-router-dom";

function ProtectedRoutes({redirectPath="/login", children}){
    const navigate = useNavigate();

    if(!sessionStorage.getItem("isLoggedIn")){
        navigate(redirectPath);
    }
}

export default ProtectedRoutes;