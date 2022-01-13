import { CardMedia, Radio, RadioGroup, Typography } from '@mui/material'
import React, { useEffect } from 'react'
import { Button, Box, FormControlLabel, Switch, AppBar, Toolbar, IconButton, Menu, MenuItem, Checkbox, TextField, Slider, Select, FormControl, InputLabel, Autocomplete, Card, TabScrollButton, createMuiTheme } from '@mui/material'
import { sizeHeight } from '@mui/system'
import { useState } from 'react'
import Image from '../../../photo/bank-card.jpeg'

export const Order = () => {
    const [prefix, setPrefix] = useState('')
    const options = ['+40', '+12', '+57']
    const [hidden, setHidden] = useState(true)

    const showCard = () => {
        setHidden(false)
    }

    const hideCard = () => {
        setHidden(true)
    }

    const goToLoading = () => {
      window.location.replace('http://localhost:3000/loading')
    }

    return (
        <Box>
            <Typography sx={{position:'absolute',fontSize:'30px', marginLeft:'500px', marginTop:'100px'}}>City:</Typography>
            <TextField sx={{position:'absolute',width:'150px',marginTop:'105px', marginLeft:'580px'}} size='small'></TextField>
            <Typography sx={{position:'absolute',fontSize:'30px', marginLeft:'750px', marginTop:'100px'}}>County:</Typography>
            <TextField sx={{position:'absolute',width:'150px',marginTop:'105px', marginLeft:'870px'}} size='small'></TextField>
            <Typography sx={{position:'absolute',fontSize:'30px', marginLeft:'500px', marginTop:'200px'}}>Street:</Typography>
            <TextField sx={{position:'absolute',width:'300px',marginTop:'205px', marginLeft:'600px'}} size='small'></TextField>
            <Typography sx={{position:'absolute',fontSize:'30px', marginLeft:'920px', marginTop:'200px'}}>Nr:</Typography>
            <TextField sx={{position:'absolute',width:'50px',marginTop:'205px', marginLeft:'970px'}} size='small'></TextField>
            <Typography sx={{position:'absolute',fontSize:'30px', marginLeft:'500px', marginTop:'300px'}}>Phone:</Typography>
            <Autocomplete
                   sx={{position:'absolute', marginTop:'305px', marginLeft:'610px'}}
                   isOptionEqualToValue={(option, value) => option === value}
                   onChange={(event, value) => {setPrefix(value as string)}}
                   options={options}
                   renderInput={(params) => <TextField {...params} size={"small"} />}/>
            <TextField sx={{position:'absolute',width:'300px',marginTop:'305px', marginLeft:'750px'}} size='small'></TextField>
            <Typography sx={{position:'absolute',fontSize:'30px', marginLeft:'500px', marginTop:'400px'}}>Payment:</Typography>
            <RadioGroup  sx={{position:'absolute', marginTop:'405px', marginLeft:'650px'}} row  name="row-radio-buttons-group">
        <FormControlLabel value="cash" control={<Radio />} label="cash" onClick={hideCard}/>
        <FormControlLabel value="card" control={<Radio />} label="card" onClick={showCard} />
      </RadioGroup>
      <Typography sx={{position:'absolute',fontSize:'30px', marginLeft:'500px', marginTop:'500px'}}>Delivery:</Typography>
      <RadioGroup sx={{position:'absolute', marginTop:'505px', marginLeft:'650px'}} row  name="row-radio-buttons-group">
        <FormControlLabel value="cargus" control={<Radio />} label="cargus" />
        <FormControlLabel value="post" control={<Radio />} label="post" />
      </RadioGroup>
      
      { !hidden && (<Box
       sx={{width:'500px', height:'300px', position:'absolute', marginLeft:'900px', marginTop:'400px'}}>
           <TextField size='small' sx={{width:'450px', position:'absolute', marginTop:'155px', marginLeft:'20px' , background:'white'}}></TextField>
           <TextField size='small' sx={{width:'40px', position:'absolute', marginTop:'203px', marginLeft:'205px', background:'white'}}></TextField>
           <TextField size='small' sx={{width:'40px', position:'absolute', marginTop:'203px', marginLeft:'260px', background:'white'}}></TextField>
           <TextField size='small' sx={{width:'100px', position:'absolute', marginTop:'80px', marginLeft:'300px' , background:'white'}}></TextField>
      <CardMedia
        component="img"
        height="300"
        image={Image}
      />
      </Box>) }
      <Button sx={{position:'absolute', marginTop:'600px', marginLeft:'550px', backgroundColor:'black', color:'white', width:'200px'}} onClick={goToLoading}>Order</Button>
        </Box>
    )
}