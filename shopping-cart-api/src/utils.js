export const to = (promise) => {
  return promise.then((result) => [null, result]).catch((err) => [err]);
};

export const resolveOrThrow = async (promise) => {
  const [err, result] = await to(promise);
  if (err) {
    if (err.response) {
      const error = new Error(err?.response?.data?.error);
      error.status = err.status;
      throw error;
    } else if (err.request) {
      const error = new Error('Server is down or unreachable');
      throw error;
    } else {
      const error = new Error('Unexpected error');
      throw error;
    }
  }
  return result;
};

export const isServerError = (status) => {
  return !status || (status >= 500 && status < 600);
};
