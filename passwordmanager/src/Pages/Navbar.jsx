import { Link } from "react-router-dom";

export const Navbar = () => {
    return (
        <nav>
            <ul>
                <li><Link to="/">Home</Link></li>
                <li><Link to="/SignUp">SignUp</Link></li>
                <li><Link to="/Login">Login</Link></li>
            </ul>
        </nav>
    );
}
