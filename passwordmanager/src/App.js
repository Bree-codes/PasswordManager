import {Route, Routes} from "react-router-dom";
import {SignUp} from "./Pages/SignUp";
import {Login} from "./Pages/Login";
import {NavigationBar} from "./Pages/NavigationBar";
function App() {
  return (
    <div className="App">
        <NavigationBar/>
        <Routes>
            <Route path="/SignUp" element={<SignUp/>}/>
            <Route path="/Login" element={<Login/>}/>
        </Routes>
    </div>
  );
}

export default App;
