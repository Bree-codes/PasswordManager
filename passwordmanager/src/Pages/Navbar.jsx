import { Link } from "react-router-dom";
import "./styling/Navbar.css"
export const Navbar = () => {
    return (
        <nav className="nav">
            <ul>
                <li><Link to="/">Home</Link></li>
                <li className="sign"><Link to="/SignUp">SignUp</Link></li>
                <li  className="login"><Link to="/Login">Login</Link></li>
            </ul>
        </nav>
    );
}
