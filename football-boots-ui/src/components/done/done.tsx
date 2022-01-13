import React, { useEffect } from 'react'

import CircularProgress from '@mui/material/CircularProgress';
import { Box, Typography } from '@mui/material';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import axios from 'axios';

export const Done = () => {
    setTimeout(function() {
        window.location.replace('http://localhost:3000/boots');
      }, 5000);

      async function sendEmail(){
          axios.get('http://localhost:10000/users/order/' + localStorage.getItem('userEmail'), {
            headers: {
              authorization:'Bearer ' + localStorage.getItem('token') as string 
           }
      })
    }

    useEffect(() => {
        sendEmail()
    }, [])
 
    return (
        <Box sx={{width:'1792px', height:'1041px', backgroundColor:'black'}}>
            <CheckCircleIcon sx={{position:'asbolute' , marginLeft:'850px', marginTop:'500px', fontSize:'60px'}} color="success" />
            <Typography sx={{fontSize:'30px', position:'absolute', marginLeft:'610px', marginTop:'20px'}} color="green">Transaction was processed with success.</Typography>
            <Typography sx={{fontSize:'30px', position:'absolute', marginLeft:'800px', marginTop:'60px'}} color="green">Thank you!</Typography>
        </Box>
    )
}