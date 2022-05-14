import { Box, Toolbar, AppBar, Typography, IconButton, Menu, MenuItem } from '@mui/material';
import HomeIcon from '@mui/icons-material/Home';
import PeopleIcon from '@mui/icons-material/People';
import SportsSoccerIcon from '@mui/icons-material/SportsSoccer';
import ShoppingBagIcon from '@mui/icons-material/ShoppingBag';
import React from 'react'
import { AccountCircle } from '@mui/icons-material';
import classes from './adminConsole.module.css'
import axios from 'axios';

export const AdminConsole = () => {

  const [auth, setAuth] = React.useState(true);
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);

  async function logout() {
    localStorage.setItem("isAuth", JSON.stringify(false))
    await axios.post("http://localhost:10000/users/logout/user/" + localStorage.getItem("userEmail"), {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    })
  }
  const handleLogout = () => {
    logout()
    localStorage.removeItem('token')
    localStorage.removeItem('userId')
    window.location.replace('http://localhost:3000/login')
  };

  const handleHome = () => {
    window.location.replace('http://localhost:3000/adminConsole')
  }

  const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleUsers = () => {
    window.location.replace('http://localhost:3000/adminConsole/users')
  }
  const handleBoots = () => {
    window.location.replace('http://localhost:3000/adminConsole/boots')
  }
  const handleBasket = () => {
    window.location.replace('http://localhost:3000/adminConsole/basket')
  }
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Footbal boots
          </Typography>
          {auth && (
            <div>
              <IconButton color="inherit"
                onClick={handleHome}>
                <HomeIcon />
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
                <MenuItem onClick={handleLogout}>Logout</MenuItem>
              </Menu>
            </div>
          )}
        </Toolbar>
      </AppBar>
      <Box className={classes.users_container}>
        <Typography className={classes.title}>USERS</Typography>
        <IconButton sx={{ marginLeft: '105px', marginTop: '95px' }} onClick={handleUsers}>
          <PeopleIcon sx={{ color: 'white', fontSize: '80px' }} />
        </IconButton>
      </Box>
      <Box className={classes.boots_container}>
        <Typography className={classes.title}>BOOTS</Typography>
        <IconButton sx={{ marginLeft: '105px', marginTop: '95px' }} onClick={handleBoots}>
          <SportsSoccerIcon sx={{ color: 'white', fontSize: '80px' }} />
        </IconButton>
      </Box>
      <Box className={classes.basket_container}>
        <Typography className={classes.basket_title}>BASKET</Typography>
        <IconButton sx={{ marginLeft: '105px', marginTop: '95px' }} onClick={handleBasket}>
          <ShoppingBagIcon sx={{ color: 'white', fontSize: '80px' }} />
        </IconButton>
      </Box>
    </Box>
  );
};
