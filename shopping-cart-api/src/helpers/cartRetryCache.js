const retryCache = [];

const addToRetryCache = (cartItems) => {
	if (!cartItems) {
		return;
	}
	retryCache.push(...cartItems); // Adds to the cache
}

const getItemsFromRetryCache = () => {
	return retryCache.splice(0); // Clears the cache and returns the array
}

export default {
	addToRetryCache,
	getItemsFromRetryCache
}

