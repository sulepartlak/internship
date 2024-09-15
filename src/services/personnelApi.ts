import axios from 'axios';
import { Personnel } from '../types/personnel';

const api = axios.create({
  baseURL: process.env.REACT_APP_PERSONNEL_API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Fetch all personnel
export const fetchPersonnel = async (): Promise<Personnel[]> => {
  try {
    const response = await api.get('/personnel');
    return response.data;
  } catch (error) {
    console.error('Error fetching personnel:', error);
    throw error; 
  }
};

// Add a new personnel
export const addPersonnel = async (newPersonnel: Omit<Personnel, 'id'>): Promise<Personnel> => {
  try {
    const response = await api.post('/personnel', newPersonnel);
    return response.data;
  } catch (error) {
    console.error('Error adding personnel:', error);
    throw error;
  }
};

// Update an existing personnel
export const updatePersonnel = async (id: number, updatedPersonnel: Omit<Personnel, 'id'>): Promise<Personnel> => {
  try {
    const response = await api.put(`/personnel/${id}`, updatedPersonnel);
    return response.data;
  } catch (error) {
    console.error('Error updating personnel:', error);
    throw error;
  }
};

// Delete a personnel
export const deletePersonnel = async (id: number): Promise<void> => {
  try {
    await api.delete(`/personnel/${id}`);
  } catch (error) {
    console.error('Error deleting personnel:', error);
    throw error;
  }
};

// Fetch a specific personnel by ID
export const fetchPersonnelById = async (id: number): Promise<Personnel> => {
  try {
    const response = await api.get(`/personnel/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching personnel by ID:', error);
    throw error;
  }
};


