import React, { useEffect } from 'react'
import { useState } from 'react'
import { Button, Box, AppBar, Toolbar, IconButton, Typography, Menu, MenuItem } from '@mui/material'
import ShoppingBagIcon from '@mui/icons-material/ShoppingBag';
import FavoriteIcon from '@mui/icons-material/Favorite';
import HomeIcon from '@mui/icons-material/Home';
import { AccountCircle, RemoveCircle } from '@mui/icons-material';
import axios from 'axios'
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import DeleteIcon from '@mui/icons-material/Delete';
import { v4 as uuidv4 } from 'uuid';

export const Basket = () => {

  const [auth, setAuth] = React.useState(true);
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const [basket, setBasket] = useState([])
  const [toggle, setToggle] = useState(false)
  const [totalPrice, setTotalPrice] = useState('')

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

  async function getBasketData() {
    const res = await axios.get('http://localhost:10000/basket/' + localStorage.getItem('userId'), {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    }).then(function (res) {
      setBasket(res.data)
    })
  }

  async function deleteEntryInBasket(id: number) {
    await axios.delete('http://localhost:10000/basket/' + id, {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    })
  }

  const handleDeleteFromBasket = (id: number) => {
    deleteEntryInBasket(id)
    setToggle(!toggle)
  }

  async function getPrice() {
    const res = await axios.get('http://localhost:10000/basket/totalPrice/' + localStorage.getItem('userId'), {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    }).then(function (res) {
      var total = 0
      total += res.data as number
      setTotalPrice(total as unknown as string)
    })
  }

  const order = () => {
    localStorage.setItem("totalPrice", totalPrice)
    window.location.replace('http://localhost:3000/order')
  }

  const handleShopingBag = () => {
    window.location.replace('http://localhost:3000/basket')
  }

  const handleFavorites = () => {
    window.location.replace('http://localhost:3000/favorites')
  }

  useEffect(() => {
    getBasketData()
    getPrice()
  }, [toggle])

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
      {(totalPrice as unknown as Number) != 0 &&
        <Button onClick={order} sx={{ position: 'absolute', width: '200px', backgroundColor: 'black', borderRadius: '8px', color: 'white', marginLeft: '770px', marginTop: '100px' }}>Checkout</Button>
      }
      <ShoppingCartIcon sx={{ position: 'absolute', marginTop: '300px', fontSize: '300px', marginLeft: '75px', width: '300px' }} />
      <ShoppingCartIcon sx={{ position: 'absolute', marginTop: '300px', fontSize: '300px', marginLeft: '1400px', width: '300px' }} />
      <Box sx={{ position: 'absolute', marginTop: '200px', marginLeft: '550px', border: '2px black solid', borderRadius: '8px' }}>
        <TableContainer component={Box}>
          <Table sx={{ width: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell><Typography sx={{ fontSize: '40px' }}>Product</Typography></TableCell>
                <TableCell align="right"> <Typography sx={{ fontSize: '40px' }}>Size</Typography></TableCell>
                <TableCell align="right"><Typography sx={{ fontSize: '40px' }}>Price</Typography></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {basket.map((row: any) => (
                <TableRow
                  key={uuidv4()}
                  sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                  <TableCell component="th" scope="row">
                    <Typography sx={{ fontSize: '30px' }}>{row.name}</Typography>
                  </TableCell>
                  <TableCell align="right"><Typography sx={{ fontSize: '30px' }}>{row.size}</Typography></TableCell>
                  <TableCell align="right"><Typography sx={{ fontSize: '30px' }}>{row.price}</Typography></TableCell>
                  <IconButton sx={{ position: 'absolute', marginTop: '20px' }} onClick={() => handleDeleteFromBasket(row.idBasket)}>
                    <DeleteIcon />
                  </IconButton>
                </TableRow>
              ))}
              <TableCell align="right"><Typography sx={{ fontSize: '30px' }}>Total: {totalPrice}</Typography></TableCell>
            </TableBody>
          </Table>
        </TableContainer>
      </Box>
    </Box>
  )
}