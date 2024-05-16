import "./styling/SignUp.css"
export const SignUp=()=>{
    return(
        <div className="Registration-form">
            <form>
                <fieldset>
                    <legend className="h1"> Registration Form</legend>

                    <label className="name" htmlFor="name"> Username:</label><br/><br/>
                    <input type="text" name="Username" placeholder="Username"/><br/>
                    <label className="name" htmlFor="name"> Email:</label><br/><br/>
                    <input type="text" name="Email" placeholder="email"/><br/>
                    <label className="name" htmlFor="name"> Password:</label><br/><br/>
                    <input type="text" name="password" placeholder="password"/><br/>
                    <label className="name" htmlFor="name"> Confirm password:</label><br/><br/>
                    <input type="text" name="cornfirm password" placeholder="confirm password"/><br/>

                    <button className={"submit"} type="submit">Submit</button>
                </fieldset>
            </form>
        </div>
    )

}