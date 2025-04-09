import { Router } from 'express';
import { addCartItems, getCartItems, getTotalPrice } from '../helpers/shopping-cart-backend-client.js';
import cartRetryCache from '../helpers/cartRetryCache.js';
import { to } from '../utils.js';
import { isServerError } from '../utils.js';

const ADD_ITEMS_RETRY_INETRVAL = 1 * 60 * 1000;

const router = Router();

router.post('/add', async (req, res) => {
	const cartItems = req.body || {};
	const [err, response] = await to(addItemsToCart(cartItems));
	if (err) {
		if (isServerError(err.status)) {
			console.log(`Added ${cartItems.items.length} to the retry cache`);
			return res.status(200).json({
				status: true,
				message: 'Items queued to be added to cart'
			});
		} else {
			throw err;
		}
	}
	return res.status(200).json({
		status: true,
		message: 'Items successfully added to cart'
	});
});

router.get('/view', async (req, res) => {
	const items = await getCartItems();
	return res.status(200).json(items);
});

router.get('/total', async (req, res) => {
	const itemsWithPrice = await getTotalPrice();
	return res.status(200).json(itemsWithPrice);
});


const addItemsToCart = async (cartItems, isRetry = false) => {
	try {
		if (cartItems.items) {
			cartItems.items.push(...cartRetryCache.getItemsFromRetryCache())
		} else {
			cartItems.items = cartRetryCache.getItemsFromRetryCache();
		}
		if (isRetry && cartItems.items.length === 0) { // Do not send request if cache is empty and it is a retry
			return;
		}
		await addCartItems(cartItems);
	} catch (err) {
		if (isServerError(err.status)) { // Add to retry queue only if it a server error 
			cartRetryCache.addToRetryCache(cartItems.items);
		}
		throw err;
	}
}

setInterval(async () => {
	try {
		await addItemsToCart({}, true);
	} catch (err) {
		console.log('Error while retrying to send cached cart items');
		console.log(err);
	}
}, ADD_ITEMS_RETRY_INETRVAL);


export default router;