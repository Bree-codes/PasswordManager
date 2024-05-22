import {Route, Routes} from "react-router-dom";

import {SignUp} from "./Pages/AppPages/SignUp";
import {Login} from "./Pages/AppPages/Login";
import {NavigationBar} from "./Pages/Components/NavigationBar";
import RefreshAuthentication from "./Pages/Components/RefreshAuthentication";


import ProtectedRoutes from "./Pages/Components/ProtectedRoutes";
import {Home} from "./Pages/AppPages/Home";


import PasswordsPage from "./Pages/AppPages/PasswordsPage";
import {Footer} from "./Pages/AppPages/Footer";
import {VerifyEmail} from "./Pages/AppPages/VerifyEmail";


function App() {
  return (
      <>

        <Routes>
            <Route path={"/"}  element={(<RefreshAuthentication><NavigationBar /></RefreshAuthentication>)}>
                <Route index element={<Home/>}/>
                <Route path={"SignUp"} element={<SignUp/>} />
                <Route path={"verify/email"} element={<VerifyEmail/>} />
                <Route path={"Login"} element={<Login/>} />
            </Route>
            <Route path={"home"} element={<ProtectedRoutes><Home/></ProtectedRoutes>}>
                <Route path={"view/passwords"} element={<PasswordsPage />} />
            </Route>
            <Route path={"*"} element={<h1>Page Not Found</h1>} />
        </Routes>
          <Footer/>
      </>);
}

export default App;
