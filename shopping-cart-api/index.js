import './src/config.js';
import express from 'express';
import routes from './src/routes/index.js';
import { errorMiddleware } from './src/middlewares.js';

const app = express();

app.use(express.json());
app.use(express.json({ extended: true }));

app.get('/', (req, res) => { // Home Page
  return res.status(200).send('Shopping Cart API Layer Home Page');
});

app.use('/api', routes);

app.use(errorMiddleware);

const PORT = process.env.PORT || 3000;

app.listen(PORT, () => {
  console.log(`Shopping Cart API Layer running on port ${PORT}`);
});

process.on('uncaughtException', (err) => {
  console.log('uncaughtException', err);
});