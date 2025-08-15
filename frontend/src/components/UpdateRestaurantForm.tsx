import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Restaurant, getRestaurantById, updateRestaurant } from '../api';
import './FormStyles.css'; // Assuming you create a CSS file for form styles

const UpdateRestaurantForm: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();

    const [restaurant, setRestaurant] = useState<Restaurant | null>(null);
    const [name, setName] = useState('');
    const [address, setAddress] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [submitting, setSubmitting] = useState(false);

    useEffect(() => {
        const fetchRestaurant = async () => {
            try {
                if (id) {
                    const fetchedRestaurant = await getRestaurantById(id);
                    if (fetchedRestaurant) {
                        setRestaurant(fetchedRestaurant);
                        setName(fetchedRestaurant.name);
                        setAddress(fetchedRestaurant.address);
                    } else {
                        setError('Restaurant not found.');
                    }
                }
            } catch (err) {
                setError('Error fetching restaurant data.');
                console.error('Error fetching restaurant:', err);
            } finally {
                setLoading(false);
            }
        };
        fetchRestaurant();
    }, [id]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!restaurant || !id) return;

        setSubmitting(true);
        setError(null);

        const updatedRestaurantData: Restaurant = {
            ...restaurant,
            name,
            address,
        };

        try {
            const success = await updateRestaurant(id, updatedRestaurantData);
            if (success) {
                navigate(`/restaurants/${id}`);
            } else {
                setError('Failed to update restaurant.');
            }
        } catch (err) {
            setError('Error updating restaurant.');
            console.error('Error updating restaurant:', err);
        } finally {
            setSubmitting(false);
        }
    };

    if (loading) {
        return <div>Loading restaurant...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    if (!restaurant) {
        return <div>Restaurant not found.</div>;
    }

    return (
        <div className="form-container">
            <h2>Update Restaurant</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="name">Name:</label>
                    <input
                        type="text"
                        id="name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="address">Address:</label>
                    <input
                        type="text"
                        id="address"
                        value={address}
                        onChange={(e) => setAddress(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" disabled={submitting}>
                    {submitting ? 'Updating...' : 'Update Restaurant'}
                </button>
            </form>
        </div>
    );
};

export default UpdateRestaurantForm;