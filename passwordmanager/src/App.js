import {Route, Routes} from "react-router-dom";
import {SignUp} from "./Pages/SignUp";
import {Login} from "./Pages/Login";


function App() {
  return (
    <div className="App">
        <Routes>
            <Route path="/SignUp" element={<SignUp/>}/>
            <Route path="/Login" element={<Login/>}/>
        </Routes>
    </div>
  );
}

export default App;
