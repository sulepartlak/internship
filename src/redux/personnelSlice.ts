import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { Personnel } from '../types/personnel';

interface PersonnelState {
  list: Personnel[];
  loading: boolean;
  error: string | null;
}

const initialState: PersonnelState = {
  list: [],
  loading: false,
  error: null,
};

const personnelSlice = createSlice({
  name: 'personnel',
  initialState,
  reducers: {
    fetchPersonnelStart(state) {
      state.loading = true;
      state.error = null;
    },
    fetchPersonnelSuccess(state, action: PayloadAction<Personnel[]>) {
      state.list = action.payload;
      state.loading = false;
    },
    fetchPersonnelFailure(state, action: PayloadAction<string>) {
      state.loading = false;
      state.error = action.payload;
    },
    addPersonnel(state, action: PayloadAction<Personnel>) {
      state.list.push(action.payload);
    },
    deletePersonnelSuccess(state, action: PayloadAction<number>) {
      state.list = state.list.filter(personnel => personnel.id !== action.payload);
    },
    deletePersonnelFailure(state, action: PayloadAction<string>) {
      state.error = action.payload;
    },
    updatePersonnelSuccess(state, action: PayloadAction<Personnel>) {
      const updatedPersonnel = action.payload;
      const index = state.list.findIndex(personnel => personnel.id === updatedPersonnel.id);
      if (index !== -1) {
        state.list[index] = updatedPersonnel;
      }
    },
    updatePersonnelFailure(state, action: PayloadAction<string>) {
      state.error = action.payload;
    },
  },
});

export const {
  fetchPersonnelStart,
  fetchPersonnelSuccess,
  fetchPersonnelFailure,
  addPersonnel,
  deletePersonnelSuccess,
  deletePersonnelFailure,
  updatePersonnelSuccess,
  updatePersonnelFailure,
} = personnelSlice.actions;

export default personnelSlice.reducer;
