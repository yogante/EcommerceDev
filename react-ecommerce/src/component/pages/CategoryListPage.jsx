import React, {useState, useEffect} from 'react'
import { useNavigate } from 'react-router-dom'
import ApiService from '../../service/ApiService'
import '../../style/categoryListPage.css'
const CategoryListPage = () => {

  const [categories, setCategories] = useState([]);
  const [error, setError] = useState();
  const navigate = useNavigate();

  useEffect(()=>{
   
    fetchCategories()
  }, []);

  const fetchCategories = async()=>{
    try {
      const response = await ApiService.getAllCategory();
      setCategories(response.categoryList || []);
    } catch (error) {
      setError(error.response?.data?.message || error.message || 'unable to fetch categories.');
      
    }
  }

  const handleCategoryClick = (categoryId)=>{
    navigate(`/category/${categoryId}`);
  }

  return (
    <div className='category-list'>
      {error ?  (
        <p className='error-message'>{error}</p>
      ):(
        <div>
          <h2>categories</h2>
          <ul>
            {categories.map((category)=>(
              <li key={category.id}>
                <button onClick={()=>handleCategoryClick(category.id)}>{category.name}</button>
              </li>
            ))}
          </ul>
        </div>
      )}
      
    </div>
  )
}





export default CategoryListPage
