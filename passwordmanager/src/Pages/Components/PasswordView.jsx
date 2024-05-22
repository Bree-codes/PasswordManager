import {Form} from "react-bootstrap";
import "./../styling/HomePage.css"

const PasswordView = ({websiteName, username, password, doEdit}) => {
    return (<div className={"password-view"}>
                <Form className={"details-view-form"}>
                    <Form.Group>
                        <Form.Label className={"website-name"} htmlFor={"website-name"}>Website</Form.Label>
                        <Form.Control id="website-name" type={"text"} disabled={true} value={"website name"} />
                    </Form.Group>

                    <Form.Group>
                        <Form.Label className={"username"} htmlFor={"Username"}>Username</Form.Label>
                        <Form.Control id="Username" type={"text"} disabled={true} value={"username"} />
                    </Form.Group>

                    <Form.Group>
                        <Form.Label className={"password"} htmlFor={"Password"}>Password</Form.Label>
                        <Form.Control id="Password"  type={"text"} disabled={true} value={"website name"} />
                    </Form.Group>
                </Form>
            </div>);
}

export default PasswordView;