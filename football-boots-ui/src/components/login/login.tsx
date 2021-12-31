import { Box, Button, TextField, Typography } from "@mui/material";
import classes from './login.module.css'
import { Divider } from '@mui/material'
import { Link } from 'react-router-dom'
import { useState } from "react"
import axios from 'axios';

const Login =  () => {

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    async function makePostRequestForLogin() {
        const res = await axios.post('http://localhost:10000/users/login', {email, password});
        if(res.status == 200){
            localStorage.setItem('token', res.data.token)
        }
    }

   return (
    <Box className={classes.container}>
     <Typography className={classes.discover_text}>Discover the world’s top football boots brands at the most affordable prices.</Typography>
     <Box className={classes.allTextTogheter}>
     <Typography className={classes.login_text}>Welcome</Typography>
     <Typography className={classes.email_address_text}>Email Address</Typography>
     <TextField
        onChange={event => setEmail(event.target.value)}
        hiddenLabel
        variant="filled"
        size="small"
        className={classes.email_textfield}
      />
      <Typography className={classes.password_text}>Password</Typography>
      <TextField
        onChange={event => setPassword(event.target.value)}
        hiddenLabel
        variant="filled"
        size="small"
        type={"password"}
        className={classes.password_textfield}
      />
      <Link to="/boots" style={{ textDecoration: 'none' }}>
      <Button onClick={makePostRequestForLogin} variant="contained" className={classes.login_button}>Login</Button>
      </Link>
      <Divider className={classes.divider_right}/>
      <Typography className={classes.or_text}>or</Typography>
      <Divider className={classes.divider_left}/>
      <Typography className={classes.need_account_text}>Need an account?</Typography>
      <Link to="/registration" style={{ textDecoration: 'none' }}>
      <Button variant="contained" className={classes.sign_up_button}>Sign up</Button>
      </Link>
      <Typography className={classes.policy_text}>This site is protected by reCAPTCHA and the google <span className={classes.policy_text_blue}>Privacy Policy</span> and the <span className={classes.policy_text_blue}> Terms of Service </span>apply</Typography> 
    </Box>
    </Box>
   );
};

  export default Login;