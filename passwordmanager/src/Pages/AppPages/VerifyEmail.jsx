import {Form} from "react-bootstrap";
import {useState} from "react";

export const VerifyEmail = () => {
    const [code, setCode] = useState("");

    return(
        <>
            <Form className={"verification-page"}>
                <Form.Group>
                    <Form.Label htmlFor={"code-input"}>Verify Your Email : </Form.Label>
                    <Form.Control type={"text"} value={code} maxLength={6} id={"code-input"}
                                  onChange={(e) => setCode(e.target.value)} />
                </Form.Group>
            </Form>
        </>);
}
