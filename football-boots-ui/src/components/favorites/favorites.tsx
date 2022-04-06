import { AppBar, Avatar, Box, Button, IconButton, List, ListItem, ListItemAvatar, ListItemText, Menu, MenuItem, styled, Toolbar, Typography } from '@mui/material'
import axios from 'axios'
import React, { useEffect } from 'react'
import DeleteIcon from '@mui/icons-material/Delete';
import FavoriteIcon from '@mui/icons-material/Favorite';
import { AccountCircle } from '@mui/icons-material';
import HomeIcon from '@mui/icons-material/Home';
import ShoppingBagIcon from '@mui/icons-material/ShoppingBag';

export const Favorites = () => {
    const [favorites, setFavorites] = React.useState([]);
    const [auth, setAuth] = React.useState(true);
    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const [toggle, setToggle] = React.useState(true);

    async function getFavorites(){
        const res = axios.get("http://localhost:10000/favorites/" + localStorage.getItem("userId"), {
            headers: {
                authorization: 'Bearer ' + localStorage.getItem('token') as string
            }
    }).then(function(res){
        setFavorites(res.data)
    })
}

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

const handleShopingBag = () => {
    window.location.replace('http://localhost:3000/basket')
  }

  const handleFavorites = () => {
    window.location.replace('http://localhost:3000/favorites')
  }

  const handleNameClick = (bootsId : string) => {
    localStorage.setItem("bootsId", bootsId)
    window.location.replace("http://localhost:3000/boots/id")
  }

  async function deleteFavorites(favoritesId : string) {
   const res = await axios.delete("http://localhost:10000/favorites/" + favoritesId, {
        headers: {
            authorization: 'Bearer ' + localStorage.getItem('token') as string
        }
    }).then(function(res){
        if(res.status == 200)
        {
            setToggle(!toggle)
        }
    })
  }

  const handleDeleteFavorites = (favoritesId : string) => {
     deleteFavorites(favoritesId)
  }

useEffect( () => {
getFavorites()
},[toggle])

    return (
        <Box>
             <AppBar position="static">
                <Toolbar>
                  <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                    Footbal boots
                  </Typography>
                  {auth && (
                    <div>
                      <IconButton color="inherit"
                      onClick={handleHome}>
                            <HomeIcon/>
                        </IconButton>
                        <IconButton color="inherit" onClick={handleFavorites}>
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
                <Box sx={{position:'absolute', width:'300px', marginLeft:'700px', marginTop:'100px', backgroundColor:'#1976d2', borderRadius:'8px'}}>
            <List>
              {favorites.map((row : any) =>
                <ListItem
                key = {row.favoritesId}

                  secondaryAction={
                    <IconButton edge="end" aria-label="delete" onClick={() => handleDeleteFavorites(row.favoritesId)}>
                      <DeleteIcon sx={{color:'white'}}/>
                    </IconButton>
                  }>
                  <ListItemAvatar>
                    <Avatar sx={{backgroundColor:'white'}}>
                      <FavoriteIcon color='error'/>
                    </Avatar>
                  </ListItemAvatar>
                  <ListItemText sx={{color:'white'}} onClick={() => handleNameClick(row.bootsId)} style={{cursor:"pointer"}}>
                      {row.bootsName}
                  </ListItemText>
                </ListItem>
              )}
            </List>
        </Box>
                </Box>
    )
}

