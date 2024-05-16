import {Link} from "react-router-dom";
import {Container, Nav, Navbar} from "react-bootstrap";

export const NavigationBar = () => {
    return (
            <Navbar expand="lg" className="bg-body-tertiary">
                <Container fluid>
                    <Navbar.Toggle aria-controls="navbarScroll" />
                    <Navbar.Collapse id="navbarScroll">
                        <Nav navbarScroll>
                            <Link to="/SignUp">SignUp</Link>
                            <Link to="/Login">Login</Link>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>);
}
