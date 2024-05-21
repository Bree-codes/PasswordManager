import {Outlet} from "react-router-dom";
import "./../styling/HomePage.css"

export const Home =()=>{
    return(
        <>
            <div className={"home-header"}>
                hello
            </div>
            <Outlet/>
        </>
    );
}