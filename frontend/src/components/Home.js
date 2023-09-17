import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import {connect, useSelector} from 'react-redux';
import {Link, useNavigate} from 'react-router-dom';
import axios from 'axios';
import {logoutUser} from "../actions/authActions";

const Home = () => {
    const user = useSelector((state) => state.auth.user);
    const [walletBalance, setWalletBalance] = useState(null);
    const dispatch = useDispatch();
    const history = useNavigate();
    const handleLogout = (e) => {
        e.preventDefault();
        dispatch(logoutUser(history)); // Dispatch the logout action
    };

    console.log(user.phone);
    const postData = {
        phone: user.phone,
    };

    if(user == null) {history("/login");}

    useEffect(() => {
        // Define a function to fetch the wallet balance
        const fetchWalletBalance = async () => {
            try {
                // Replace 'your-api-endpoint' with the actual URL of your API
                const response = await axios.post(`http://localhost:8080/api/v1/wallet/balance`, postData);
                console.log(response);
                setWalletBalance(response.data == "" ? 0 : response.data);
            } catch (error) {
                // Handle errors
                console.error('Error fetching wallet balance:', error);
            }
        };

        // Call the function to fetch the wallet balance
        fetchWalletBalance();
    }, [user.phone]);

    return (
        <div className="container mt-5">
            <h2>Welcome, {user.name}!</h2>
            {walletBalance !== null ? (
                <p>Your Wallet Balance: Kes.{walletBalance}</p>
            ) : (
                <p>Loading wallet balance...</p>
            )}
            <Link to="/withdraw" className="btn btn-primary my-button">
                Withdraw
            </Link>
            <Link to="/deposit" className="btn btn-primary ml-2 my-button">
                Deposit
            </Link>
            <button type="submit" onClick={handleLogout} className="btn btn-primary my-button">
                Logout
            </button>
        </div>
    );
};

const mapStateToProps = (state) => ({
    user: state.auth.user,
});

export default connect(mapStateToProps)(Home);