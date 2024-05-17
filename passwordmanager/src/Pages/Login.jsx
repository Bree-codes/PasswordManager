import "./styling/login.css"
export const Login=()=>{
    return(
        <div className="Login ">
            <form>
                <fieldset>
                    <legend className="h1">Login</legend>
                    <label className="name"  htmlFor="name"> Username:</label><br/>
                    <input type="text" name="Username" placeholder="Username"/><br/>
                    <label className="name"  htmlFor="name"> Password:</label><br/>
                    <input type="text" name="password" placeholder="password"/><br/>
                    <button type="submit">Login</button><br/>
                </fieldset>
            </form>
        </div>
    )
}