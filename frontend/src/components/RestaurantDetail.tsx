import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getRestaurantById, getMenuItemsByRestaurantId, Restaurant, MenuItem } from '../api';

const RestaurantDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [restaurant, setRestaurant] = useState<Restaurant | null>(null);
  const [menuItems, setMenuItems] = useState<MenuItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (id) {
          setLoading(true);
          const restaurantData = await getRestaurantById(id);
          setRestaurant(restaurantData);

          const menuItemsData = await getMenuItemsByRestaurantId(id);
          setMenuItems(menuItemsData);
        }
      } catch (err) {
        setError('Failed to fetch data.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  if (!restaurant) {
    return <div>Restaurant not found.</div>;
  }

  return (
    <div>
      <h2>{restaurant.name}</h2>
      <p>Address: {restaurant.address}</p>

      <h3>Menu</h3>
      {menuItems.length > 0 ? (
        <ul>
          {menuItems.map((item) => (
            <li key={item.id}>
              {item.name} - ${item.price} ({item.category})
              <p>{item.description}</p>
            </li>
          ))}
        </ul>
      ) : (
        <p>No menu items available.</p>
      )}
    </div>
  );
};

export default RestaurantDetail;