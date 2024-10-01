import './App.css';
import { BrowserRouter,Routes,Route,Navigate } from 'react-router-dom';
import { ProtectedRoute, AdminRoute } from './service/Guard';
import Navbar from './component/common/Navbar';
import Footer from './component/common/Footer.jsx';
import { CartProvider } from './component/context/CartContext.js';


function App() {
  return (
    <BrowserRouter>
    <CartProvider>
    <Navbar />
      <Routes>

      </Routes>
    <Footer />
    </CartProvider>
    
    
    </BrowserRouter>
    );
}

export default App;
