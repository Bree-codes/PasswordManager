import axios from "axios";
import {prepend} from "dom/lib/mutation";

const permittedEndPoints = axios.create({
    baseURL:"http://localhost:8080/api/password-manager/auth",
    withCredentials:true
});


async function userRegistration(registrationRequest){
    return await permittedEndPoints.post("/register", registrationRequest);
}