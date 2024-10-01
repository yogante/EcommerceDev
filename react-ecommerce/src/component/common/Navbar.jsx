import React, {useState} from 'react'
import '../../style/Navbar.css'
import { NavLink, useNavigate } from 'react-router-dom'
import ApiService from '../../service/ApiService'


const Navbar = () => {
  
  const [searchValue, setSearchValue] = useState("");
  const navigate =useNavigate();

  const isAdmin = ApiService.isAdmin();
  const isAuthenticated = ApiService.isAuthenticated();

  const handleSearchChange = (e)=>{
    setSearchValue(e.target.value);
  }

  const handleSearchSubmit = async (e)=>{
    e.preventDefault();
    navigate(`/?search=${searchValue}`)
  }

  const handleLogout= ()=>{
    const confirm = window.confirm("Are you sure logout?" );
    if (confirm){
      ApiService.logout();
      setTimeout(()=>{
        navigate('/login')
      }, 500);
    }
  }

  return (

    <div className='navbar'>
      <div className='navbar-brand'>
        <NavLink to='/'> <img src='/yogante.png' alt='yogante mart' /></NavLink>
      </div>
      {/** SEARCH FORM */}
      <form className='navbar-search' onSubmit={handleSearchSubmit}>
        <input type="text" 
        placeholder='Search products' 
        value={searchValue} 
        onChange={handleSearchChange} />
        <button type='submit'>Search</button>
      </form>

      <div className='navbar-link'>
        <NavLink to='/' activeClassName='active'>Home</NavLink>
        <NavLink to='/categories' activeClassName='active'>Categories</NavLink>
        {isAuthenticated &&  <NavLink to='/profile' activeClassName='active'>My Account</NavLink>}
        {isAdmin && <NavLink to='/admin' activeClassName='active'>Admin</NavLink>}
        {!isAuthenticated && <NavLink to='/login' activeClassName='active'>Login</NavLink>}
        {isAuthenticated && <NavLink onClick={handleLogout}>Logout</NavLink>}
        <NavLink to='/cart'>Cart</NavLink>
      </div>
    </div>
  )
}

export default Navbar