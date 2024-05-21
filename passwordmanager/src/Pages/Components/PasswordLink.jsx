import "./../styling/HomePage.css"
import {Button} from "react-bootstrap";

const PasswordLink = ({websiteName}) => {
    return(
        <Button id="title-holder" className={"password-link"}>
            Website Name.
        </Button>);
}

export default PasswordLink;