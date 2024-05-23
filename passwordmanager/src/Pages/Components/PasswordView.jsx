import {Button, Form, Image} from "react-bootstrap";
import "./../styling/HomePage.css"
import eyeSlash from "./../pics/eye-slash-solid.svg"
import eye from "./../pics/eye-solid.svg"
import {useEffect, useState} from "react";

const PasswordView = ({websiteName, username, password, setUsername, setPassword}) => {
    const [see, setSee] = useState(false);
    const [passwordType, setPasswordType] = useState("");
    const [doEdit, setDoEdit] = useState(true);
    const [editUsername, setEditUsername] = useState("Username");
    const [editPassword, setEditPassword] = useState("Password");
    const [saveChanges, setSaveChanges] = useState("save-changes-hide");
    const [copyUsername, setCopyUsername] = useState("Copy");
    const [copyPassword, setCopyPassword] = useState("Copy");
    const [copyButtonUsername, setCopyButtonUsername] = useState("password-copy");
    const [copyButtonPassword, setCopyButtonPassword] = useState("password-copy");


    useEffect(() => {
        if(see){
            setPasswordType("password");
        }else {
            setPasswordType("text")
        }
    }, [see]);

    useEffect(() => {
        if(!doEdit){
            setEditUsername("Username-edit");
            setEditPassword("Password-edit");
            setSaveChanges("save-changes");
            setCopyButtonUsername("hide");
            setCopyButtonPassword("hide");

        }else {
            setEditUsername("Username");
            setEditPassword("Password");
            setSaveChanges("save-changes-hide");
            setCopyButtonUsername("password-copy");
            setCopyButtonPassword("password-copy");
        }
    }, [doEdit]);


    const handleCopyUsername = () => {

        setCopyUsername("Copied!");
        setCopyPassword("Copy");
        setCopyButtonUsername("password-copied");
        setCopyButtonPassword("password-copy");
        setTimeout(() => {
            setCopyUsername("Copy");
            setCopyButtonUsername("password-copy");
        }, 10000);

        return navigator.clipboard.writeText(username);
    }

    const handleCopyPassword = () => {

        setCopyPassword("Copied!");
        setCopyUsername("Copy");
        setCopyButtonUsername("password-copy");
        setCopyButtonPassword("password-copied");
        setTimeout(() => {
            setCopyPassword("Copy");
            setCopyButtonPassword("password-copy");
        }, 10000);
        return navigator.clipboard.writeText(password);
    }

    return (<div className={"password-view"}>
                <div className={"edit-delete"}>
                        <Button onClick={() =>setDoEdit (false)} id={"edit"}>Edit</Button>
                        <Button id={"delete"}>Delete</Button>
                </div>
                <Form className={"details-view-form"}>
                    <Form.Group>
                        <Form.Label className={"website-name"} htmlFor={"website-name"}>Website</Form.Label>
                        <Form.Control id="website-name" type={"text"} disabled={true} value={websiteName} />
                    </Form.Group>

                    <Form.Group>
                        <Form.Label className={"username"} htmlFor={"Username"}>Username</Form.Label>

                        <div className={"username-copy"}>
                            <Form.Control id={editUsername} type={"text"} disabled={doEdit} value={username}
                            onChange={(e) => setUsername(e.target.value)} />

                            <Button onClick={handleCopyUsername} id={copyButtonUsername}>{copyUsername}</Button>

                        </div>

                    </Form.Group>

                    <Form.Group>
                        <Form.Label className={"password"} htmlFor={"Password"}>Password</Form.Label>
                        <div id={"password-view"}>

                            <Form.Control id={editPassword}  type={passwordType} disabled={doEdit} value={password}
                            onChange={(e) => setPassword(e.target.value)} />

                            {see ? <Button onClick={() => setSee(false)}
                                           id={"view"}><Image src={eye} width={20} height={20} /></Button> :
                            <Button onClick={() => setSee(true)}
                                    id={"view"}><Image src={eyeSlash} width={20} height={20} /></Button>}

                            <Button onClick={handleCopyPassword} id={copyButtonPassword}>{copyPassword}</Button>

                        </div>
                    </Form.Group>
                </Form>
                <div className={saveChanges}>
                    <Button id={"save-changes"}>Save Changes</Button>
                    <Button id={"cancel"} onClick={() => setDoEdit(true)}>Cancel</Button>
                </div>

            </div>);
}

export default PasswordView;