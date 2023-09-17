import { createStore, applyMiddleware } from 'redux';
import thunk from 'redux-thunk'; // Example middleware for handling async actions
import rootReducer from './rootReducer'

const store = createStore(rootReducer, applyMiddleware(thunk));

export default store;
