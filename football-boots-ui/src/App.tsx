
import Login from './components/login/login'
import SignUp from './components/login/signup'
import GetAllDefaultBoots from './components/showBoots/getAllDefaultBoots'
import MenuAppBar from './components/showBoots/getAllDefaultBoots'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import UserProfile from './components/userProfile/userProfile'
import { ShowBootsById } from './components/showBoots/showBootsById'
import { Basket } from './components/basket/basket'
import { Order } from './components/order/order'
import { Loading } from './components/loading/loading'
import {Done} from './components/done/done'
import { AdminConsole } from './components/adminConsole/adminConsole'
import { AdminConsoleUsers } from './components/adminConsole/adminConsoleUsersCruds'
import { AdminConsoleBoots } from './components/adminConsole/adminConsoleBootsCruds'
import { AdminConsoleBasket } from './components/adminConsole/adminConsoleBasketCruds'
import { Favorites } from './components/favorites/favorites'

function App() {
  return (
   <BrowserRouter>
   <Routes>
   <Route path="/login" element={<Login/>} />
   <Route path="/registration" element={<SignUp/>}/>
   <Route path="/boots" element={<MenuAppBar/>}/>
   <Route path="/profile" element={<UserProfile/>}/>
   <Route path="/boots/id" element={<ShowBootsById/>}/>
   <Route path="/basket" element={<Basket/>}/>
   <Route path="/order" element={<Order/>}/>
   <Route path="/loading" element={<Loading/>}/>
   <Route path="/done" element={<Done/>}/>
   <Route path="/adminConsole" element={<AdminConsole/>}/>
   <Route path="/adminConsole/boots" element={<AdminConsoleBoots/>}/>
   <Route path="/adminConsole/users" element={<AdminConsoleUsers/>}/>
   <Route path="/adminConsole/basket" element={<AdminConsoleBasket/>}/>
   <Route path="/favorites" element={<Favorites/>}/>
   </Routes> 
   </BrowserRouter>
  )
}
export default App
