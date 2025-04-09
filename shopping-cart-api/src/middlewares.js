import { verifyToken } from './helpers/jwt.js';

export const authMiddleware = async (req, res, next) => {
  const authToken = req.headers.authorization;
  if (!authToken) {
    return res.status(401).json({ error: 'Access Denied. Authorization header missing.' });
  }
  const verifyStatus = await verifyToken(authToken);
  if (!verifyStatus.status) {
    return res.status(401).json({ error: 'Access Denied. Invalid Authentication Token.' });
  }
  req.user = verifyStatus.payload;
  next();
};

export const errorMiddleware = async (err, req, res, next) => {
  console.log('Error:', err.message);
  console.log('Exception trace:', err);
  return res.status(err.status || 500).json({
    message: err.message || 'Internal Server Error',
  });
};