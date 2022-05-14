import React, { useEffect } from 'react'

import { Box, Typography } from '@mui/material';
import ErrorIcon from '@mui/icons-material/Error';

export const Error = () => {
    setTimeout(function () {
        window.location.replace('http://localhost:3000/boots');
    }, 5000);

    return (
        <Box sx={{ width: '1792px', height: '1041px', backgroundColor: 'black' }}>
            <ErrorIcon sx={{ position: 'asbolute', marginLeft: '850px', marginTop: '500px', fontSize: '60px' }} color="error" />
            <Typography sx={{ fontSize: '30px', position: 'absolute', marginLeft: '610px', marginTop: '20px' }} color="error">{localStorage.getItem("messageError")}</Typography>
            <Typography sx={{ fontSize: '30px', position: 'absolute', marginLeft: '800px', marginTop: '60px' }} color="error">Thank you!</Typography>
        </Box>
    )
}