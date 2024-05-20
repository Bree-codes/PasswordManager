import {Outlet} from "react-router-dom";
import secure from "../pics/secure.jpeg"
export const Home =()=>{
    return(
        <>
            <div className="container">
                <div className="content">
                    <h1>Say goodbye to forgetting passwords</h1>
                    <h2>
                        <span>Make life</span>
                        <span>simple & safe</span>
                    </h2>
                </div>
                <div className="image">
                    <img src={secure}  alt={" Security pic"} />
                </div>
            </div>
            );


            <Outlet />
        </>
    );
}