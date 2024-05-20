import {Outlet} from "react-router-dom";

export const Home =()=>{
    return(
        <>
        <div>
        <div>
            <h1> Say goodbye to forgetting password</h1>
            <h2>
                <span> Make life</span>
                <span> simple & safe</span>

            </h2>
        </div>
            <div>

            </div>
        </div>

            <Outlet />
        </>
    );
}