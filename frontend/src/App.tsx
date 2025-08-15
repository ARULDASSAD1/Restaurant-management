import React from 'react';
import './App.css';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import RestaurantList from './components/RestaurantList'; // We will create this next
import RestaurantDetail from './components/RestaurantDetail'; // We will create this next
import AddRestaurantForm from './components/AddRestaurantForm';
import UpdateRestaurantForm from './components/UpdateRestaurantForm';
 
function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <nav>
          <ul>
            <li><Link to="/">Home</Link></li>
            <li><Link to="/restaurants/add">Add Restaurant</Link></li>
          </ul>
        </nav>
        <Routes>
          <Route path="/" element={<RestaurantList />} />
          <Route path="/restaurants/add" element={<AddRestaurantForm />} />
          <Route path="/restaurants/:id" element={<RestaurantDetail />} />
          <Route path="/restaurants/:id/edit" element={<UpdateRestaurantForm />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;