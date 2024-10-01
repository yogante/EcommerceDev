import React from 'react'
import '../../style/Footer.css'
import { NavLink } from 'react-router-dom'

const footer = () => {
  return (
    <footer>
      <div className='footer-links'>
        <ul>
          <NavLink to={"/"}>About us</NavLink>
          <NavLink to={"/"}>Contact us</NavLink>
          <NavLink to={"/"}>Terms & Conditions</NavLink>
          <NavLink to={"/"}>Privacy Policy</NavLink>
          <NavLink to={"/"}>FAQs</NavLink>
        </ul>
      </div> 
      <div className='footer-info'>
        <p>&copy; 2024 yogante mart. all right reserved.</p>
      </div> 
    </footer>
    )
}

export default footer
