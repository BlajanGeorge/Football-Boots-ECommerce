import { Box, Button, Card, CardMedia, Paper } from '@mui/material'
import React from 'react'
import classes from './card_for_show.module.css'

interface Boots{
    id:number,
    name:string,
    price:number,
    brand:string,
    size:number,
    photoPath:string
  }
  
export const CreateCardsWithBoots =(props:Boots) =>{

    const handleClick = () => {
        localStorage.setItem('bootsId', props.id as unknown as string)
        window.location.replace('http://localhost:3000/boots/id')
    }
   
    return  (
       <Card key={props.id} sx={{border:2, borderColor: 'white'}} className={classes.show_pair_of_boots_card}>
           <Box className={classes.box_for_boots_image}>
           <CardMedia
        component="img"
        height="180"
        image={props.photoPath}
      />
           </Box>
        <Box sx={{borderTop:2, borderColor: 'black'}} className={classes.box_for_boots_name}>{props.name}
        </Box>
        <Box  className={classes.box_for_boots_size}>{props.size}</Box>
        <Box  className={classes.box_for_boots_brand}>{props.brand}</Box>
        <Box  className={classes.box_for_boots_price}>{props.price}</Box>
        <Button className={classes.button_show_boots_card} onClick={handleClick}>SHOW</Button>
        </Card>
    )
    
  }