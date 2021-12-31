import React from 'react'
import axios from 'axios'
import { useState, useEffect } from 'react'
import { Button, Box } from '@mui/material'

const GetAllDefaultBoots = () =>
{ 
    //const [boots, setBoots] = useState('')

    async function getAllDefaultBoots() {
       const res = await axios.get('https://thingproxy.freeboard.io/fetch/http://localhost:10000/boots', {
            headers: {
               authorization:'Bearer ' + localStorage.getItem('token') as string 
            }
        }
    )

    if(res.status == 200){
        //setBoots(res.data)
        console.log(res.data)
    }

    }

    return (
        <Box>
        <Button onClick={getAllDefaultBoots}>click</Button>
        </Box>
        )
}
export default GetAllDefaultBoots