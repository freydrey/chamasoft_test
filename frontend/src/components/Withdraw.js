import React, { useState } from 'react';
import {useDispatch, useSelector} from 'react-redux';
import { Link } from 'react-router-dom';
import axios from "axios";

const Withdraw = () => {
    const dispatch = useDispatch();
    const [amount, setAmount] = useState('');
    const [withdrawMessage, setWithdrawMessage] = useState(null)
    const user = useSelector((state) => state.auth.user);

    const handleWithdrawal = (e) => {
        e.preventDefault();
        // Create an object with your parameters
        const postData = {
            amount: amount,
            phone: user.phone,
        };

        axios
            .post('http://localhost:8080/api/v1/wallet/withdraw/mpesa', postData)
            .then((response) => {
                // Dispatch a success action with the data
                setAmount('');
                // Display a success message
                console.log(response);
                setWithdrawMessage(response.data);
            })
            .catch((error) => {
                // Handle deposit failure
                setWithdrawMessage(error.data);
            });
    };

    return (
        <div className="container mt-5">
            {withdrawMessage && (
                <div className={`alert ${withdrawMessage.includes('successful') ? 'alert-success' : 'alert-danger'}`}>
                    {withdrawMessage}
                </div>
            )}
            <h2>Request Withdrawal</h2>
            <form onSubmit={handleWithdrawal}>
            <div className="form-group">
                <label>Withdrawal Amount</label>
                <input
                    type="number"
                    value={amount}
                    name="amount"
                    onChange={(e) => setAmount(e.target.value)}
                    className="form-control"
                    placeholder="Enter withdrawal amount"
                />
            </div>
            <div><br/></div>
            <button onClick={handleWithdrawal} className="btn btn-primary my-button">
                Request Withdrawal
            </button>
            <Link to="/home" className="btn btn-secondary ml-2 my-button">
                Back to Home
            </Link>
            </form>
        </div>

    );
};

export default Withdraw;
