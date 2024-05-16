import "./styling/SignUp.css"
import {Button, Form} from "react-bootstrap";
import {useState} from "react";
export const SignUp = () => {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");




    return(
        <div className="Registration-component">

            <Form className={"registration-form"}>

                <Form.Label className={"reg-title"}>Registration Form</Form.Label>

                <Form.Group>
                    <Form.Label className="username" htmlFor="username"> Username:</Form.Label>
                    <Form.Control id="username" type="text"
                                  name="Username" placeholder="Username" value={username}
                    onChange={(e) => {setUsername(e.target.value)}}/>
                </Form.Group>

                <Form.Group>
                    <Form.Label className="email" htmlFor="email"> Email:</Form.Label>
                    <Form.Control id={"email"} type="email" name="Email" placeholder="email"
                    value={email}
                    onChange={(e) => {setEmail(e.target.value)}}/>
                </Form.Group>

                <Form.Group>
                    <Form.Label className="password" htmlFor="password"> Password:</Form.Label>
                    <Form.Control id="password" type="password" name="password" placeholder="password"
                    value={password} onChange={(e) => {setPassword(e.target.value)}}/>
                </Form.Group>

                <Form.Group>
                    <Form.Label className="confirm-password" htmlFor="confirm-password"> Confirm password:</Form.Label>
                    <Form.Control id={"confirm-password"} type="password" name="cornfirm password" placeholder="confirm password"/>
                </Form.Group>

                <Button id={"submit"} type="submit" >Submit</Button>

            </Form>

        </div>);
}