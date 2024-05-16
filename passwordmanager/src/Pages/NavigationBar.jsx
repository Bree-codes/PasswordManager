import {Link} from "react-router-dom";
import {Container, Nav, Navbar} from "react-bootstrap";
import "./styling/NavigationBar.css"

export const NavigationBar = () => {
    return (
            <Navbar expand="lg" className="bg-body-tertiary">
                <Container fluid>
                    <Navbar.Toggle aria-controls="navbarScroll" />
                    <Navbar.Collapse id="navbarScroll">
                        <Nav  className="nav" navbarScroll>
                            <ul>
                          <li>  <Link className="link" to="/">Home</Link> </li>
                                <li><Link className="link" to="/SignUp">SignUp</Link> </li>
                                <li><Link className="link" to="/Login">Login</Link></li>
                            </ul>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>);
}
