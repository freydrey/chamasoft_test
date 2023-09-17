import React, { useState } from 'react';
import {useDispatch, useSelector} from 'react-redux';
import { loginUser } from '../actions/authActions';
import { Link, useNavigate} from 'react-router-dom';

const Login = () => {
    const dispatch = useDispatch();
    const history = useNavigate();
    const loginError = useSelector((state) => state.auth.error);
    const [formData, setFormData] = useState({
        phone: '',
        password: '',
    });

    const { phone, password } = formData;

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        dispatch(loginUser(formData, history)); // Dispatch the login action
    };

    return (
        <div className="container mt-5">
            <h2>Login</h2>
            {loginError && <div className="alert alert-danger">{loginError}</div>}
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Email</label>
                    <input
                        type="number"
                        name="phone"
                        value={phone}
                        onChange={handleChange}
                        className="form-control"
                        placeholder="Enter your phone"
                    />
                </div>
                <div className="form-group">
                    <label>Password</label>
                    <input
                        type="password"
                        name="password"
                        value={password}
                        onChange={handleChange}
                        className="form-control"
                        placeholder="Enter your password"
                    />
                </div>
                <button type="submit" className="btn btn-primary">
                    Login
                </button>
            </form>
            <p>
                Don't have an account? <Link to="/registration">Register</Link>
            </p>

        </div>
    );
};

export default Login;
