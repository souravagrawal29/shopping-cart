import { Router } from 'express';
import { generateToken } from '../helpers/jwt.js';

const router = Router();

const users = [];

router.post('/signup', async (req, res) => {
	const { username } = req.body || {};
	if (!username) {
		return res.status(400).json({ error: 'Username required for signup!'});
	}
	if (doesUserExist(username)) {
		return res.status(409).json({ error: 'User already signed up!'});
	}
	users.push(username.toLowerCase());
	return res.status(201).json({ message: 'User signed up successfully!' });
});


router.post('/signin', async (req, res) => {
	const { username } = req.body || {};
	if (!username) {
		return res.status(400).json({ error: 'Username required for signin!'});
	}
	if (!doesUserExist(username)) {
		return res.status(401).json({ error: 'User not registered. Please sign up !'});
	}
	const payload = {
		sub: username.toLowerCase()
	}
	const authToken = await generateToken(payload)
	return res.status(200).json({ 
		message: 'User signed in successfully',
		token: authToken
	})
})

const doesUserExist = (username) => {
	return users.find(u => u === username.toLowerCase());
}

export default router;