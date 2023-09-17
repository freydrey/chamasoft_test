import logo from './logo.svg';
import './App.css';
import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { loginUser } from './actions/authActions';
import { Link } from 'react-router-dom';
import Login from "./components/Login";
import MyRouter from "./MyRouter";
import Registration from "./components/Registration";


function App() {
   return (
   <nav>
      <ul>
         <li>
            <Link to="/withdraw">Register</Link>
         </li>
         <li>
            <Link to="/withdraw">Login</Link>
         </li>
         {/* Add more navigation links here */}
      </ul>
   </nav>
   );
}

export default App;
