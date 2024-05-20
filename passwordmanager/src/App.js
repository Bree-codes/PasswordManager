import {Outlet, Route, Routes} from "react-router-dom";
import {SignUp} from "./Pages/SignUp";
import {Login} from "./Pages/Login";
import {NavigationBar} from "./Pages/NavigationBar";
import RefreshAuthentication from "./Pages/styling/RefreshAuthentication";

import ProtectedRoutes from "./Pages/Components/ProtectedRoutes";
import {Home} from "./Pages/Home";


function App() {
  return (
    <div className="App">
        <Routes>
            <Route path={"/"}  element={(<RefreshAuthentication><NavigationBar /></RefreshAuthentication>)}>
                <Route path="SignUp" element={<SignUp/>}/>
                <Route path="Login" element={<Login/>}/>
                <Route path={"Home"} element={<ProtectedRoutes><Home/></ProtectedRoutes>}>
                    <Route path={"view/passwords"} element={<h1>password view page.</h1>} />
                </Route>
                <Route path={"*"} element={<h1>Page Not Found</h1>} />
            </Route>
        </Routes>
    </div>);
}

export default App;
