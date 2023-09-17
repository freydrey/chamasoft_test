// MyRouter.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import Login from './components/Login';
import Withdraw from './components/Withdraw';
import Deposit from './components/Deposit';

const MyRouter = () => {
    return (
        <Router>
            <Routes>
                <Route path="/registration" component={Login} />
                <Route path="/login" component={Login} />
                <Route path="/withdraw" component={Withdraw} />
                <Route path="/deposit" component={Deposit} />
                <Route path="/" component={Home} />
            </Routes>
        </Router>
    );
};

export default MyRouter;
