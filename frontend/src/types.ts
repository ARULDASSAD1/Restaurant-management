export interface Restaurant {
  id: string;
  name: string;
  address: string;
}

export interface MenuItem {
  id: string;
  restaurantId: string;
  name: string;
  description: string;
  price: number;
  category: string;
}

export interface Order {
  id: string;
  restaurantId: string;
  orderTime: string; // Date in Java maps to string in TypeScript for simplicity in API calls
  status: string;
  totalPrice: number;
}

export interface OrderItem {
  id: string;
  menuItemId: string;
  quantity: number;
  price: number;
}