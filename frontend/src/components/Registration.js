import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import {loginSuccess, regFailure, registerUser} from '../actions/authActions';
import {Link, useNavigate} from 'react-router-dom';
import axios from "axios";

const Registration = () => {
    //const history = useNavigate();
    let regError = "";
    const [formData, setFormData] = useState({
        name: '',
        phone: '',
        password: ''
    });
    const [registrationMessage, setRegistrationMessage] = useState(null);

    const { name, phone, password } = formData;

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
         e.preventDefault();
         // Make API call to register user
         axios
            .post('http://localhost:8080/api/v1/customers/register', formData, {
            headers: {
                'Content-Type': 'application/json',
            }
        }).then((response) => {

                 // Reset the form fields after successful registration
                 setFormData({
                     name: '',
                     phone: '',
                     password: '',
                 });

                 // Display a success message
                 setRegistrationMessage('Registration successful!. Click on login to proceed');
            })
            .catch((error) => {
                // Handle login failure
                //dispatch(regFailure(error.response.data));
                regError = error.response.data;
            });
    };

    return (
        <div className="container mt-5">
            {registrationMessage && (
                <div className={`alert ${registrationMessage.includes('successful') ? 'alert-success' : 'alert-danger'}`}>
                    {registrationMessage}
                </div>
            )}
            <h2>Registration</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="name">Name</label>
                    <input type="text"
                           className="form-control"
                           name="name"
                           value={name}
                           onChange={handleChange}
                           placeholder="Enter your name" />
                </div>
                <div className="form-group">
                    <label htmlFor="phone">Phone Number</label>
                    <input type="phone"
                           className="form-control"
                           name="phone"
                           value={phone}
                           onChange={handleChange}
                           placeholder="Enter your phone" />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <input type="password"
                           className="form-control"
                           name="password"
                           value={password}
                           onChange={handleChange}
                           placeholder="Enter your password" />
                </div>
                <button type="submit" className="btn btn-primary">
                    Register
                </button>
            </form>
            <p>
                Already have an account? <Link to="/login">Login</Link>
            </p>
        </div>
    );
};

export default Registration;
