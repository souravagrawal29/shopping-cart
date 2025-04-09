import jwt from 'jsonwebtoken';

const SECRET_KEY = process.env.SECRET_KEY;

export const generateToken = async (payload) => {
  return jwt.sign(payload, SECRET_KEY, { expiresIn: '1d' });
};

export const verifyToken = async (token) => {
  const tk = token.split(' ')[1];
  try {
    const decoded = jwt.verify(tk, SECRET_KEY);
    return { status: true, payload: decoded };
  } catch (err) {
    console.log('Failed to decode token with err=' + err);
    return { status: false, err: err };
  }
};
