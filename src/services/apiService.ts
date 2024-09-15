
import axios from 'axios';

export const fetchData = () => {
  return axios.get('/some-url')
    .then(response => {
      console.log('Response:', response);
      return response.data; // Yanıt verilerini geri döndürebilirsiniz
    })
    .catch(error => {
      if (error.response) {
        console.error('Error Response Data:', error.response.data);
        console.error('Error Response Status:', error.response.status);
        console.error('Error Response Headers:', error.response.headers);
      } else if (error.request) {
        console.error('Error Request:', error.request);
      } else {
        console.error('Error Message:', error.message);
      }
      console.error('Error Config:', error.config);
      throw error; // Hatanın üst katmana fırlatılması için
    });
};


