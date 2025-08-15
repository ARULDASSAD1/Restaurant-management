import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getRestaurantById, getMenuItemsByRestaurantId, Restaurant, MenuItem } from '../api';

import {
  Container,
  Typography,
  CircularProgress,
  Box,
  Paper,
  List,
  ListItem,
  ListItemText,
  Divider,
} from '@mui/material';

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
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
        <CircularProgress />
        <Typography variant="h6" sx={{ ml: 2 }}>Loading details...</Typography>
      </Box>
    );
  }

  if (error) {
    return (
      <Container sx={{ mt: 4 }}>
        <Typography color="error">Error: {error}</Typography>
      </Container>
    );
  }

  if (!restaurant) {
    return (
      <Container sx={{ mt: 4 }}>
        <Typography variant="h6">Restaurant not found.</Typography>
      </Container>
    );
  }

  return (
    <Container sx={{ mt: 4 }}>
      <Paper elevation={3} sx={{ p: 3, mb: 4 }}>
        <Typography variant="h4" component="h2" gutterBottom>
          {restaurant.name}
        </Typography>
        <Typography variant="body1" color="text.secondary" sx={{ mb: 2 }}>
          Address: {restaurant.address}
        </Typography>
        {restaurant.id && (
 <Link to={`/restaurants/${restaurant.id}/edit`}>Edit Restaurant</Link>
        )}

      </Paper>

      <Paper elevation={3} sx={{ p: 3 }}>
        <Typography variant="h5" component="h3" gutterBottom>
          Menu
        </Typography>
        {menuItems.length > 0 ? (
          <List>
            {menuItems.map((item) => (
              <React.Fragment key={item.id}>
                <ListItem alignItems="flex-start">
                  <ListItemText
                    primary={
                      <Typography variant="h6">
                        {item.name} - ${item.price}
                      </Typography>
                    }
                    secondary={
                      <>
                        <Typography
                          sx={{ display: 'inline' }}
                          component="span"
                          variant="body2"
                          color="text.primary"
                        >
                          {item.category}
                        </Typography>
                        {" — " + item.description}
                      </>
                    }
                  />
                </ListItem>
                <Divider component="li" />
              </React.Fragment>
            ))}
          </List>
        ) : (
          <Typography variant="body1">No menu items available.</Typography>
        )}
      </Paper>
    </Container>
  );
};

export default RestaurantDetail;