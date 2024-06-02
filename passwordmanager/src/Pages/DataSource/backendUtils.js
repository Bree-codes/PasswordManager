import axios from "axios";



const permittedEndPoints = axios.create({
    baseURL:"http://localhost:8080/api/password-manager/auth",
    withCredentials:true
});

const secureEndpoints = axios.create({
    baseURL:"http://localhost:8080/api/password-manager",
    withCredentials:true,
    headers:{
        Authorization:`${sessionStorage.getItem("token")}`
    }
});

secureEndpoints.interceptors.request.use(refreshRequestToken);


export async function userRegistration(registrationRequest){
    return await permittedEndPoints.post("/register", registrationRequest);
}
export async function login(registrationRequest){
    return await permittedEndPoints.post("/login",registrationRequest);
}

export async function refreshRequestToken(){
    return await permittedEndPoints.put("/refresh/token").then(
        (response) => {
            sessionStorage.setItem("token", response.data.token);
            sessionStorage.setItem("id", response.data.userId);
            sessionStorage.setItem("username", response.data.username)
            sessionStorage.setItem("isLoggedIn", "true");
        }).catch((error) => {
            sessionStorage.setItem("token", "");
            sessionStorage.setItem("id", "");
            sessionStorage.setItem("isLoggedIn", "");
        });
}

export async  function refreshToken(){
    return await permittedEndPoints.put("/refresh/token");
}

export async function getPasswords(userId){
    return await  secureEndpoints.get(`/get/${userId}`);
}
