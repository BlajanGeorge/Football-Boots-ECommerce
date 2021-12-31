import { Autocomplete, Box, Button, TextField, Typography } from "@mui/material";
import classes from './signUp.module.css'
import { useState } from "react"
import axios from 'axios'

const options = ['M', 'F'];

const SignUp =  () => {
  const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [age, setAge] = useState('');
    const [gender, setGender] = useState('');

    async function makePostRequestForSignUp() {
      const res = await axios.post('http://localhost:10000/users/registration/customer', {firstName, lastName, email, gender, age, password});
      if(res.status == 200){
          console.log(res.data)
      }
  }

   return (  
    <Box className={classes.container}>
    <Typography className={classes.discover_text}>Discover the world’s top football boots brands at the most affordable prices.</Typography>
    <Box className={classes.allTextTogheter}>
    <Typography className={classes.login_text}>Sign up</Typography>
    <Typography className={classes.first_name_text}>First Name</Typography>
    <Typography className={classes.last_name_text}>Last Name</Typography>
    <TextField
    onChange={event => setFirstName(event.target.value)}
        hiddenLabel
        variant="filled"
        size="small"
        className={classes.first_name_textfield}

      />
      <TextField
      onChange={event => setLastName(event.target.value)} 
        hiddenLabel
        variant="filled"
        size="small"
        className={classes.last_name_textfield}
      />
      <Typography className={classes.email_text}>Email Address</Typography>
      <TextField
      onChange={event => setEmail(event.target.value)}
        hiddenLabel
        variant="filled"
        size="small"
        className={classes.email_textfield}
      />
      <Typography className={classes.age_text}>Age</Typography>
      <TextField
      onChange={event => setAge(event.target.value)}
      hiddenLabel
      variant="filled"
      size="small"
      className={classes.age_textfield}
      />
      <Typography className={classes.gender_text}>Gender</Typography>
      <Autocomplete
      onChange={(event, value) => setGender(value as string)}
      disablePortal
      id="combo-box-demo"
      options={options}
      className={classes.gender_combobox}
      renderInput={(params) => <TextField {...params} label="M" size={"small"} />}
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
      <Button onClick={makePostRequestForSignUp} variant="contained" href="#contained-buttons" className={classes.create_account_button}>Create Account</Button>
      <Typography className={classes.policy_text}>This site is protected by reCAPTCHA and the google <span className={classes.policy_text_blue}>Privacy Policy</span> and the <span className={classes.policy_text_blue}> Terms of Service </span>apply</Typography>
    </Box>
    </Box>
   );
};

  export default SignUp;