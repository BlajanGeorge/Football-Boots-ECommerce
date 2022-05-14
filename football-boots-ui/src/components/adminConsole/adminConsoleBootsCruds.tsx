import { AppBar, Autocomplete, Box, Button, IconButton, Menu, MenuItem, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField, Toolbar, Typography } from '@mui/material'
import React, { useState } from 'react'
import HomeIcon from '@mui/icons-material/Home';
import { AccountCircle } from '@mui/icons-material';
import axios from 'axios';

export const AdminConsoleBoots = () => {

  const [auth, setAuth] = React.useState(true);
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const [bootsData, setBootsData] = useState([]);
  const [idForSearch, setIdForSearch] = useState("");
  const [sizeForSearch, setSizeForSearch] = useState("")
  const [brand, setBrand] = useState('NIKE');
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [photoPath, setPhotoPath] = useState('');
  const [bigPhotoPath, setBigPhotoPath] = useState('');
  const [idForDelete, setIdForDelete] = useState('');
  const [idForUpdate, setIdForUpdate] = useState('');
  const [sizeForUpdate, setSizeForUpdate] = useState('');
  const [priceForSize35, setPriceForSize35] = useState(0);
  const [priceForSize36, setPriceForSize36] = useState(0);
  const [priceForSize37, setPriceForSize37] = useState(0);
  const [priceForSize38, setPriceForSize38] = useState(0);
  const [priceForSize39, setPriceForSize39] = useState(0);
  const [priceForSize40, setPriceForSize40] = useState(0);
  const [priceForSize41, setPriceForSize41] = useState(0);
  const [priceForSize42, setPriceForSize42] = useState(0);
  const [priceForSize43, setPriceForSize43] = useState(0);
  const [priceForSize44, setPriceForSize44] = useState(0);
  const [priceForSize45, setPriceForSize45] = useState(0);
  const [quantityForSize35, setQuantityForSize35] = useState(0);
  const [quantityForSize36, setQuantityForSize36] = useState(0);
  const [quantityForSize37, setQuantityForSize37] = useState(0);
  const [quantityForSize38, setQuantityForSize38] = useState(0);
  const [quantityForSize39, setQuantityForSize39] = useState(0);
  const [quantityForSize40, setQuantityForSize40] = useState(0);
  const [quantityForSize41, setQuantityForSize41] = useState(0);
  const [quantityForSize42, setQuantityForSize42] = useState(0);
  const [quantityForSize43, setQuantityForSize43] = useState(0);
  const [quantityForSize44, setQuantityForSize44] = useState(0);
  const [quantityForSize45, setQuantityForSize45] = useState(0);
  const [nameForUpdateById, setNameForUpdateById] = useState("");
  const [descriptionForUpdateById, setDescriptionForUpdateById] = useState("");
  const [brandForUpdatebyId, setBrandForUpdateById] = useState("NIKE");
  const [idForUpdateById, setIdForUpdateById] = useState("");
  const [priceForUpdate, setPriceForUpdate] = useState(0);
  const [quantityForUpdate, setQuantityForUpdate] = useState(0);



  const options = ['NIKE', 'ADIDAS', 'PUMA'];

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

  const handleGetBoots = () => {
    if (idForSearch == "" || sizeForSearch == "") {
      getBoots()
    }
    else {
      getBootsAfterIdAndSize(idForSearch, sizeForSearch)
    }
  }

  const handleDeleteBoots = () => {
    deleteBoots(idForDelete)
  }

  const handleUpdateById = () => {
    updateById(idForUpdateById)
  }

  const handleUpdateByIdAndSize = () => {
    updateByIdAndSize(idForUpdate, sizeForUpdate)
  }

  async function getBootsAfterIdAndSize(id: string, size: string) {
    const res = await axios.get('http://localhost:10000/boots/' + id + '/' + size, {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    }).then(function (res) {
      setBootsData([res.data] as any)
    })
  }

  async function updateByIdAndSize(id: string, size: string) {
    const res = await axios.put('http://localhost:10000/boots/' + id + '/' + size, { price: priceForUpdate, quantity: quantityForUpdate }, {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    })
  }

  async function deleteBoots(id: string) {
    const res = await axios.delete('http://localhost:10000/boots/' + id, {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    })
  }

  async function updateById(id: string) {
    const res = await axios.put('http://localhost:10000/boots/' + id, { name: nameForUpdateById, description: descriptionForUpdateById, brand: brandForUpdatebyId }, {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    })
  }

  async function getBoots() {
    const res = await axios.get('http://localhost:10000/boots', {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    }).then(function (res) {
      setBootsData(res.data)
    })
  }

  async function createBoots() {
    const res = await axios.post('http://localhost:10000/boots', {
      name, description, brand, photoPath, bigPhotoPath, footballBootsAttributesCreateRequestList: [{ size: 35, price: priceForSize35, quantity: quantityForSize35 },
      { size: 36, price: priceForSize36, quantity: quantityForSize36 }, { size: 37, price: priceForSize37, quantity: quantityForSize37 }, { size: 38, price: priceForSize38, quantity: quantityForSize38 },
      { size: 39, price: priceForSize39, quantity: quantityForSize39 }, { size: 40, price: priceForSize40, quantity: quantityForSize40 }, { size: 41, price: priceForSize41, quantity: quantityForSize41 },
      { size: 42, price: priceForSize42, quantity: quantityForSize42 }, { size: 43, price: priceForSize43, quantity: quantityForSize43 }, { size: 44, price: priceForSize44, quantity: quantityForSize44 },
      { size: 45, price: priceForSize45, quantity: quantityForSize45 }]
    }, {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    })
  }


  const handleCreateBoots = () => {
    createBoots()
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
      <Typography sx={{ fontSize: '60px', marginLeft: '775px', marginTop: '20px' }}>BOOTS</Typography>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '340px', marginTop: '100px' }} >Size :</Typography>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '240px', marginTop: '100px' }} >Id :</Typography>
      <TextField sx={{ position: 'absolute', width: '50px', marginTop: '95px', marginLeft: '400px' }} size='small' onChange={event => setSizeForSearch(event.target.value as any)}></TextField>
      <TextField sx={{ position: 'absolute', width: '50px', marginTop: '95px', marginLeft: '275px' }} size='small' onChange={event => setIdForSearch(event.target.value as any)}></TextField>
      <Button sx={{ backgroundColor: '#1976d2', color: 'white', borderRadius: '8px', width: '100px', position: 'absolute', marginTop: '50px', marginLeft: '300px' }} onClick={handleGetBoots}>GET</Button>
      <Box sx={{ border: '2px black solid', width: '700px', position: 'absolute', marginTop: '150px', marginLeft: '15px' }}>
        <TableContainer component={Box}>
          <Table aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell><Typography sx={{ fontSize: '10px' }}>Id</Typography></TableCell>
                <TableCell align="right"> <Typography sx={{ fontSize: '10px' }}>Name</Typography></TableCell>
                <TableCell align="right"><Typography sx={{ fontSize: '10px' }}>Price</Typography></TableCell>
                <TableCell align="right"><Typography sx={{ fontSize: '10px' }}>Quantity</Typography></TableCell>
                <TableCell align="right"><Typography sx={{ fontSize: '10px' }}>Size</Typography></TableCell>
                <TableCell align="right"><Typography sx={{ fontSize: '10px' }}>Description</Typography></TableCell>
                <TableCell align="right"><Typography sx={{ fontSize: '10px' }}>Brand</Typography></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {bootsData.map((row: any) => (
                <TableRow
                  key={1 + (Math.random() * (100 - 1))}
                  sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                  <TableCell component="th" scope="row">
                    <Typography sx={{ fontSize: '8px' }}>{row.id}</Typography>
                  </TableCell>
                  <TableCell align="right"><Typography sx={{ fontSize: '8px' }}>{row.name}</Typography></TableCell>
                  <TableCell align="right"><Typography sx={{ fontSize: '8px' }}>{row.price}</Typography></TableCell>
                  <TableCell align="right"><Typography sx={{ fontSize: '8px' }}>{row.quantity}</Typography></TableCell>
                  <TableCell align="right"><Typography sx={{ fontSize: '8px' }}>{row.size}</Typography></TableCell>
                  <TableCell align="right"><Typography sx={{ fontSize: '8px' }}>{row.description}</Typography></TableCell>
                  <TableCell align="right"><Typography sx={{ fontSize: '8px' }}>{row.brand}</Typography></TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Box>
      <Button sx={{ backgroundColor: '#1976d2', color: 'white', borderRadius: '8px', width: '200px', position: 'absolute', marginTop: '50px', marginLeft: '1100px' }} onClick={handleCreateBoots}>CREATE</Button>
      <Button sx={{ backgroundColor: '#1976d2', color: 'white', borderRadius: '8px', width: '200px', position: 'absolute', marginTop: '300px', marginLeft: '1150px' }} onClick={handleDeleteBoots}>DELETE</Button>
      <TextField sx={{ position: 'absolute', width: '50px', marginTop: '300px', marginLeft: '1360px' }} size='small' onChange={event => setIdForDelete(event.target.value as any)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '890px', marginTop: '150px' }}>Name :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '145px', marginLeft: '960px' }} onChange={event => setName(event.target.value as string)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1200px', marginTop: '150px' }} >Description :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '145px', marginLeft: '1320px' }} onChange={event => setDescription(event.target.value as string)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '890px', marginTop: '220px' }}>Photo path :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '215px', marginLeft: '1005px' }} onChange={event => setPhotoPath(event.target.value as string)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '890px', marginTop: '305px' }}>Brand :</Typography>
      <Autocomplete
        onChange={(event, value) => setBrand(value as string)}
        disablePortal
        id="combo-box-demo"
        options={options}
        value={brand}
        defaultValue={brand}
        sx={{ position: 'absolute', marginLeft: '980px', marginTop: '300px', width: '150px' }}
        renderInput={(params) => <TextField {...params} size={"small"} />} />
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1220px', marginTop: '220px' }}>Big photo path :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '215px', marginLeft: '1370px', width: '200px' }} onChange={event => setBigPhotoPath(event.target.value as string)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '890px', marginTop: '380px' }}>Football boots attributes :</Typography>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1150px', marginTop: '380px' }}>Size 35 :</Typography>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1250px', marginTop: '380px' }}>Price :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '375px', marginLeft: '1310px', width: '75px' }} onChange={event => setPriceForSize35(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1400px', marginTop: '380px' }}>Quantity :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '375px', marginLeft: '1490px', width: '75px' }} onChange={event => setQuantityForSize35(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1150px', marginTop: '420px' }}>Size 36 :</Typography>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1250px', marginTop: '420px' }}>Price :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '415px', marginLeft: '1310px', width: '75px' }} onChange={event => setPriceForSize36(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1400px', marginTop: '420px' }}>Quantity :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '415px', marginLeft: '1490px', width: '75px' }} onChange={event => setQuantityForSize36(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1150px', marginTop: '460px' }}>Size 37 :</Typography>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1250px', marginTop: '460px' }}>Price :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '455px', marginLeft: '1310px', width: '75px' }} onChange={event => setPriceForSize37(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1400px', marginTop: '460px' }}>Quantity :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '455px', marginLeft: '1490px', width: '75px' }} onChange={event => setQuantityForSize37(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1150px', marginTop: '500px' }}>Size 38 :</Typography>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1250px', marginTop: '500px' }}>Price :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '495px', marginLeft: '1310px', width: '75px' }} onChange={event => setPriceForSize38(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1400px', marginTop: '500px' }}>Quantity :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '495px', marginLeft: '1490px', width: '75px' }} onChange={event => setQuantityForSize38(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1150px', marginTop: '540px' }}>Size 39 :</Typography>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1250px', marginTop: '540px' }}>Price :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '535px', marginLeft: '1310px', width: '75px' }} onChange={event => setPriceForSize39(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1400px', marginTop: '540px' }}>Quantity :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '535px', marginLeft: '1490px', width: '75px' }} onChange={event => setQuantityForSize39(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1150px', marginTop: '580px' }}>Size 40 :</Typography>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1250px', marginTop: '580px' }}>Price :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '575px', marginLeft: '1310px', width: '75px' }} onChange={event => setPriceForSize40(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1400px', marginTop: '580px' }}>Quantity :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '575px', marginLeft: '1490px', width: '75px' }} onChange={event => setQuantityForSize40(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1150px', marginTop: '620px' }}>Size 41 :</Typography>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1250px', marginTop: '620px' }}>Price :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '615px', marginLeft: '1310px', width: '75px' }} onChange={event => setPriceForSize41(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1400px', marginTop: '620px' }}>Quantity :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '615px', marginLeft: '1490px', width: '75px' }} onChange={event => setQuantityForSize41(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1150px', marginTop: '660px' }}>Size 42 :</Typography>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1250px', marginTop: '660px' }}>Price :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '655px', marginLeft: '1310px', width: '75px' }} onChange={event => setPriceForSize42(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1400px', marginTop: '660px' }}>Quantity :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '655px', marginLeft: '1490px', width: '75px' }} onChange={event => setQuantityForSize42(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1150px', marginTop: '700px' }}>Size 43 :</Typography>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1250px', marginTop: '700px' }}>Price :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '695px', marginLeft: '1310px', width: '75px' }} onChange={event => setPriceForSize43(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1400px', marginTop: '700px' }}>Quantity :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '695px', marginLeft: '1490px', width: '75px' }} onChange={event => setQuantityForSize43(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1150px', marginTop: '740px' }}>Size 44 :</Typography>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1250px', marginTop: '740px' }}>Price :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '735px', marginLeft: '1310px', width: '75px' }} onChange={event => setPriceForSize44(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1400px', marginTop: '740px' }}>Quantity :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '735px', marginLeft: '1490px', width: '75px' }} onChange={event => setQuantityForSize44(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1150px', marginTop: '780px' }}>Size 45 :</Typography>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1250px', marginTop: '780px' }}>Price :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '775px', marginLeft: '1310px', width: '75px' }} onChange={event => setPriceForSize45(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1400px', marginTop: '780px' }}>Quantity :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '775px', marginLeft: '1490px', width: '75px' }} onChange={event => setQuantityForSize45(event.target.value as unknown as number)}></TextField>
      <Button sx={{ backgroundColor: '#1976d2', color: 'white', borderRadius: '8px', width: '200px', position: 'absolute', marginTop: '850px', marginLeft: '900px' }} onClick={handleUpdateById}>Update by Id</Button>
      <Button sx={{ backgroundColor: '#1976d2', color: 'white', borderRadius: '8px', width: '200px', position: 'absolute', marginTop: '850px', marginLeft: '1400px' }} onClick={handleUpdateByIdAndSize}>Update by Id and size</Button>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '890px', marginTop: '920px' }}>Name :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '915px', marginLeft: '960px' }} onChange={event => setNameForUpdateById(event.target.value as string)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '890px', marginTop: '965px' }}>Description :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '960px', marginLeft: '1010px' }} onChange={event => setDescriptionForUpdateById(event.target.value as string)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '890px', marginTop: '1005px' }}>Brand :</Typography>
      <Autocomplete
        onChange={(event, value) => setBrandForUpdateById(value as string)}
        disablePortal
        id="combo-box-demo"
        options={options}
        value={brandForUpdatebyId}
        defaultValue={brandForUpdatebyId}
        sx={{ position: 'absolute', marginLeft: '980px', marginTop: '1005px', width: '150px' }}
        renderInput={(params) => <TextField {...params} size={"small"} />} />
      <TextField sx={{ position: 'absolute', width: '50px', marginTop: '850px', marginLeft: '1120px' }} size='small' onChange={event => setIdForUpdateById(event.target.value as any)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1520px', marginTop: '900px' }} >Id :</Typography>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1390px', marginTop: '900px' }} >Size :</Typography>
      <TextField sx={{ position: 'absolute', width: '50px', marginTop: '895px', marginLeft: '1440px' }} size='small' onChange={event => setIdForUpdate(event.target.value as any)}></TextField>
      <TextField sx={{ position: 'absolute', width: '50px', marginTop: '895px', marginLeft: '1550px' }} size='small' onChange={event => setSizeForUpdate(event.target.value as any)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1400px', marginTop: '950px' }}>Price :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '945px', marginLeft: '1470px', width: '75px' }} onChange={event => setPriceForUpdate(event.target.value as unknown as number)}></TextField>
      <Typography sx={{ position: 'absolute', fontSize: '20px', marginLeft: '1400px', marginTop: '1000px' }}>Quantity :</Typography>
      <TextField size='small' sx={{ position: 'absolute', marginTop: '995px', marginLeft: '1500px', width: '75px' }} onChange={event => setQuantityForUpdate(event.target.value as unknown as number)}></TextField>
    </Box>
  )
}