import { AccountCircle } from '@mui/icons-material'
import { Alert, AppBar, Autocomplete, Box, Button, CardMedia, IconButton, Menu, MenuItem, Rating, Snackbar, TextField, Toolbar, Typography } from '@mui/material'
import ShoppingBagIcon from '@mui/icons-material/ShoppingBag';
import FavoriteIcon from '@mui/icons-material/Favorite';
import HomeIcon from '@mui/icons-material/Home';
import React from 'react'
import classes from './showBootsById.module.css'
import { useState, useEffect } from 'react'
import axios from 'axios';

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
  const [open2, setOpen2] = React.useState(false);
  const [open3, setOpen3] = React.useState(false);
  const [severity, setSeverity] = useState('success')
  const [isFavorite, setIsFavorite] = useState(false)
  const [ratingValue, setRatingValue] = useState(0)
  const [avgRating, setAvgRating] = useState('')

  const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

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

  const handleClose2 = (event?: React.SyntheticEvent | Event, reason?: string) => {
    if (reason === 'clickaway') {
      return;
    }

    setOpen2(false);
  };

  const handleClose3 = (event?: React.SyntheticEvent | Event, reason?: string) => {
    if (reason === 'clickaway') {
      return;
    }

    setOpen3(false);
  };

  const handleRating = (rating: number) => {
    if (ratingValue === 0) {
      createRating(rating)
    }
    else {
      updateRating(rating)
    }

    setRatingValue(rating)
    setToggle(!toggle)
  }


  async function getBootsByIdAndSize() {
    const res = await axios.get('http://localhost:10000/boots/' + localStorage.getItem('bootsId') + '/' + filterValue, {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    }).then(function (res) {
      setNameValue(res.data.name)
      setSizeValue(res.data.size)
      setStockValue(res.data.quantity)
      setPriceValue(res.data.price)
      setDescriptionValue(res.data.description)
      setPath(res.data.bigPhotoPath)
    }
    )
  }

  async function updateRating(rating: number) {
    axios.put("http://localhost:10000/rating", { bootsId: localStorage.getItem("bootsId"), userId: localStorage.getItem("userId"), rating }, {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    })
  }

  async function createRating(rating: number) {
    axios.post("http://localhost:10000/rating", { bootsId: localStorage.getItem("bootsId"), userId: localStorage.getItem("userId"), rating }, {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    })
  }

  async function isFavoriteFunction() {
    const res = await axios.get('http://localhost:10000/favorites/isFavorites/' + localStorage.getItem("userId") + "/" + localStorage.getItem("bootsId"), {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    }).then(function (res) {
      setIsFavorite(res.data)
    })
  }

  const changeToggle = () => {
    setToggle(!toggle)
  }

  const handleShopingBag = () => {
    window.location.replace('http://localhost:3000/basket')
  }

  const handleFavorites = () => {
    window.location.replace('http://localhost:3000/favorites')
  }

  async function addEntryInBasket() {
    const userId = localStorage.getItem('userId')
    const bootsId = localStorage.getItem('bootsId')
    setOpen(true);
    const res = await axios.post("http://localhost:10000/basket/create", { 'idUser': userId, 'idBoots': bootsId, 'name': nameValue, 'size': sizeValue, 'price': priceValue }, {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    }).then(function (res) {
      if (res.status != 200) {
        setSeverity('error')
      }
      else {
        setSeverity('success')
      }
    })
  }

  async function addToFavorites() {
    if (isFavorite == false) {
      const res = await axios.post("http://localhost:10000/favorites", { bootsId: localStorage.getItem("bootsId"), userId: localStorage.getItem("userId") }, {
        headers: {
          authorization: 'Bearer ' + localStorage.getItem('token') as string
        }
      }).then(function (res) {
        if (res.status == 200) {
          setIsFavorite(true)
          setOpen2(true)
        }
      }).catch(function (error) {
        if (error.response.status == 400) {
          setOpen3(true)
        }
      })
    }
  }

  async function getRating() {
    const res = await axios.get("http://localhost:10000/rating/user/" + localStorage.getItem("userId") + "/boots/" + localStorage.getItem("bootsId"), {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    }).then(function (res) {
      if (res.data.rating != null) {
        setRatingValue(res.data.rating)
      }
    })
  }

  async function getAvgRating() {
    const res = await axios.get("http://localhost:10000/rating/boots/" + localStorage.getItem("bootsId") + "/average", {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    }).then(function (res) {
      setAvgRating(res.data)
    })
  }

  useEffect(() => {
    getBootsByIdAndSize()
    isFavoriteFunction()
    getRating()
    getAvgRating()
  }, [toggle]);

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
              <IconButton color="inherit" onClick={handleFavorites}>
                <FavoriteIcon />
              </IconButton>
              <IconButton color="inherit"
                onClick={handleShopingBag}>
                <ShoppingBagIcon />
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
        <Typography sx={{ position: 'absolute', marginLeft: '700px', marginTop: '570px' }} component="legend">Rating</Typography>
        <Typography sx={{ position: 'absolute', marginLeft: '700px', marginTop: '630px' }} component="legend">Avg</Typography>
        <Typography sx={{ position: 'absolute', marginLeft: '740px', marginTop: '630px' }} component="legend">{avgRating}</Typography>
        <Rating sx={{ position: 'absolute', marginLeft: '700px', marginTop: '600px' }}
          name="simple-controlled"
          value={ratingValue}
          precision={0.5}
          onChange={(event, newValue) => {
            handleRating(newValue as number);
          }} />
        <IconButton sx={{ position: 'absolute', marginLeft: '1100px', marginTop: '600px' }} onClick={addToFavorites}>
          {isFavorite &&
            <FavoriteIcon fontSize='large' color='error' />
          }
          {!isFavorite &&
            <FavoriteIcon fontSize='large' color='disabled' />
          }
        </IconButton>
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
          onChange={(event, value) => { setFilterValue(value as string), changeToggle() }}
          options={options}
          defaultValue={'40'}
          className={classes.select_filter}
          renderInput={(params) => <TextField {...params} label='Size' size={"small"} />} />
        <Button className={classes.add_to_bag} onClick={addEntryInBasket}>Add to bag</Button>
        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
          <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
            Item added to bag.
          </Alert>
        </Snackbar>
        <Snackbar open={open2} autoHideDuration={6000} onClose={handleClose}>
          <Alert onClose={handleClose2} severity="success" sx={{ width: '100%' }}>
            Item added to favorites.
          </Alert>
        </Snackbar>
        <Snackbar open={open3} autoHideDuration={6000} onClose={handleClose}>
          <Alert onClose={handleClose3} severity="error" sx={{ width: '100%' }}>
            You can't add another item to favorites.
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