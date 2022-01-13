import React from 'react'
import axios from 'axios'
import { useState, useEffect } from 'react'
import { Button, Box, FormControlLabel, Switch, AppBar, Toolbar, IconButton, Typography, Menu, MenuItem, TextField } from '@mui/material'
import { FormGroup } from '@material-ui/core'
import { AccountCircle } from '@mui/icons-material'
import ShoppingBagIcon from '@mui/icons-material/ShoppingBag';
import FavoriteIcon from '@mui/icons-material/Favorite';
import HomeIcon from '@mui/icons-material/Home';
import classes from './profile.module.css'

const UserProfile = () =>
{
    const [auth, setAuth] = React.useState(true);
    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const [firstname, setFirstname] = useState('')
    const [lastname, setLastname] = useState('')
    const [age, setAge] = useState('')
    const [gender, setGender] = useState('')
    const [email, setEmail] = useState('')
    const [oldPassword, setOldPassword] = useState('')
    const [newPassword, setNewPassword] = useState('')
  
    const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
      setAnchorEl(event.currentTarget);
    };
  
    const handleLogout = () => {
      localStorage.removeItem('token')
      localStorage.removeItem('userId')
      window.location.replace('http://localhost:3000/login')
    };

    const handleProfile = () => {
        window.location.replace("http://localhost:3000/profile")
    };

    const handleHome = () => {
        window.location.replace('http://localhost:3000/boots')
      }

      async function getProfile() {
        const res = await axios.get('http://localhost:10000/users/' + localStorage.getItem('userId'), {
             headers: {
                authorization:'Bearer ' + localStorage.getItem('token') as string
             }
         }
     ).then(function(res){
         setFirstname(res.data.firstName)
         setLastname(res.data.lastName)
         setEmail(res.data.email)
         setGender(res.data.gender)
         setAge(res.data.age)
     })
    }

    const handleShopingBag = () => {
      window.location.replace('http://localhost:3000/basket')
    }

    async function changePassword(){
        const res = await axios.put('http://localhost:10000/users/' + localStorage.getItem('userId'), {oldPassword, newPassword}, {
            headers: {
                authorization:'Bearer ' + localStorage.getItem('token') as string
            }
        }
        )
    }

    useEffect(() => {    
        getProfile() 
    });
    
    return(
        <Box>
        <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar sx={{bgcolor: 'black'}}>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Footbal boots
          </Typography>
          {auth && (
            <div>
              <IconButton color="inherit"
              onClick={handleHome}>
                    <HomeIcon/>
                </IconButton>
                <IconButton color="inherit">
                    <FavoriteIcon/>
                </IconButton>
                <IconButton color="inherit"
                onClick={handleShopingBag}>
                  <ShoppingBagIcon/>
              </IconButton>
              <IconButton
                size="large"
                aria-label="account of current user"
                aria-controls="menu-appbar"
                aria-haspopup="true"
                onClick={handleMenu}
                color="inherit"
              >
                <AccountCircle />
              </IconButton>
              <Menu
                id="menu-appbar"
                anchorEl={anchorEl}
                anchorOrigin={{
                  vertical: 'top',
                  horizontal: 'right',
                }}
                keepMounted
                transformOrigin={{
                  vertical: 'top',
                  horizontal: 'right',
                }}
                open={Boolean(anchorEl)}
                onClose={handleLogout}
              >
                <MenuItem onClick={handleProfile}>Profile</MenuItem>
                <MenuItem onClick={handleLogout}>Logout</MenuItem>
              </Menu>
            </div>
          )}
        </Toolbar>
      </AppBar>
    </Box>
        <Box className={classes.container}>
            <Box className={classes.profile_container}>
                <Typography className={classes.firstname_text}>First name :</Typography>
                <Typography className={classes.lastname_text}>Last name :</Typography>
                <Typography className={classes.age_text}>Age :</Typography>
                <Typography className={classes.gender_text}>Gender :</Typography>
                <Typography className={classes.email_text}>Email :</Typography>
                <Typography className={classes.old_password_text}>Old password :</Typography>
                <Typography className={classes.new_password_text}>New password :</Typography>
                <p className={classes.firstname_value_text}>{firstname}</p>
                <p className={classes.lastname_value_text}>{lastname}</p>
                <p className={classes.age_value_text}>{age}</p>
                <p className={classes.gender_value_text}>{gender}</p>
                <p className={classes.email_value_text}>{email}</p>
                <TextField type={'password'} className={classes.old_password_text_field} onChange={event => setOldPassword(event.target.value)}/>
                <TextField type={'password'} className={classes.new_password_text_field} onChange={event => setNewPassword(event.target.value)}/>
                <Button onClick={changePassword} className={classes.change_password_button}>Change password</Button>
                </Box>
        </Box>
        </Box>
    )

}

export default UserProfile