import {Button, Col, Form, Image} from "react-bootstrap";
import "./../styling/HomePage.css"
import eyeSlash from "./../pics/eye-slash-solid.svg"
import eye from "./../pics/eye-solid.svg"
import {useState} from "react";

const PasswordView = ({websiteName, username, password, doEdit}) => {
    const [see, setSee] = useState(false);


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
                        <div id={"password-view"}>
                            <Form.Control id="Password"  type={"text"} disabled={true} value={"website name"} />
                            {see ? <Button onClick={() => setSee(false)}
                                           id={"view"}><Image src={eye} width={20} height={20} /></Button> :
                            <Button onClick={() => setSee(true)}
                                    id={"view"}><Image src={eyeSlash} width={20} height={20} /></Button>}
                        </div>
                    </Form.Group>
                </Form>
            </div>);
}

export default PasswordView;