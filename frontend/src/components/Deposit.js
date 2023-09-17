import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useSelector } from 'react-redux';
import {depositFunds, loginFailure, loginSuccess} from '../actions/walletActions';
import { Link } from 'react-router-dom';
import axios from "axios";

const Deposit = () => {
    const dispatch = useDispatch();
    const [amount, setAmount] = useState('');
    const [depositMessage, setDepositMessage] = useState(null);

    const user = useSelector((state) => state.auth.user);

    const handleDeposit = (e) => {
        e.preventDefault();

        // Create an object with your parameters
        const postData = {
            amount: amount,
            phone: user.phone,
        };
        console.log(user);
        axios
            .post('http://localhost:8080/api/v1/wallet/deposit/mpesa', postData)
            .then((response) => {
                // Dispatch a success action with the data
                setAmount('');
                // Display a success message
                console.log(response);
                setDepositMessage(response.data);
            })
            .catch((error) => {
                // Handle deposit failure
                setDepositMessage(error.data);
            });
    };


    return (
        <div className="container mt-5">
            {depositMessage && (
                <div className={`alert ${depositMessage.includes('successful') ? 'alert-success' : 'alert-danger'}`}>
                    {depositMessage}
                </div>
            )}
            <h2>Deposit Funds</h2>
            <form onSubmit={handleDeposit}>
            <div className="form-group">
                <label>Deposit Amount</label>

                <input
                    type="number"
                    value={amount}
                    name="amount"
                    onChange={(e) => setAmount(e.target.value)}
                    className="form-control"
                    placeholder="Enter deposit amount"
                />
            </div>
            <div><br/></div>
            <button type="submit" className="btn btn-primary my-button">
                Deposit
            </button>
            <Link to="/home" className="btn btn-secondary ml-2 my-button">
                Back to Home
            </Link>
            </form>

        </div>
    );
};

export default Deposit;
