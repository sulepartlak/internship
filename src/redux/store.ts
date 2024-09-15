import { configureStore } from '@reduxjs/toolkit';
import personnelReducer from './personnelSlice';

const store = configureStore({
  reducer: {
    personnel: personnelReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
