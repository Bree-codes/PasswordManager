import {Outlet, Route, Routes} from "react-router-dom";
import {SignUp} from "./Pages/SignUp";
import {Login} from "./Pages/Login";
import {NavigationBar} from "./Pages/NavigationBar";
import RefreshAuthentication from "./Pages/styling/RefreshAuthentication";

function App() {
  return (
    <div className="App">
        <Routes>
            <Route path={"/"}  element={(<RefreshAuthentication><NavigationBar /></RefreshAuthentication>)}>
                <Route path="SignUp" element={<SignUp/>}/>
                <Route path="Login" element={<Login/>}/>
            </Route>
            <Route path={"home"} element={<><h1>Home page Nav bar</h1><Outlet/></>}>
                <Route path={"view/passwords"} element={<h2>password view page.</h2>} />
            </Route>
            <Route path={"*"} element={<h1>Page Not Found</h1>} />
        </Routes>
    </div>);
}

export default App;
