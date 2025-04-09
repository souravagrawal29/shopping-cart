import { response } from 'express';
import { resolveOrThrow } from '../utils.js';
import axios from 'axios';

const SHOPPING_CART_BASE_URL = 'http://localhost:8080/api/cart/';

const shoppingCartClient = axios.create({
  baseURL: SHOPPING_CART_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const addCartItems = async (cartItems) => {
  const response = await resolveOrThrow(shoppingCartClient.post('/items', cartItems));
  return response.status === 200;
};

export const getCartItems = async () => {
  const response = await resolveOrThrow(shoppingCartClient.get('/items'));
  return response.data;
};

export const getTotalPrice = async () => {
  const response = await resolveOrThrow(shoppingCartClient.get('/total'));
  return response.data;
};