import "./styling/SignUp.css"
import {Button, Form} from "react-bootstrap";
export const SignUp = () => {
    return(
        <div className="Registration-form">

            <Form>

                <Form.Label>Registration Form</Form.Label>

                <Form.Group>
                    <Form.Label className="name" htmlFor="name"> Username:</Form.Label>
                    <Form.Control type="text" name="Username" placeholder="Username"/>
                </Form.Group>

                <Form.Group>
                    <Form.Label className="name" htmlFor="name"> Email:</Form.Label>
                    <Form.Control type="text" name="Email" placeholder="email"/>
                </Form.Group>

                <Form.Group>
                    <Form.Label className="name" htmlFor="name"> Password:</Form.Label>
                    <Form.Control type="text" name="password" placeholder="password"/>
                </Form.Group>

                <Form.Group>
                    <Form.Label className="name" htmlFor="name"> Confirm password:</Form.Label>
                    <Form.Control type="text" name="cornfirm password" placeholder="confirm password"/>
                </Form.Group>

                <Button className={"submit"} type="submit">Submit</Button>

            </Form>

        </div>);
}