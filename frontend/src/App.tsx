import React from 'react';
import './App.css';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import RestaurantList from './components/RestaurantList'; // We will create this next
import RestaurantDetail from './components/RestaurantDetail'; // We will create this next

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <nav>
          <ul>
            <li><Link to="/">Home</Link></li>
          </ul>
        </nav>
        <Routes>
          <Route path="/" element={<RestaurantList />} />
          <Route path="/restaurants/:id" element={<RestaurantDetail />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;