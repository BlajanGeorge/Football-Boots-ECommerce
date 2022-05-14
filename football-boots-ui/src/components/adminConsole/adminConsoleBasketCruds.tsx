import { AppBar, Autocomplete, Box, Button, IconButton, Menu, MenuItem, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField, Toolbar, Typography } from '@mui/material'
import React, { useState } from 'react'
import HomeIcon from '@mui/icons-material/Home';
import { AccountCircle } from '@mui/icons-material';
import axios from 'axios';

export const AdminConsoleBasket = () => {

  const [auth, setAuth] = React.useState(true);
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const [basketData, setBasketData] = useState([]);
  const [idForSearch, setIdForSearch] = useState("");
  const [idUser, setIdUser] = useState('');
  const [idBoots, setIdBoots] = useState('');
  const [name, setName] = useState('');
  const [size, setSize] = useState('');
  const [price, setPrice] = useState('');
  const [idForDelete, setIdForDelete] = useState('');
  const [totalPrice, setTotalPrice] = useState('0');
  const [idTotalPrice, setIdTotalPrice] = useState('');


  const options = ['M', 'F'];

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

  const handleGetBasketAfterUserId = () => {
    getBasketByUserId(idForSearch)
  }

  async function getBasketByUserId(id: string) {
    const res = await axios.get('http://localhost:10000/basket/' + id, {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    }).then(function (res) {
      setBasketData(res.data)
    })
  }

  async function createEntry() {
    const res = await axios.post('http://localhost:10000/basket/create', { idUser, idBoots, name, size, price }, {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    })
  }

  async function deleteEntryById(id: string) {
    const res = await axios.delete('http://localhost:10000/basket/' + id, {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    })
  }

  async function computeTotalPrice(id: string) {
    const res = await axios.get('http://localhost:10000/basket/totalPrice/' + id as unknown as string, {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    }).then(function (res) {
      setTotalPrice(res.data)
    })
  }

  const handleCreateEntryBasket = () => {
    createEntry()
  }

  const handleDeleteEntry = () => {
    deleteEntryById(idForDelete)
  }

  const handleTotalPrice = () => {
    console.log(idTotalPrice)
    computeTotalPrice(idTotalPrice)
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
      <Typography sx={{ fontSize: '60px', marginLeft: '775px', marginTop: '20px' }}>BASKET</Typography>
      <TextField sx={{ position: 'absolute', width: '50px', marginTop: '48px', marginLeft: '350px' }} size='small' onChange={event => setIdForSearch(event.target.value as any)}></TextField>
      <Button sx={{ backgroundColor: '#1976d2', color: 'white', borderRadius: '8px', width: '100px', position: 'absolute', marginTop: '50px', marginLeft: '240px' }} onClick={handleGetBasketAfterUserId}>GET</Button>
      <Box sx={{ border: '2px black solid', width: '700px', position: 'absolute', marginTop: '150px', marginLeft: '15px' }}>
        <TableContainer component={Box}>
          <Table aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell><Typography sx={{ fontSize: '10px' }}>Basket id</Typography></TableCell>
                <TableCell align="right"> <Typography sx={{ fontSize: '10px' }}>User id</Typography></TableCell>
                <TableCell align="right"><Typography sx={{ fontSize: '10px' }}>Boots id</Typography></TableCell>
                <TableCell align="right"><Typography sx={{ fontSize: '10px' }}>Name</Typography></TableCell>
                <TableCell align="right"><Typography sx={{ fontSize: '10px' }}>Size</Typography></TableCell>
                <TableCell align="right"><Typography sx={{ fontSize: '10px' }}>Price</Typography></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {basketData.map((row: any) => (
                <TableRow
                  key={1 + (Math.random() * (100 - 1))}
                  sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                  <TableCell component="th" scope="row">
                    <Typography sx={{ fontSize: '8px' }}>{row.idBasket}</Typography>
                  </TableCell>
                  <TableCell align="right"><Typography sx={{ fontSize: '8px' }}>{row.idUser}</Typography></TableCell>
                  <TableCell align="right"><Typography sx={{ fontSize: '8px' }}>{row.idBoots}</Typography></TableCell>
                  <TableCell align="right"><Typography sx={{ fontSize: '8px' }}>{row.name}</Typography></TableCell>
                  <TableCell align="right"><Typography sx={{ fontSize: '8px' }}>{row.size}</Typography></TableCell>
                  <TableCell align="right"><Typography sx={{ fontSize: '8px' }}>{row.price}</Typography></TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Box>
      <Button sx={{ backgroundColor: '#1976d2', color: 'white', borderRadius: '8px', width: '200px', position: 'absolute', marginTop: '50px', marginLeft: '920px' }} onClick={handleCreateEntryBasket}>CREATE</Button>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '880px', marginTop: '150px' }}>User id :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '145px', marginLeft: '960px' }} onChange={event => setIdUser(event.target.value as string)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1230px', marginTop: '150px' }} >Boots id :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '145px', marginLeft: '1320px' }} onChange={event => setIdBoots(event.target.value as string)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '890px', marginTop: '220px' }}>Name :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '215px', marginLeft: '960px' }} onChange={event => setName(event.target.value as string)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1180px', marginTop: '220px' }}>Size :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '215px', marginLeft: '1240px', width: '50px' }} onChange={event => setSize(event.target.value as string)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1320px', marginTop: '220px' }}>Price :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '215px', marginLeft: '1380px', width: '75px' }} onChange={event => setPrice(event.target.value as string)}></TextField>
      <Button sx={{ backgroundColor: '#1976d2', color: 'white', borderRadius: '8px', width: '200px', position: 'absolute', marginTop: '50px', marginLeft: '1200px' }} onClick={handleDeleteEntry}>Delete</Button>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '48px', marginLeft: '1410px', width: '50px' }} onChange={event => setIdForDelete(event.target.value as string)}></TextField>
      <Button sx={{ backgroundColor: '#1976d2', color: 'white', borderRadius: '8px', width: '200px', position: 'absolute', marginTop: '300px', marginLeft: '920px' }} onClick={handleTotalPrice}>Total price</Button>
      <Typography sx={{ position: 'absolute', marginLeft: '1200px', marginTop: '308px' }}>Total price: {totalPrice}</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '300px', marginLeft: '1135px', width: '50px' }} onChange={event => setIdTotalPrice(event.target.value as string)}></TextField>
    </Box>
  )
}