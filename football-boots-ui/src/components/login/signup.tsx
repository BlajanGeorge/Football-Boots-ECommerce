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
    const [gender, setGender] = useState('M');
    const [errorFirstName, setErrorFirstName] = useState('')
    const [errorLastName, setErrorLastName] = useState('')
    const [errorGender, setErrorGender] = useState('')
    const [errorEmail, setErrorEmail] = useState('')
    const [errorPassword, setErrorPassword] = useState('')
    const [errorAge, setErrorAge] = useState('')

    async function makePostRequestForSignUp() {
      const res = await axios.post('http://localhost:10000/users/registration/customer', {firstName, lastName, email, gender, age, password})
      .then(function(res){
        window.location.replace('http://localhost:3000/login')
    })
    .catch(function (error) {
        if (error.response) {
          if(error.response.data.message.includes('First'))
          {
            setErrorFirstName(error.response.data.message)
            setErrorLastName('')
            setErrorAge('')
            setErrorEmail('')
            setErrorPassword('');
            setErrorGender('')
          }
          if(error.response.data.message.includes('Last'))
          {
            setErrorFirstName('')
            setErrorLastName(error.response.data.message)
            setErrorAge('')
            setErrorEmail('')
            setErrorPassword('');
            setErrorGender('')
          }
          if(error.response.data.message.includes('Age'))
          {
            setErrorFirstName('')
            setErrorLastName('')
            setErrorPassword('')
            setErrorEmail('')
            setErrorAge(error.response.data.message);
            setErrorGender('')
          }
          if(error.response.data.message.includes('gender'))
          {
            setErrorFirstName('')
            setErrorLastName('')
            setErrorAge('')
            setErrorEmail('')
            setErrorPassword('');
            setErrorGender(error.response.data.message)
          }
          if(error.response.data.message.includes('email'))
          {
            setErrorFirstName('')
            setErrorLastName('')
            setErrorAge('')
            setErrorEmail(error.response.data.message)
            setErrorPassword('');
            setErrorGender('')
          }
          if(error.response.data.message.includes('Password'))
          {
            setErrorFirstName('')
            setErrorLastName('')
            setErrorAge('')
            setErrorEmail('')
            setErrorPassword(error.response.data.message);
            setErrorGender('')
          }
        }
    })
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
      value={gender}
      defaultValue={gender}
      className={classes.gender_combobox}
      renderInput={(params) => <TextField {...params} size={"small"} />}
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
      <Button onClick={makePostRequestForSignUp} variant="contained" className={classes.create_account_button}>Create Account</Button>
      <Typography className={classes.policy_text}>This site is protected by reCAPTCHA and the google <span className={classes.policy_text_blue}>Privacy Policy</span> and the <span className={classes.policy_text_blue}> Terms of Service </span>apply</Typography>
      <p className={classes.firstname_error_text}>{errorFirstName}</p>
      <p className={classes.lastname_error_text}>{errorLastName}</p>
      <p className={classes.age_error_text}>{errorAge}</p>
      <p className={classes.email_error_text}>{errorEmail}</p>
      <p className={classes.password_error_text}>{errorPassword}</p>
      <p className={classes.gender_error_text}>{errorGender}</p>
    </Box>
    </Box>
   );
};

  export default SignUp;