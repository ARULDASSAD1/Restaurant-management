import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Restaurant } from '../api'; // Assuming Restaurant interface is in api.ts
import { getAllRestaurants } from '../api'; // Assuming getAllRestaurants function is in api.ts

const RestaurantList: React.FC = () => {
  const [restaurants, setRestaurants] = useState<Restaurant[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchRestaurants = async () => {
      try {
        setLoading(true);
        const data = await getAllRestaurants();
        setRestaurants(data);
      } catch (err) {
        setError('Failed to fetch restaurants.');
        console.error('Error fetching restaurants:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchRestaurants();
  }, []);

  if (loading) {
    return <div>Loading restaurants...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div>
      <h2>Restaurants</h2>
      {restaurants.length === 0 ? (
        <p>No restaurants found.</p>
      ) : (
        <ul>
          {restaurants.map((restaurant) => (
            <li key={restaurant.id}>
              <Link to={`/restaurants/${restaurant.id}`}>
                {restaurant.name} - {restaurant.address}
              </Link>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default RestaurantList;