import { Button, Box, FormControlLabel, Switch, AppBar, Toolbar, IconButton, Typography, Menu, MenuItem, Checkbox, TextField, Slider, Select, FormControl, InputLabel, Autocomplete, Card, TabScrollButton, createMuiTheme, List, ListItem, ListItemAvatar, Avatar, ListItemText } from '@mui/material'
import React from 'react'
import { io, Socket } from 'socket.io-client'
import { useState, useEffect } from 'react'
import { AccountCircle, MaximizeRounded, PauseOutlined } from '@mui/icons-material'
import ShoppingBagIcon from '@mui/icons-material/ShoppingBag';
import FavoriteIcon from '@mui/icons-material/Favorite';
import HomeIcon from '@mui/icons-material/Home';
import classes from './defaultboots.module.css'
import axios from 'axios'
import SendIcon from '@mui/icons-material/Send';
import { ChatHeadRight } from './chatHeadRight'
import { ChatHeadLeft } from './chatHeadLeft'
import { get } from 'https'

export const Chat = () => {
  const [auth, setAuth] = React.useState(true);
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const [users, setUsers] = useState([])
  const [inputText, setInputText] = useState('')
  const textInput = React.useRef(null);
  const [toggle, setToggle] = useState(false)
  const [messages, setMessages] = useState([])

  var socket = io('ws://localhost:10001')
  socket.on('chat message client', (msg) => {
    setToggle(!toggle)
  })

  const handleSend = () => {
    if (inputText) {
      socket.emit('chat message server', { email: localStorage.getItem('userEmail'), text: inputText })
      textInput.current.value = ""
      setInputText("")
    }
  }

  const handleInputText = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputText(event.target.value)
  }
  const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  async function logout() {
    await axios.post("http://localhost:10000/users/logout/user/" + localStorage.getItem("userEmail"), {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    })
  }

  async function getChatMessages() {
    const res = await axios.get("http://localhost:10000/chat", {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    }).then(function (res) {
      setMessages(res.data)
    })
  }

  async function getUsers() {
    const res = await axios.get("http://localhost:10000/users", {
      headers: {
        authorization: 'Bearer ' + localStorage.getItem('token') as string
      }
    }).then(function (res) {
      setUsers(res.data)
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

  const handleShopingBag = () => {
    window.location.replace('http://localhost:3000/basket')
  }

  const handleFavorites = () => {
    window.location.replace('http://localhost:3000/favorites')
  }

  useEffect(() => {
    getUsers()
    getChatMessages()
  }, [toggle])

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
      <Box sx={{ position: 'absolute', width: '300px', height: '80%', marginTop: '70px', backgroundColor: '#1976d2', borderRadius: '8px' }}>
        <Typography sx={{ marginLeft: '120px', color: 'white' }}>Users</Typography>
        <List>
          {users.map((row: any) =>
            <ListItem>
              <ListItemAvatar>
                <Avatar sx={{ backgroundColor: 'white' }}>
                  <AccountCircle color='primary' />
                </Avatar>
              </ListItemAvatar>
              <ListItemText sx={{ color: 'white' }}>
                {row.email}
              </ListItemText>
            </ListItem>
          )}
        </List>
      </Box>
      <Box sx={{ position: 'absolute', width: '80%', height: '80%', marginLeft: '350px', marginTop: '70px', backgroundColor: '#1976d2', borderRadius: '8px' }}>
        <Box style={{ overflow: 'auto' }} sx={{ position: 'absolute', width: '93%', marginTop: '50px', marginLeft: '50px', height: '80%', backgroundColor: 'white', borderRadius: '8px' }} >
          {messages.map((message: any) => message.username !== localStorage.getItem('userEmail') ?
            <ChatHeadRight text={message.message} email={message.username} /> : <ChatHeadLeft text={message.message} />)}
        </Box>
        <TextField sx={{ position: 'absolute', marginTop: '750px', marginLeft: '50px', width: '1300px' }} color='primary' size='small' variant='filled'
          onChange={handleInputText}
          inputRef={textInput}
        ></TextField>
        <IconButton onClick={handleSend}>
          <SendIcon sx={{ position: 'absolute', marginTop: '1525px', marginLeft: '2750px', fontSize: '30px' }} />
        </IconButton>
      </Box>
    </Box>
  )
}