import {Link, Outlet} from "react-router-dom";
import {Container, Nav, Navbar} from "react-bootstrap";
import "../styling/NavigationBar.css"
import {Footer} from "../AppPages/Footer";
export const NavigationBar = () => {
    return (
        <>
            <Navbar expand="md" className="bg-body-tertiary" id={"home-navbar"}>
                <Container fluid className={"container"}>
                    <Navbar.Toggle aria-controls="navbarScroll"/>
                    <div className={"home-md"}>
                        <div className={"home-icon"}> <ul><li><Link to="/">Home</Link></li> </ul></div>
                        <div className={"links"}>
                            <ul>
                            <div className="sign"><li><Link to="/SignUp">SignUp</Link></li></div>  </ul>
                           <ul> <div className="login"><li><Link to="/Login">Login</Link></li></div> </ul>

                        </div>
                    </div>
                    <Navbar.Collapse id="navbarScroll">
                        <Nav className="nav" navbarScroll>
                            <ul className={"home"}>
                                <li className={"home-icon"}><Link className="link" to="/">Home</Link></li>
                                <ul className={"links"}>
                                    <li className="sign"><Link to="/SignUp">SignUp</Link></li>
                                    <li className="login"><Link to="/Login">Login</Link></li>
                                </ul>
                            </ul>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>

            <Outlet></Outlet>
            <Footer/>
        </>);
}
