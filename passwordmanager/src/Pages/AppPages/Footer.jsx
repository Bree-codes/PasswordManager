import React from "react";
import "../styling/Footer.css"
export const Footer=()=>{
    return (
        <footer className="footer">
            <div className="footer-content">
                <p>&copy; 2024 Password Manager. All rights reserved.</p>
               {/* <div className="footer-links">
                    <a href="/privacy-policy">Privacy Policy</a>
                    <a href="/terms-of-service">Terms of Service</a>
                    <a href="/contact">Contact Us</a>
                </div>*/}
            </div>
        </footer>
    )
}