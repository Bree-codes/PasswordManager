import {Link} from "react-router-dom";
import {Container, Nav, Navbar} from "react-bootstrap";
import "./styling/NavigationBar.css"
export const NavigationBar = () => {
    return (
            <Navbar expand="md" className="bg-body-tertiary">
                <Container fluid>
                    <Navbar.Toggle aria-controls="navbarScroll"/>
                    <div className={"home-md"}>
                        <div className={"home-icon"}><Link to="/">Home</Link></div>
                        <div className={"links"}>
                            <div className="sign"><Link to="/SignUp">SignUp</Link></div>
                            <div className="login"><Link to="/Login">Login</Link></div>
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
            </Navbar>);
}
