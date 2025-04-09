import { Router } from 'express';
import authRouter from './auth.js';
import cartRouter from './cart.js';
import { authMiddleware } from '../middlewares.js';

const router = Router();

router.use('/auth', authRouter);
router.use('/cart', authMiddleware, cartRouter);

export default router;
