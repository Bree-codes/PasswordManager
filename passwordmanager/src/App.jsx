import {Route, Routes} from "react-router-dom";

import {SignUp} from "./Pages/AppPages/SignUp";
import {Login} from "./Pages/AppPages/Login";
import {NavigationBar} from "./Pages/Components/NavigationBar";
import RefreshAuthentication from "./Pages/Components/RefreshAuthentication";


import ProtectedRoutes from "./Pages/Components/ProtectedRoutes";
import {Home} from "./Pages/AppPages/Home";
import {Footer} from "./Pages/AppPages/Footer";


function App() {
  return (
      <>

        <Routes>

            <Route path={"/"}  element={(<RefreshAuthentication><NavigationBar /></RefreshAuthentication>)}>
                <Route index element={<Home/>}/>
                <Route path="SignUp" element={<SignUp/>}/>
                <Route path="Login" element={<Login/>}/>
            </Route>
            <Route path={"home"} element={<ProtectedRoutes><Home/></ProtectedRoutes>}>
                <Route path={"view/passwords"} element={<h1>password view page.</h1>} />
            </Route>
            <Route path={"*"} element={<h1>Page Not Found</h1>} />
        </Routes>
          <Footer/>
      </>);
}

export default App;
