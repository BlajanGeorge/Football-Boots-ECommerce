import { Box, Typography } from '@mui/material'
import React from 'react'
import CircularProgress from '@mui/material/CircularProgress';

export const Loading = () => {
    if (JSON.parse(localStorage.getItem("error") || '{}') === false) {
        setTimeout(function () {
            window.location.replace('http://localhost:3000/done');
        }, 5000);
    }
    else {
        setTimeout(function () {
            window.location.replace('http://localhost:3000/error');
        }, 5000);
    }

    return (
        <Box sx={{ width: '1792px', height: '1041px', backgroundColor: 'black' }}>
            <CircularProgress sx={{ position: 'asbolute', marginLeft: '850px', marginTop: '500px' }} color="secondary" />
            <Typography sx={{ fontSize: '30px', position: 'absolute', marginLeft: '690px', marginTop: '20px' }} color={'secondary'}>Transaction is processing...</Typography>
        </Box>
    )
}