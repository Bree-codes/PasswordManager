
import './App.css';
import {SignUp} from "./Pages/SignUp";
import {Login} from "./Pages/Login";
import {Route, Routes} from "react-router-dom";

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
