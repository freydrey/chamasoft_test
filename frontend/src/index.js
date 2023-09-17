import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css';
import App from "./App";
import {Provider} from "react-redux";
import store from "./reducers/Store";
import MyRouter from "./MyRouter";
import {BrowserRouter as Router, Link, Route, Routes} from "react-router-dom";
import Registration from "./components/Registration"
import Login from "./components/Login";
import Deposit from "./components/Deposit";
import Home from "./components/Home";
import Withdraw from "./components/Withdraw";


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <Provider store={store}>
            <Router>
                <Routes>
                    <Route path="/registration" element={<Registration />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/withdraw" element={<Withdraw />} />
                    <Route path="/deposit" element={<Deposit />} />
                    <Route path="/home" element={<Home />} />
                    <Route path="/" element={<Login />} />
                </Routes>
            </Router>
        </Provider>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
