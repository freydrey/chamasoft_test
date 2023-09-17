import axios from 'axios';

// Action Types
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';


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


// Action to log out the user
export const logoutUser = (history) => (dispatch) => {
    // Dispatch a logout action
    dispatch({ type: 'LOGOUT' });
    history("/login");
};

// Thunk Action to Log In User
export const loginUser = (userData, history) => async (dispatch) => {
    // Replace 'your-api-endpoint/login' with the actual login endpoint URL
    axios
        .post('http://localhost:8080/api/v1/customers/login', userData)
        .then((response) => {
            // Assuming the API returns the user object on successful login
            console.log(response.data);
            // Dispatch a success action if login is successful
            dispatch({ type: 'LOGIN_SUCCESS', payload: response.data });

            // Redirect to the home page after successful login
            if(response.status == 200 && response.data != ""){
                history('/home');
            }else{
                dispatch(loginFailure("User not found"));
            }

        })
        .catch((error) => {
            // Handle login failure
            dispatch(loginFailure(error.data));
            // Dispatch an error action if login fails
            dispatch({ type: 'LOGIN_FAILURE', payload: error.data });

        });
};


export const registerUser = (userData) => (dispatch) => {

};