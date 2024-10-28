import React, { useEffect, useState } from 'react'
import ApiService from '../../service/ApiService'
import '../../style/addProduct.css'
import { useNavigate } from 'react-router-dom';


const AddProductPage = () => {

  const [image, setImage]=useState(null);
  const [categories, setCategories] = useState([]);
  const [categoryId, setCategoryId] = useState('');
  const [name, setName]=useState('');
  const [description, setDescription]= useState('');
  const [price, setPrice] = useState('');
  const [message, setMessage] = useState('');

  const navigate =useNavigate();

  useEffect(()=>{
    ApiService.getAllCategory().then((res)=> setCategories(res.categoryList))
  }, [])

  const handleImage = (e)=>{
    e.preventDefault()
    setImage(e.target.files[0])
  }


  const handleSubmit = async(e)=>{
    e.preventDefault();
    try {
      const formData = new FormData();
      formData.append('image', image);
      formData.append('categoryId', categoryId);
      formData.append('name', name);
      formData.append('description', description);
      formData.append('price', price);

      const response = await ApiService.addProduct(formData);
      if(response.status === 200){ 
        setMessage(response.message);
        setTimeout(()=>{
          setMessage('')
          navigate('/admin/products')
        }, 3000)
      }
    } catch (error) {
      setMessage(error.response?.data?.message || error.message || 'unable to save a product.');

    }
  }


  return (
    <div className='add-product'>
      <form className='product-form' onSubmit={handleSubmit}>
        <h2>Add Product</h2>
        {message && <div className='message'>{message}</div>}
        <input type="file" onChange={handleImage} />
        <select value={categoryId} onChange={(e)=> setCategoryId(e.target.value)}>
          <option value="">Select Category</option>
          {categories.map((cat)=>(
            <option value={cat.id} key={cat.id}>{cat.name}</option>
          ))}
        </select>
        <input type="text"
          placeholder='product name'
          value={name}
          onChange={(e)=> setName(e.target.value)} 
        />
        <textarea 
        placeholder='description'
        value={description}
        onChange={(e)=> setDescription(e.target.value)} />

        <input type="number"
        placeholder='price'
        onChange={(e)=>setPrice(e.target.value)} />

        <button type='submit'>Add Product</button>
      </form>
      
    </div>
  )
}

export default AddProductPage
