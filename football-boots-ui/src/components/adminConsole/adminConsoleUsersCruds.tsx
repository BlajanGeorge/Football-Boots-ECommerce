import { AppBar, Autocomplete, Box, Button, IconButton, Menu, MenuItem, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField, Toolbar, Typography } from '@mui/material'
import React, { useState } from 'react'
import HomeIcon from '@mui/icons-material/Home';
import { AccountCircle } from '@mui/icons-material';
import axios from 'axios';

export const AdminConsoleUsers = () => {

    const [auth, setAuth] = React.useState(true);
    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const [userData, setUserData] = useState([]);
    const [idForSearch, setIdForSearch] = useState("");
    const [gender, setGender] = useState('M');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email,setEmail] = useState('');
    const [age, setAge] = useState('');
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [idForDelete, setIdForDelete] = useState('');
    const [idForUpdate, setIdForUpdate] = useState('');


    const options = ['M', 'F'];

    const handleLogout = () => {
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

      const handleGetUsers = () => {
          if(idForSearch == ""){
          getUsers()
          }
          else
          {
              getUserAfterId(idForSearch)
          }
      }

      async function getUserAfterId(id : string){
        const res = await axios.get('http://localhost:10000/users/' + id , {
          headers: {
              authorization:'Bearer ' + localStorage.getItem('token') as string 
           }
        }).then(function(res){
             setUserData([res.data])
        })
    }

      async function getUsers(){
          const res = await axios.get('http://localhost:10000/users', {
            headers: {
                authorization:'Bearer ' + localStorage.getItem('token') as string 
             }
          }).then(function(res){
              setUserData(res.data)
          })
      }

      async function createCustomer(){
        const res = await axios.post('http://localhost:10000/users/registration/customer', {firstName, lastName, email, gender, age, password: oldPassword}, {
          headers: {
              authorization:'Bearer ' + localStorage.getItem('token') as string 
           }
        })
    }

    async function createAdmin(){
      const res = await axios.post('http://localhost:10000/users/registration/admin', {firstName, lastName, email, gender, age, password: oldPassword}, {
        headers: {
            authorization:'Bearer ' + localStorage.getItem('token') as string 
         }
      })
  }

  async function updateUser(id : string){
    const res = await axios.put('http://localhost:10000/users/' + id, {firstName, lastName, email, gender, age, oldPassword, newPassword}, {
      headers: {
          authorization:'Bearer ' + localStorage.getItem('token') as string 
       }
    })
}

async function deleteUser(id : string){
  const res = await axios.delete('http://localhost:10000/users/' + id, {
    headers: {
        authorization:'Bearer ' + localStorage.getItem('token') as string 
     }
  })
}



      const handleCreateCustomer = () => {
        createCustomer()
      }

      const handleCreateAdmin = () => {
        createAdmin()
      }

      const handleUpdateUser = () => {
        updateUser(idForUpdate)
      }

      const handleDeleteUser = () => {
        deleteUser(idForDelete)
      }
      
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
      <Typography sx={{fontSize:'60px', marginLeft:'775px', marginTop:'20px'}}>USERS</Typography>
      <TextField sx={{position:'absolute', width:'50px', marginTop:'48px', marginLeft:'350px'}} size='small' onChange={event => setIdForSearch(event.target.value as any)}></TextField>
      <Button sx={{backgroundColor:'#1976d2', color:'white', borderRadius:'8px', width:'100px', position:'absolute', marginTop:'50px', marginLeft:'240px'}} onClick={handleGetUsers}>GET</Button>
      <Box sx={{border:'2px black solid', width:'700px', position:'absolute', marginTop:'150px', marginLeft:'15px'}}>
      <TableContainer component={Box}>
      <Table aria-label="simple table">
      <TableHead>
          <TableRow>
            <TableCell><Typography sx={{fontSize:'10px'}}>Id</Typography></TableCell>
            <TableCell align="right"> <Typography sx={{fontSize:'10px'}}>First Name</Typography></TableCell>
            <TableCell align="right"><Typography sx={{fontSize:'10px'}}>Last Name</Typography></TableCell>
            <TableCell align="right"><Typography sx={{fontSize:'10px'}}>Email</Typography></TableCell>
            <TableCell align="right"><Typography sx={{fontSize:'10px'}}>Gender</Typography></TableCell>
            <TableCell align="right"><Typography sx={{fontSize:'10px'}}>Age</Typography></TableCell>
            <TableCell align="right"><Typography sx={{fontSize:'10px'}}>Role</Typography></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {userData.map((row: any) => (
            <TableRow
              key={1 + (Math.random() * (100 - 1))}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell component="th" scope="row">
                  <Typography sx={{fontSize:'8px'}}>{row.id}</Typography>
              </TableCell>
              <TableCell align="right"><Typography sx={{fontSize:'8px'}}>{row.firstName}</Typography></TableCell>
              <TableCell align="right"><Typography sx={{fontSize:'8px'}}>{row.lastName}</Typography></TableCell>
              <TableCell align="right"><Typography sx={{fontSize:'8px'}}>{row.email}</Typography></TableCell>
              <TableCell align="right"><Typography sx={{fontSize:'8px'}}>{row.gender}</Typography></TableCell>
              <TableCell align="right"><Typography sx={{fontSize:'8px'}}>{row.age}</Typography></TableCell>
              <TableCell align="right"><Typography sx={{fontSize:'8px'}}>{row.role}</Typography></TableCell>
            </TableRow>
          ))}
        </TableBody>
        </Table>
        </TableContainer>
        </Box>
        <Button sx={{backgroundColor:'#1976d2', color:'white', borderRadius:'8px', width:'200px', position:'absolute', marginTop:'50px', marginLeft:'840px'}} onClick={handleCreateCustomer}>Create customer</Button>
        <Button sx={{backgroundColor:'#1976d2', color:'white', borderRadius:'8px', width:'200px', position:'absolute', marginTop:'50px', marginLeft:'1100px'}} onClick={handleCreateAdmin}>Create admin</Button>
        <Button sx={{backgroundColor:'#1976d2', color:'white', borderRadius:'8px', width:'200px', position:'absolute', marginTop:'50px', marginLeft:'1360px'}} onClick={handleUpdateUser}>UPDATE</Button>
        <Typography sx={{position:'absolute', fontSize:'20px', marginLeft:'840px', marginTop:'150px'}}>First name :</Typography>
        <TextField size='small' sx={{position:'absolute', marginTop:'145px', marginLeft:'960px'}} onChange={event => setFirstName(event.target.value as string)}></TextField>
        <Typography sx={{position:'absolute', fontSize:'20px', marginLeft:'1200px', marginTop:'150px'}} >Last name :</Typography>
        <TextField size='small' sx={{position:'absolute', marginTop:'145px', marginLeft:'1320px'}} onChange={event => setLastName(event.target.value as string)}></TextField>
        <Typography sx={{position:'absolute', fontSize:'20px', marginLeft:'890px', marginTop:'220px'}}>Email :</Typography>
        <TextField size='small' sx={{position:'absolute', marginTop:'215px', marginLeft:'960px'}} onChange={event => setEmail(event.target.value as string)}></TextField>
        <Typography sx={{position:'absolute', fontSize:'20px', marginLeft:'1200px', marginTop:'220px'}}>Gender :</Typography>
        <Autocomplete
      onChange={(event, value) => setGender(value as string)}
      disablePortal
      id="combo-box-demo"
      options={options}
      value={gender}
      defaultValue={gender}
      sx={{position:'absolute', marginLeft:'1300px', marginTop:'215px'}}
      renderInput={(params) => <TextField {...params} size={"small"} />}/>
     <Typography sx={{position:'absolute', fontSize:'20px', marginLeft:'1450px', marginTop:'220px'}}>Age :</Typography>
     <TextField size='small' sx={{position:'absolute', marginTop:'215px', marginLeft:'1500px', width:'50px'}} onChange={event => setAge(event.target.value as string)}></TextField>
     <Typography sx={{position:'absolute', fontSize:'20px', marginLeft:'840px', marginTop:'290px'}}>Password :</Typography>
     <TextField size='small' sx={{position:'absolute', marginTop:'285px', marginLeft:'960px'}} onChange={event => setOldPassword(event.target.value as string)}></TextField>
     <Typography sx={{position:'absolute', fontSize:'20px', marginLeft:'1200px', marginTop:'290px'}}>New password :</Typography>
     <TextField size='small' sx={{position:'absolute', marginTop:'285px', marginLeft:'1350px'}} onChange={event => setNewPassword(event.target.value as string)}></TextField>
     <Button sx={{backgroundColor:'#1976d2', color:'white', borderRadius:'8px', width:'200px', position:'absolute', marginTop:'400px', marginLeft:'1000px'}} onClick={handleDeleteUser}>Delete</Button>
     <TextField size='small' sx={{position:'absolute', marginTop:'398px', marginLeft:'1210px', width:'50px'}} onChange={event => setIdForDelete(event.target.value as string)}></TextField>
     <TextField size='small' sx={{position:'absolute', marginTop:'50px', marginLeft:'1570px', width:'50px'}} onChange={event => setIdForUpdate(event.target.value as string)}></TextField>
      </Box>
    )
}