import {Route, Routes} from "react-router-dom";
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
            <Route path={"home"} element={<h1>Home page</h1>}>
                <Route path={"view/passwords"} element={<h1>password view page.</h1>} />
            </Route>
            <Route path={"*"} element={<h1>Page Not Found</h1>} />
            </Route>
        </Routes>
    </div>);
}

export default App;
