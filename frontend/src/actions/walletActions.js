import axios from 'axios';

// Action Types
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';
// Add more action types as needed for registration, logout, etc.

// Action Creators
export const loginSuccess = (user) => {
    return {
        type: LOGIN_SUCCESS,
        payload: user,
    };
};

export const loginFailure = (error) => {
    return {
        type: LOGIN_FAILURE,
        payload: error,
    };
};

// Thunk Action to Log In User
export const depositFunds = (userData) => (dispatch) => {
    // Replace 'your-api-endpoint/login' with the actual login endpoint URL
    axios
        .post('your-api-endpoint/login', userData)
        .then((response) => {
            // Assuming the API returns the user object on successful login
            const user = response.data;

            // Dispatch a success action with the user data
            dispatch(loginSuccess(user));
        })
        .catch((error) => {
            // Handle login failure
            dispatch(loginFailure(error.response.data));
        });
};

// Thunk Action to Log In User
export const requestWithdrawal = (userData) => (dispatch) => {
    // Replace 'your-api-endpoint/login' with the actual login endpoint URL
    axios
        .post('your-api-endpoint/login', userData)
        .then((response) => {
            // Assuming the API returns the user object on successful login
            const user = response.data;

            // Dispatch a success action with the user data
            dispatch(loginSuccess(user));
        })
        .catch((error) => {
            // Handle login failure
            dispatch(loginFailure(error.response.data));
        });
};
