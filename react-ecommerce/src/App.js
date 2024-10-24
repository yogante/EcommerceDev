import './App.css';
import { BrowserRouter,Routes,Route } from 'react-router-dom';
import Navbar from './component/common/Navbar';
import Footer from './component/common/Footer.jsx';
import { CartProvider } from './component/context/CartContext.js';
import Home from './component/pages/Home.jsx';
import ProductDetailsPage from './component/pages/ProductDetailsPage.jsx';
import CategoryListPage from './component/pages/CategoryListPage.jsx';
import CategoryProductsPage from './component/pages/CategoryProductsPage.jsx';
import CartPage from './component/pages/CartPage.jsx';
import RegisterPage from './component/pages/RegisterPage.jsx';
import LoginPage from './component/pages/LoginPage.jsx';
import ProfilePage from './component/pages/ProfilePage.jsx';
import AddressPage from './component/pages/AddressPage.jsx';
import { AdminRoute, ProtectedRoute } from './service/Guard.js';
import AdminPage from './component/admin/AdminPage.jsx';
import AdminCategoryPage from './component/admin/AdminCategoryPage.jsx';
import AddCategory from './component/admin/AddCategory.jsx';
import EditCategory from './component/admin/EditCategory.jsx';

function App() {
  return (
    <BrowserRouter>
    <CartProvider>
    <Navbar />
      <Routes>
        {/**OUR ROUTES */}
        
        <Route exact path='/' element={<Home/>}/>
        <Route path='/product/:productId' element={<ProductDetailsPage />} />
        <Route path='/categories' element={<CategoryListPage/>} />
        <Route path='/category/:categoryId' element={<CategoryProductsPage />} />
        <Route path='/cart' element={<CartPage/>} />
        <Route path='/register' element={<RegisterPage/>} />
        <Route path='/login' element={<LoginPage/>} />
        
        <Route path='/profile' element={<ProtectedRoute element={<ProfilePage/>} />}/>
        <Route path='/add-address' element={<ProtectedRoute element={<AddressPage/>}  />} />
        <Route path='/edit-address' element={<ProtectedRoute element={<AddressPage/>} />} />
        
        <Route path='/admin' element={<AdminRoute element={<AdminPage/>} />}/>
        <Route path='/admin/categories' element={<AdminRoute element={<AdminCategoryPage/>} />}/>
       
        <Route path='/admin/add-category' element={<AdminRoute element={<AddCategory/>} />}/>
        <Route path='/admin/edit-category/:categoryId' element={<AdminRoute element={<EditCategory/>} />}/>
        

      </Routes>
    <Footer />
    </CartProvider>
    
    
    </BrowserRouter>
    );
}

export default App;
