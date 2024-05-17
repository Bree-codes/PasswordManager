import "./styling/login.css"

import {Button, Form} from "react-bootstrap";
import {useState} from "react";
export const    Login=()=>{
    const [username,setUsername]=useState("");
    const [password,setPassword]=useState("");
    return(
        <div className="Login ">
            <Form className={"login-form"}>
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