import  { useState } from 'react';
import "../styling/Profile.css"

export const Profile=()=>{
    const [username ,setUsername]=useState();
    const [email, setEmail]=useState();
    const [avatar, setAvatar]=useState()



    const handleSubmit = (e) => {
        e.preventDefault();
        // Handle form data, e.g., send to server
        console.log('Username:', username);
        console.log('Email:', email);
        console.log('Avatar:', avatar);
    };


    const handleAvatarChange = (e) => {
        setAvatar(e.target.files[0]);
    };
    return(
            <div className="profile-container">
                <h1>User Profile</h1>
                <form onSubmit={handleSubmit}>
                    <label htmlFor="username">Username:</label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />

                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />

                    <label htmlFor="avatar">Avatar:</label>
                    <input
                        type="file"
                        id="avatar"
                        accept="image/*"
                        onChange={handleAvatarChange}
                    />

                    <button type="submit">Save Profile</button>
                </form>
                {avatar && (
                    <div className="avatar-preview">
                        <img src={URL.createObjectURL(avatar)} alt="Avatar Preview" />
                    </div>
                )}
            </div>
        );



}