import React from 'react'
import axios from 'axios'
import { useState, useEffect } from 'react'
import { Button, Box, FormControlLabel, Switch, AppBar, Toolbar, IconButton, Typography, Menu, MenuItem, Checkbox, TextField, Slider, Select, FormControl, InputLabel, Autocomplete, Card, TabScrollButton, createMuiTheme } from '@mui/material'
import { FormGroup } from '@material-ui/core'
import { AccountCircle, MaximizeRounded } from '@mui/icons-material'
import ShoppingBagIcon from '@mui/icons-material/ShoppingBag';
import FavoriteIcon from '@mui/icons-material/Favorite';
import HomeIcon from '@mui/icons-material/Home';
import classes from './defaultboots.module.css'
import { CreateCardsWithBoots } from './cardForShowBoots'

    const options = ['The highest price', 'The lowest price'];


    export default function MenuAppBar() {
        const [auth, setAuth] = React.useState(true);
        const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);

        const [nikeCheck, setNikeCheck] = useState(true)
        const [pumaCheck, setPumaCheck] = useState(true)
        const [adidasCheck, setAdidasCheck] = useState(true)
        const [brandArray, setBrandArray] = useState(['NIKE', 'PUMA', 'ADIDAS'])
  
        const [size35Color , setSize35Color] = useState('white')
        const [size36Color , setSize36Color] = useState('white')
        const [size37Color , setSize37Color] = useState('white')
        const [size38Color , setSize38Color] = useState('white')
        const [size39Color , setSize39Color] = useState('white')
        const [size40Color , setSize40Color] = useState('white')
        const [size41Color , setSize41Color] = useState('white')
        const [size42Color , setSize42Color] = useState('white')
        const [size43Color , setSize43Color] = useState('white')
        const [size44Color , setSize44Color] = useState('white')
        const [size45Color , setSize45Color] = useState('white')

        const [size35Checked, setSize35Checked] = useState(true)
        const [size36Checked, setSize36Checked] = useState(true)
        const [size37Checked, setSize37Checked] = useState(true)
        const [size38Checked, setSize38Checked] = useState(true)
        const [size39Checked, setSize39Checked] = useState(true)
        const [size40Checked, setSize40Checked] = useState(true)
        const [size41Checked, setSize41Checked] = useState(true)
        const [size42Checked, setSize42Checked] = useState(true)
        const [size43Checked, setSize43Checked] = useState(true)
        const [size44Checked, setSize44Checked] = useState(true)
        const [size45Checked, setSize45Checked] = useState(true)
        const [sizeArray, setSizeArray] = useState(['35', '36', '37', '38', '39', '40', '41', '42', '43', '44', '45'])

        const [priceFilter, setPriceFilter] = useState([0, 1000]);
        const [lowPriceField, setLowPriceField] = useState(0)
        const [highPriceField, setHighPriceField] = useState(1000)
        const [bootsData, setBootsData] = useState([])
        const [filterDisplayToggle, setFilterDisplayToggle] = useState(false)
        const [firstDisplay, setFirstDisplay] = useState(true)
        const [filterValue, setFilterValue] = useState('')
      
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

        const handleNikeCheck = () => {
          if(nikeCheck == true)
          {
            setNikeCheck(false);
            brandArray.splice(brandArray.indexOf('NIKE'),1)
          }
          else
          {
            setNikeCheck(true);
            brandArray.push('NIKE');
          }
        }

        const handlePumaCheck = () => {
          if(pumaCheck == true)
          {
            setPumaCheck(false);
            brandArray.splice(brandArray.indexOf('PUMA'),1)
          }
          else
          {
            setPumaCheck(true);
            brandArray.push('PUMA');
          }
        }

        const handleAdidasCheck = () => {
          if(adidasCheck == true)
          {
            setAdidasCheck(false);
            brandArray.splice(brandArray.indexOf('ADIDAS'),1)
          }
          else
          {
            setAdidasCheck(true);
            brandArray.push('ADIDAS');
          }
        }

        const size35Check = () => {
          if(size35Checked == true)
          {
            setSize35Checked(false);
            setSize35Color('#1976d2');
            sizeArray.splice(sizeArray.indexOf('35'),1)
          }
          else
          {
            setSize35Checked(true);
            setSize35Color('white');
            sizeArray.push('35');
          }
        }

        const size36Check = () => {
          if(size36Checked == true)
          {
            setSize36Checked(false);
            setSize36Color('#1976d2');
            sizeArray.splice(sizeArray.indexOf('36'),1)
          }
          else
          {
            setSize36Checked(true);
            setSize36Color('white');
            sizeArray.push('36');
          }
        }

        const size37Check = () => {
          if(size37Checked == true)
          {
            setSize37Checked(false);
            setSize37Color('#1976d2');
            sizeArray.splice(sizeArray.indexOf('37'),1)
          }
          else
          {
            setSize37Checked(true);
            setSize37Color('white');
            sizeArray.push('37');
          }
        }

        const size38Check = () => {
          if(size38Checked == true)
          {
            setSize38Checked(false);
            setSize38Color('#1976d2');
            sizeArray.splice(sizeArray.indexOf('38'),1)
          }
          else
          {
            setSize38Checked(true);
            setSize38Color('white');
            sizeArray.push('38');
          }
        }

        const size39Check = () => {
          if(size39Checked == true)
          {
            setSize39Checked(false);
            setSize39Color('#1976d2');
            sizeArray.splice(sizeArray.indexOf('39'),1)
          }
          else
          {
            setSize39Checked(true);
            setSize39Color('white');
            sizeArray.push('39');
          }
        }

        const size40Check = () => {
          if(size40Checked == true)
          {
            setSize40Checked(false);
            setSize40Color('#1976d2');
            sizeArray.splice(sizeArray.indexOf('40'),1)
          }
          else
          {
            setSize40Checked(true);
            setSize40Color('white');
            sizeArray.push('40');
          }
        }

        const size41Check = () => {
          if(size41Checked == true)
          {
            setSize41Checked(false);
            setSize41Color('#1976d2');
            sizeArray.splice(sizeArray.indexOf('41'),1)
          }
          else
          {
            setSize41Checked(true);
            setSize41Color('white');
            sizeArray.push('41');
          }
        }

        const size42Check = () => {
          if(size42Checked == true)
          {
            setSize42Checked(false);
            setSize42Color('#1976d2');
            sizeArray.splice(sizeArray.indexOf('42'),1)
          }
          else
          {
            setSize42Checked(true);
            setSize42Color('white');
            sizeArray.push('42');
          }
        }

        const size43Check = () => {
          if(size43Checked == true)
          {
            setSize43Checked(false);
            setSize43Color('#1976d2');
            sizeArray.splice(sizeArray.indexOf('43'),1)
          }
          else
          {
            setSize43Checked(true);
            setSize43Color('white');
            sizeArray.push('43');
          }
        }

        const size44Check = () => {
          if(size44Checked == true)
          {
            setSize44Checked(false);
            setSize44Color('#1976d2');
            sizeArray.splice(sizeArray.indexOf('44'),1)
          }
          else
          {
            setSize44Checked(true);
            setSize44Color('white');
            sizeArray.push('44');
          }
        }

        const size45Check = () => {
          if(size45Checked == true)
          {
            setSize45Checked(false);
            setSize45Color('#1976d2');
            sizeArray.splice(sizeArray.indexOf('45'),1)
          }
          else
          {
            setSize45Checked(true);
            setSize45Color('white');
            sizeArray.push('45');
          }
        }

        const handleChangeOnPriceFilter = (event: Event, newValue: number | number[]) => {
          setPriceFilter(newValue as number[]);
          setLowPriceField((newValue as Array<number>)[0])
          setHighPriceField((newValue as Array<number>)[1])
        };

        async function getAllDefaultBoots() {
          const res = await axios.get('http://localhost:10000/boots', {
               headers: {
                  authorization:'Bearer ' + localStorage.getItem('token') as string 
               }
           }
       ).then(function(res){
         setBootsData(res.data);
       })

      }

      async function handleFilterRequest(){
        var defaulrUrl = 'http://localhost:10000/boots/filter'
        var brand = '?brand='
        var size = 'size='
        var minPrice = 'minPrice='
        var maxPrice = 'maxPrice='
        var sort = "sorting="
    
        brand = brand + brandArray.join();
        size = size + sizeArray.join();
        minPrice = minPrice + lowPriceField
        maxPrice = maxPrice + highPriceField

          if(filterValue == 'The highest price')
          {
            sort = sort + '0';
          }
          if(filterValue == 'The lowest price')
          {
            sort = sort + '1'
          }
        

        defaulrUrl = defaulrUrl + brand + '&' + size + '&' + minPrice + '&' + maxPrice + '&' + sort

        const res = await axios.get(defaulrUrl, {
          headers: {
            authorization:'Bearer ' + localStorage.getItem('token') as string 
         }
        }).then(function(res){
          setBootsData(res.data)
        }
        )
      }

      const handleFilterRequestClick = () => {
        setFilterDisplayToggle(!filterDisplayToggle)
      }

      const handleShopingBag = () => {
        window.location.replace('http://localhost:3000/basket')
      }

      useEffect(() => {
        if(firstDisplay == true){
         getAllDefaultBoots()
         setFirstDisplay(false)
        }
        else
        {
          handleFilterRequest()
        }
    }, [filterDisplayToggle]);

    const handleFavorites = () => {
      window.location.replace('http://localhost:3000/favorites')
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
                <IconButton color="inherit" onClick={handleFavorites}>
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
      <Box className={classes.brand_box}>
        <Typography className={classes.nike_text}>NIKE</Typography>
      <Checkbox className={classes.nike_checkbox} checked={nikeCheck} onChange={handleNikeCheck} />
        <Typography className={classes.puma_text}>PUMA</Typography>
        <Checkbox className={classes.puma_checkbox} checked={pumaCheck} onChange={handlePumaCheck} />
        <Typography className={classes.adidas_text}>ADIDAS</Typography>
        <Checkbox className={classes.adidas_checkbox} checked={adidasCheck} onChange={handleAdidasCheck} />
      </Box>
      <Box className={classes.size_box}>
        <Box onClick={size35Check} className={classes.size_35_box} sx={{bgcolor: size35Color, border:1, borderColor: 'white', cursor: 'pointer'}}>35</Box>
        <Box onClick={size36Check} className={classes.size_36_box} sx={{bgcolor: size36Color, border:1, borderColor: 'white', cursor: 'pointer'}}>36</Box>
        <Box onClick={size37Check} className={classes.size_37_box} sx={{bgcolor: size37Color, border:1, borderColor: 'white', cursor: 'pointer'}}>37</Box>
        <Box onClick={size38Check} className={classes.size_38_box} sx={{bgcolor: size38Color, border:1, borderColor: 'white', cursor: 'pointer'}}>38</Box>
        <Box onClick={size39Check} className={classes.size_39_box} sx={{bgcolor: size39Color, border:1, borderColor: 'white', cursor: 'pointer'}}>39</Box>
        <Box onClick={size40Check} className={classes.size_40_box} sx={{bgcolor: size40Color, border:1, borderColor: 'white', cursor: 'pointer'}}>40</Box>
        <Box onClick={size41Check} className={classes.size_41_box} sx={{bgcolor: size41Color, border:1, borderColor: 'white', cursor: 'pointer'}}>41</Box>
        <Box onClick={size42Check} className={classes.size_42_box} sx={{bgcolor: size42Color, border:1, borderColor: 'white', cursor: 'pointer'}}>42</Box>
        <Box onClick={size43Check} className={classes.size_43_box} sx={{bgcolor: size43Color, border:1, borderColor: 'white', cursor: 'pointer'}}>43</Box>
        <Box onClick={size44Check} className={classes.size_44_box} sx={{bgcolor: size44Color, border:1, borderColor: 'white', cursor: 'pointer'}}>44</Box>
        <Box onClick={size45Check} className={classes.size_45_box} sx={{bgcolor: size45Color, border:1, borderColor: 'white', cursor: 'pointer'}}>45</Box>
      </Box>
      <Box className={classes.price_box}><TextField inputProps={
					{ readOnly: true, }
				} size="small" value={lowPriceField} className={classes.low_price_bound_field}/><Typography className={classes.price_text}>PRICE</Typography>
      <Slider
        className = {classes.price_slide}
        value={priceFilter}
        onChange={handleChangeOnPriceFilter}
        valueLabelDisplay="auto"
        color="secondary"
        size = "medium"
        max = {1000}
      />
      <TextField inputProps={
					{ readOnly: true, }
				} size="small" value={highPriceField} className={classes.high_price_bound_field}/>
      </Box>
      <Button className={classes.filter_button} onClick={handleFilterRequestClick}>Filter</Button>
      <Autocomplete
      isOptionEqualToValue={(option, value) => option === value}
      onChange={(event, value) => setFilterValue(value as string)}
      options={options}
      className={classes.select_filter}
      renderInput={(params) => <TextField {...params} label='Sort' size={"small"} />}/>
   
      <Box className={classes.box_for_showing_boots}>

       { bootsData.map((s: any) => (
       <CreateCardsWithBoots key={s.id} {...s} />
       )
       )
    }
      </Box>
    </Box>
        )
}