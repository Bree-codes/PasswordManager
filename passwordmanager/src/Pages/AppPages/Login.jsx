import "../styling/login.css"
import {Alert, Button, Form} from "react-bootstrap";
import {useEffect, useState} from "react";
import {login, } from "../DataSource/backendUtils";
import {useNavigate} from "react-router-dom";
export const    Login=()=>{
    const [username,setUsername]=useState("");
    const [password,setPassword]=useState("");
    const navigate = useNavigate();
    const [loginError, setLoginError] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault()

        const loginUser= {
            username:username,
            password:password
        }

        login(loginUser).then((response) => {

            sessionStorage.setItem("token", response.data.token);
            sessionStorage.setItem("id", response.data.id);
            sessionStorage.setItem("isLoggedIn", "true");

            navigate("/home/view/passwords");

        }).catch((error) => {
            setLoginError(error.response.data.message);
        })
    }

    useEffect(() => {
        if(loginError !== ""){
            setTimeout(() => {
                setLoginError("")
            }, 4000)
        }
    }, [loginError]);



    return(
        <div className="Login ">
            {loginError && <Alert id={"error-alert"}>{loginError}</Alert>}
            <Form className={"login-form"} onSubmit={handleSubmit}>
                <Form.Label className={"login-title"}>Login</Form.Label>

                 <Form.Group>
                     <Form.Label className="name"  htmlFor="name">  Username:</Form.Label>
                     <Form.Control id="username" type="text"
                                   name="Username" placeholder="Username" value={username}
                     onChange={e=>{setUsername(e.target.value)}}/>
                 </Form.Group>


                <Form.Group>
                    <Form.Label className="password" htmlFor="password"> Password:</Form.Label>
                    <Form.Control id="passwords" type="password" name="password" placeholder="password" value={password}
                    onChange={e=>{setPassword(e.target.value)}}/>

                </Form.Group>

                <Button id={"submit"} type="submit" >Submit</Button>
            </Form>
        </div>

    )
}