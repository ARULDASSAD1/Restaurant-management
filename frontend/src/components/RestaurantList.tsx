import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Restaurant } from '../api';
import { getAllRestaurants, deleteRestaurant } from '../api';

import { 
  Container,
  Typography,
  Card,
  CardContent,
  Button,
  CircularProgress,
  IconButton,
  Box,
} from '@mui/material';

const RestaurantList: React.FC = () => {
  const [restaurants, setRestaurants] = useState<Restaurant[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  // Fetch restaurants on component mount
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

  // Handle restaurant deletion
  const handleDelete = async (id: string) => {
    if (window.confirm('Are you sure you want to delete this restaurant?')) {
      try {
        // Call the delete API function
        await deleteRestaurant(id); 
        
        // For now, let's simulate deletion by filtering the state
        setRestaurants(restaurants.filter(restaurant => restaurant.id !== id));
        console.log(`Restaurant with ID ${id} deleted.`);
      } catch (err) {
        setError('Failed to delete restaurant.');
        console.error('Error deleting restaurant:', err);
      }
    }
  };

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
        <CircularProgress />
        <Typography variant="h6" sx={{ ml: 2 }}>Loading restaurants...</Typography>
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

  return (
    <Container sx={{ mt: 4 }}>
      <Typography variant="h4" component="h2" gutterBottom>
        Restaurants
      </Typography>
      {restaurants.length === 0 ? (
        <Typography variant="body1">No restaurants found.</Typography>
      ) : (
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '24px', justifyContent: 'center' }}>
          {restaurants.map((restaurant) => (
            <div key={restaurant.id} style={{ flex: '1 1 calc(33.33% - 24px)', minWidth: '280px', maxWidth: '350px' }}>
              <Card raised sx={{
                backgroundColor: '#f5f5f5', // Light grey background
                borderRadius: '12px', // Rounded corners
                boxShadow: '0 4px 8px rgba(0,0,0,0.1)', // Subtle shadow
                transition: 'transform 0.3s ease-in-out', // Smooth transition for hover
                '&:hover': {
                  transform: 'translateY(-5px)', // Lift effect on hover
                },
                height: '100%', // Ensure cards in a row have same height
                display: 'flex', // Use flex for content alignment within card
                flexDirection: 'column', // Stack content vertically
              }}>
                <CardContent sx={{ flexGrow: 1 }}>
                  <Typography variant="h6" component="div" sx={{ color: '#3f51b5', marginBottom: '8px' }}>
                    {restaurant.name}
                     <IconButton 
                       aria-label="delete" 
                       onClick={() => handleDelete(restaurant.id!)} // Use non-null assertion if you're sure id exists
                       sx={{ color: '#f44336' }} // Red color for delete icon
                     > {/* Add delete icon here, e.g., from @mui/icons-material */} </IconButton>
                  </Typography>
                  <Typography variant="body2" color="text.secondary" sx={{ color: '#616161', marginBottom: '16px' }}>
                    {restaurant.address}
                  </Typography>
                  <Button
                    component={Link}
                    to={`/restaurants/${restaurant.id}`}
                    variant="contained"
                    sx={{
                      mt: 'auto', // Push button to the bottom
                      backgroundColor: '#4caf50', // Green button
                      '&:hover': {
                        backgroundColor: '#388e3c', // Darker green on hover
                      },
                    }}
                  >
                    View Details
                  </Button>
                </CardContent>
              </Card>
            </div>
          ))}
        </div>
      )}
    </Container>
  );
};

export default RestaurantList;