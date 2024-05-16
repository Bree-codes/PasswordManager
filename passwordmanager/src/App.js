import {Route, Routes} from "react-router-dom";
import {SignUp} from "./Pages/SignUp";
import {Login} from "./Pages/Login";
import {Navbar} from "./Pages/Navbar";
function App() {
  return (
    <div className="App">
        <Navbar/>
        <Routes>
            <Route path="/SignUp" element={<SignUp/>}/>
            <Route path="/Login" element={<Login/>}/>
        </Routes>
    </div>
  );
}

export default App;
