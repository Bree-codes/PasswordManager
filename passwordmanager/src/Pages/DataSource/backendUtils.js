import axios from "axios";



const permittedEndPoints = axios.create({
    baseURL:"http://localhost:8080/api/password-manager/auth",
    withCredentials:true
});

const secureEndpoints = axios.create({
    baseURL:"http://localhost:8080/api/password-manager",
    withCredentials:true,
})

secureEndpoints.interceptors.request.use(refreshToken);


export async function userRegistration(registrationRequest){
    return await permittedEndPoints.post("/register", registrationRequest);
}
export async function login(registrationRequest){
    return await permittedEndPoints.post("/login",registrationRequest);
}

export async function refreshToken(){
    await permittedEndPoints.put("/refresh/token").then(
        (response) => {
            sessionStorage.setItem("token", response.data.token);
            sessionStorage.setItem("id", response.data.id);
            sessionStorage.setItem("isLoggedIn", "true");
        }).catch((error) => {
            sessionStorage.setItem("token", "null");
            sessionStorage.setItem("id", "null");
            sessionStorage.setItem("isLoggedIn", "null");
        });
}

export async function getPasswords(userId){
    return
}