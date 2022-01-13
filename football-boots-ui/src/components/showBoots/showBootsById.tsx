import { AccountCircle } from '@mui/icons-material'
import { Alert, AppBar, Autocomplete, Box, Button, CardMedia, IconButton, Menu, MenuItem, Snackbar, TextField, Toolbar, Typography } from '@mui/material'
import ShoppingBagIcon from '@mui/icons-material/ShoppingBag';
import FavoriteIcon from '@mui/icons-material/Favorite';
import HomeIcon from '@mui/icons-material/Home';
import React from 'react'
import classes from './showBootsById.module.css'
import { useState, useEffect } from 'react'
import axios from 'axios';
import CloseIcon from '@mui/icons-material/Close';

export const ShowBootsById = () => {

    const [auth, setAuth] = React.useState(true);
    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const [filterValue, setFilterValue] = useState('40')
    const options = ['35', '36', '37', '38', '39', '40', '41', '42', '43', '44', '45'];
    const [toggle, setToggle] = useState(true)
    const [nameValue, setNameValue] = useState('')
    const [sizeValue, setSizeValue] = useState('')
    const [stockValue, setStockValue] = useState('')
    const [priceValue, setPriceValue] = useState('')
    const [descriptionValue, setDescriptionValue] = useState('')
    const [path, setPath] = useState('')
    const [open, setOpen] = React.useState(false);
    const [severity, setSeverity] = useState('success')

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

    const handleClose = (event?: React.SyntheticEvent | Event, reason?: string) => {
      if (reason === 'clickaway') {
        return;
      }
  
      setOpen(false);
    };
  

    async function getBootsByIdAndSize(){
        const res = axios.get('http://localhost:10000/boots/' + localStorage.getItem('bootsId') + '/' + filterValue, {
        headers: {
            authorization:'Bearer ' + localStorage.getItem('token') as string 
         }
        }).then(function(res){
            setNameValue(res.data.name)
            setSizeValue(res.data.size)
            setStockValue(res.data.quantity)
            setPriceValue(res.data.price)
            setDescriptionValue(res.data.description)
            setPath(res.data.bigPhotoPath)
        }
        )
    }

    const changeToggle = () => {
        setToggle(!toggle)
    }

    const handleShopingBag = () => {
      window.location.replace('http://localhost:3000/basket')
    }

    async function addEntryInBasket(){
        const  userId = localStorage.getItem('userId')
        const bootsId = localStorage.getItem('bootsId')
        setOpen(true);
       const res = await axios.post("http://localhost:10000/basket/create", {'idUser':userId,'idBoots':bootsId,'name':nameValue,'size':sizeValue,'price':priceValue}, {
            headers: {
                authorization: 'Bearer ' + localStorage.getItem('token') as string
            }
        }).then(function(res){
          if(res.status != 200){
            setSeverity('error')
          }
          else
          {
            setSeverity('success')
          }
        })
    }

    useEffect(() => {
        getBootsByIdAndSize()
    },[toggle]);

    return (
        <Box sx={{ flexGrow: 1}}>
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
              <Box className={classes.box_for_render_boots}>
                  <Typography className={classes.name}>{nameValue}</Typography>
                  <Typography className={classes.size}>Size: </Typography>
                  <Typography className={classes.size_value}>{sizeValue}</Typography>
                  <Typography className={classes.stock}>Stock:</Typography>
                  <Typography className={classes.stock_value}>{stockValue}</Typography>
                  <Typography className={classes.price}>Price:</Typography>
                  <Typography className={classes.price_value}>{priceValue}</Typography>
                  <Typography className={classes.description}>Description:</Typography>
                  <Typography className={classes.description_value}>{descriptionValue}</Typography>
                  <Autocomplete
                   isOptionEqualToValue={(option, value) => option === value}
                   onChange={(event, value) => {setFilterValue(value as string), changeToggle()}}
                   options={options}
                   className={classes.select_filter}
                   renderInput={(params) => <TextField {...params} label='Size' size={"small"} />}/>
                   <Button className={classes.add_to_bag} onClick={addEntryInBasket}>Add to bag</Button>
                   <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
                   <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
                     Item added to bag.
                    </Alert>
                    </Snackbar>
                   <Box className={classes.image_box}>
                   <CardMedia
                    component="img"
                    height="700"
                    image={path}
                    />
                    </Box>
              </Box>
              </Box>
              )
}