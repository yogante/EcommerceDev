import React, { useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import ApiService from '../../service/ApiService';
import '../../style/register.css'

const LoginPage = () => {

  const [formData, setFormData] =  useState({
    email:'',
    password:''
  });
  const [message, setMessage] = useState(null);
  const navigate = useNavigate();

  const handleChange = (e) =>{
    const {name, value} = e.target;
    setFormData({...formData, [name]: value});
  }
  const handleSubmit = async (e)=>{
    e.preventDefault();
    console.log(formData)
    try {
      const response = await ApiService.loginUser(formData);
      if (response.status === 200) {
          setMessage("User successfully logged in.");
          localStorage.setItem('token', response.token);
          localStorage.setItem('role', response.role);
          
          setTimeout(()=>{
            navigate("/profile")
          }, 4000)
        }
    } catch (error) {
      setMessage(error.response?.data.message || error.message || "unable to login a user.");
    }
  }

  return (
    <div className='register-page'>
      <h2>Register</h2>
      {message && <p className='message'>{message}</p>}
      <form onSubmit={handleSubmit}>
        <label>Email: </label>
          <input
          type='email'
          name='email'
          value={formData.email}
          onChange={handleChange}
          required/>
        
          <label>Password: </label>
          <input
          type='password'
          name='password'
          value={formData.password}
          onChange={handleChange}
          required/>

          <button type='submit'>Register</button>
          <p className='register-link'>
            Don't have an account? <a href="/login">login</a>
          </p>
      </form>
    </div>
  )
}

export default LoginPage
