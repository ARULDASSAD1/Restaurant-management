const API_BASE_URL = 'http://localhost:8086/api';

// Interfaces (adjust based on your backend model)
export interface Restaurant {
  id?: string;
  name: string;
  address: string;
}

export interface MenuItem {
  id?: string;
  restaurantId: string;
  name: string;
  description: string;
  price: number;
  category: string;
}

export interface Order {
  id?: string;
  restaurantId: string;
  orderTime: string; // or Date, depending on how you handle dates
  status: string;
  totalPrice: number;
  // You might add a field here to store order items,
  // or fetch them separately using getOrderItemsByOrderId
}

export interface OrderItem {
  id?: string;
  menuItemId: string;
  quantity: number;
  price: number;
}

// API Functions

export const getAllRestaurants = async (): Promise<Restaurant[]> => {
  const response = await fetch(`${API_BASE_URL}/restaurants`);
  if (!response.ok) {
    throw new Error(`Error fetching restaurants: ${response.statusText}`);
  }
  return response.json();
};

export const getRestaurantById = async (id: string): Promise<Restaurant | null> => {
  const response = await fetch(`${API_BASE_URL}/restaurants/${id}`);
  if (response.status === 404) {
    return null; // Restaurant not found
  }
  if (!response.ok) {
    throw new Error(`Error fetching restaurant with ID ${id}: ${response.statusText}`);
  }
  return response.json();
};

export const createRestaurant = async (restaurant: Restaurant): Promise<Restaurant> => {
  const response = await fetch(`${API_BASE_URL}/restaurants`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(restaurant),
  });
  if (!response.ok) {
    throw new Error(`Error creating restaurant: ${response.statusText}`);
  }
  return response.json();
};

export const updateRestaurant = async (id: string, restaurant: Restaurant): Promise<void> => {
  const response = await fetch(`${API_BASE_URL}/restaurants/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(restaurant),
  });
  if (!response.ok) {
    throw new Error(`Error updating restaurant with ID ${id}: ${response.statusText}`);
  }
  // Assuming your PUT endpoint returns 200 OK or 204 No Content
};

export const deleteRestaurant = async (id: string): Promise<void> => {
  const response = await fetch(`${API_BASE_URL}/restaurants/${id}`, {
    method: 'DELETE',
  });
  if (!response.ok) {
    throw new Error(`Error deleting restaurant with ID ${id}: ${response.statusText}`);
  }
  // Assuming your DELETE endpoint returns 204 No Content
};

export const getMenuItemsByRestaurantId = async (restaurantId: string): Promise<MenuItem[]> => {
  const response = await fetch(`${API_BASE_URL}/menuitems?restaurantId=${restaurantId}`);
  if (!response.ok) {
    throw new Error(`Error fetching menu items for restaurant ID ${restaurantId}: ${response.statusText}`);
  }
  return response.json();
};

export const createMenuItem = async (menuItem: MenuItem): Promise<MenuItem> => {
  const response = await fetch(`${API_BASE_URL}/menuitems`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(menuItem),
  });
  if (!response.ok) {
    throw new Error(`Error creating menu item: ${response.statusText}`);
  }
  return response.json();
};

export const updateMenuItem = async (id: string, menuItem: MenuItem): Promise<void> => {
  const response = await fetch(`${API_BASE_URL}/menuitems/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(menuItem),
  });
  if (!response.ok) {
    throw new Error(`Error updating menu item with ID ${id}: ${response.statusText}`);
  }
};

export const deleteMenuItem = async (id: string): Promise<void> => {
  const response = await fetch(`${API_BASE_URL}/menuitems/${id}`, {
    method: 'DELETE',
  });
  if (!response.ok) {
    throw new Error(`Error deleting menu item with ID ${id}: ${response.statusText}`);
  }
};

export const createOrder = async (order: Order): Promise<Order> => {
  const response = await fetch(`${API_BASE_URL}/orders`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(order),
  });
  if (!response.ok) {
    throw new Error(`Error creating order: ${response.statusText}`);
  }
  return response.json();
};

export const getOrderById = async (id: string): Promise<Order | null> => {
  const response = await fetch(`${API_BASE_URL}/orders/${id}`);
  if (response.status === 404) {
    return null; // Order not found
  }
  if (!response.ok) {
    throw new Error(`Error fetching order with ID ${id}: ${response.statusText}`);
  }
  return response.json();
};

export const getOrdersByRestaurantId = async (restaurantId: string): Promise<Order[]> => {
  const response = await fetch(`${API_BASE_URL}/orders?restaurantId=${restaurantId}`);
  if (!response.ok) {
    throw new Error(`Error fetching orders for restaurant ID ${restaurantId}: ${response.statusText}`);
  }
  return response.json();
};

export const addOrderItemToOrder = async (orderId: string, orderItem: OrderItem): Promise<OrderItem> => {
  const response = await fetch(`${API_BASE_URL}/orders/${orderId}/items`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(orderItem),
  });
  if (!response.ok) {
    throw new Error(`Error adding order item to order ${orderId}: ${response.statusText}`);
  }
  return response.json();
};

export const getOrderItemsByOrderId = async (orderId: string): Promise<OrderItem[]> => {
  const response = await fetch(`${API_BASE_URL}/orders/${orderId}/items`);
  if (!response.ok) {
    throw new Error(`Error fetching order items for order ${orderId}: ${response.statusText}`);
  }
  return response.json();
};