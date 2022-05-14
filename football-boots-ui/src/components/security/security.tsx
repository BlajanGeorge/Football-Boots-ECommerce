import { Navigate, useLocation } from 'react-router-dom';

export const PrivateRoute = ({ children }: { children: JSX.Element }) => {
    const auth = JSON.parse(localStorage.getItem("isAuth") || '{}');
    return !auth ? <Navigate to="/login" /> : children;
};