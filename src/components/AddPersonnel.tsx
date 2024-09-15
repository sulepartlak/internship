import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { TextField, Button, Paper, Typography, Alert } from '@mui/material';
import { addPersonnel as addPersonnelApi } from '../services/personnelApi';
import { addPersonnel } from '../redux/personnelSlice';

const AddPersonnel: React.FC = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [position, setPosition] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);
  const dispatch = useDispatch();

  const handleAddPersonnel = async () => {
    if (!firstName || !lastName || !position) {
      setError('All fields are required.');
      return;
    }

    try {
      setError(null); // Clear previous errors
      setSuccess(null); // Clear previous success messages

      // Add new personnel
      const newPersonnel = await addPersonnelApi({ firstName, lastName, position });
      if (newPersonnel) {
        // Update Redux state
        dispatch(addPersonnel(newPersonnel));

        // Clear form fields
        setFirstName('');
        setLastName('');
        setPosition('');

        setSuccess('Personnel added successfully!');
      } else {
        setError('Failed to add personnel.');
      }
    } catch (error) {
      setError('Error adding personnel: ' + (error instanceof Error ? error.message : 'Unknown error'));
    }
  };

  return (
    <Paper style={{ padding: 16, marginTop: 16 }}>
      <Typography variant="h4" gutterBottom align="center">
        Add New Personnel
      </Typography>
      <form noValidate autoComplete="off">
        <TextField
          fullWidth
          label="First Name"
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
          margin="normal"
          error={!firstName && error !== null}
          helperText={!firstName && error !== null ? 'First name is required' : ''}
        />
        <TextField
          fullWidth
          label="Last Name"
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
          margin="normal"
          error={!lastName && error !== null}
          helperText={!lastName && error !== null ? 'Last name is required' : ''}
        />
        <TextField
          fullWidth
          label="Position"
          value={position}
          onChange={(e) => setPosition(e.target.value)}
          margin="normal"
          error={!position && error !== null}
          helperText={!position && error !== null ? 'Position is required' : ''}
        />
        <Button
          variant="contained"
          color="primary"
          onClick={handleAddPersonnel}
          style={{ marginTop: 16 }}
        >
          Add Personnel
        </Button>
        {error && <Alert severity="error" style={{ marginTop: 16 }}>{error}</Alert>}
        {success && <Alert severity="success" style={{ marginTop: 16 }}>{success}</Alert>}
      </form>
    </Paper>
  );
};

export default AddPersonnel;

