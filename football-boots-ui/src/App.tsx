
import Login from './components/login/login'
import SignUp from './components/login/signup'
import GetAllDefaultBoots from './components/showBoots/getAllDefaultBoots'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'

function App() {
  return (
   <BrowserRouter>
   <Routes>
   <Route path="/login" element={<Login/>} />
   <Route path="/registration" element={<SignUp/>}/>
   <Route path="/boots" element={<GetAllDefaultBoots/>}/>
   </Routes> 
   </BrowserRouter>
  )
}
export default App
