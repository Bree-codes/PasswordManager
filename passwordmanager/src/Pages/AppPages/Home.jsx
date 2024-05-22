import {Outlet} from "react-router-dom";
<<<<<<< HEAD
import "./../styling/HomePage.css"

export const Home =()=>{
    return(
        <>
            <div className={"home-header"}>
                hello
            </div>
            <Outlet/>
=======
import secure from "../pics/secure.jpeg"
import "../styling/Home.css"
export const Home =()=>{
    return(
        <>
            <div className="container">
                <div className="content">
                    <h2 className="head">Say goodbye to forgetting passwords</h2>
                    <h3 className="sub-head">
                        <span className="span-1">Make life</span> <br/>
                        <span className="span-1" >simple & safe</span>
                    </h3>
                </div>
                <div className="image">
                    <img src={secure}  alt={" Security pic"} />
                </div>
            </div>


            <Outlet />
>>>>>>> upstream/main
        </>
    );
}