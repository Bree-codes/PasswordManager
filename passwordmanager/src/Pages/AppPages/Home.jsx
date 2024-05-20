import {Outlet} from "react-router-dom";

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
                    <img src="secure-image.jpg" alt="Secure Image" />
                </div>
            </div>
            );


            <Outlet />
        </>
    );
}