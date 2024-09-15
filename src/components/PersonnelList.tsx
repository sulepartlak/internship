import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Personnel } from '../types/personnel';
import { fetchPersonnel, deletePersonnel, updatePersonnel } from '../services/personnelApi';
import {
  addPersonnel,
  fetchPersonnelStart,
  fetchPersonnelSuccess,
  fetchPersonnelFailure,
  deletePersonnelSuccess,
  deletePersonnelFailure,
  updatePersonnelSuccess,
  updatePersonnelFailure,
} from '../redux/personnelSlice';
import { RootState } from '../redux/store';
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  CircularProgress,
  Typography,
  Alert,
  Button,
  TextField,
  Modal,
  Box,
} from '@mui/material';

const PersonnelList: React.FC = () => {
  const dispatch = useDispatch();
  const { list, loading, error } = useSelector((state: RootState) => state.personnel);

  const [open, setOpen] = useState(false);
  const [selectedPersonnel, setSelectedPersonnel] = useState<Personnel | null>(null);
  const [formData, setFormData] = useState<{ firstName: string; lastName: string; position: string }>({
    firstName: '',
    lastName: '',
    position: '',
  });
  const [formErrors, setFormErrors] = useState<{ firstName: string; lastName: string; position: string }>({
    firstName: '',
    lastName: '',
    position: '',
  });
  const [showAlert, setShowAlert] = useState(false); // State to control the visibility of the warning alert

  useEffect(() => {
    const getPersonnel = async () => {
      dispatch(fetchPersonnelStart());
      try {
        const data = await fetchPersonnel();
        dispatch(fetchPersonnelSuccess(data));
      } catch (err) {
        if (err instanceof Error) {
          dispatch(fetchPersonnelFailure(err.message));
        } else {
          dispatch(fetchPersonnelFailure('An unknown error occurred'));
        }
      }
    };

    getPersonnel();
  }, [dispatch]);

  const handleDelete = async (id: number) => {
    try {
      await deletePersonnel(id);
      dispatch(deletePersonnelSuccess(id));
    } catch (err) {
      if (err instanceof Error) {
        dispatch(deletePersonnelFailure(err.message));
      } else {
        dispatch(deletePersonnelFailure('An unknown error occurred'));
      }
    }
  };

  const validateForm = () => {
    let valid = true;
    let errors = { firstName: '', lastName: '', position: '' };

    if (!formData.firstName) {
      errors.firstName = 'First Name is required';
      valid = false;
    }
    if (!formData.lastName) {
      errors.lastName = 'Last Name is required';
      valid = false;
    }
    if (!formData.position) {
      errors.position = 'Position is required';
      valid = false;
    }

    setFormErrors(errors);
    return valid;
  };

  const handleUpdate = async () => {
    if (validateForm() && selectedPersonnel) {
      try {
        const updatedPersonnel = { ...selectedPersonnel, ...formData };
        await updatePersonnel(updatedPersonnel.id, updatedPersonnel);
        dispatch(updatePersonnelSuccess(updatedPersonnel));
        setOpen(false);
        setSelectedPersonnel(null);
        setFormData({ firstName: '', lastName: '', position: '' });
        setFormErrors({ firstName: '', lastName: '', position: '' }); // Clear errors
        setShowAlert(false); // Hide alert
      } catch (err) {
        if (err instanceof Error) {
          dispatch(updatePersonnelFailure(err.message));
        } else {
          dispatch(updatePersonnelFailure('An unknown error occurred'));
        }
      }
    } else {
      setShowAlert(true); // Show alert if form validation fails
    }
  };

  const handleOpenModal = (personnel: Personnel) => {
    setSelectedPersonnel(personnel);
    setFormData({
      firstName: personnel.firstName,
      lastName: personnel.lastName,
      position: personnel.position,
    });
    setOpen(true);
  };

  const handleCloseModal = () => {
    setOpen(false);
    setSelectedPersonnel(null);
    setFormData({ firstName: '', lastName: '', position: '' });
    setFormErrors({ firstName: '', lastName: '', position: '' }); // Clear errors
    setShowAlert(false); // Hide alert
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  if (loading) return <CircularProgress />;
  if (error) return <Alert severity="error">Error: {error}</Alert>;

  return (
    <div>
      <TableContainer component={Paper}>
        <Typography variant="h4" gutterBottom align="center">
          Personnel List
        </Typography>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>First Name</TableCell>
              <TableCell>Last Name</TableCell>
              <TableCell>Position</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {list.map((personnel) => (
              <TableRow key={personnel.id}>
                <TableCell>{personnel.id}</TableCell>
                <TableCell>{personnel.firstName}</TableCell>
                <TableCell>{personnel.lastName}</TableCell>
                <TableCell>{personnel.position}</TableCell>
                <TableCell>
                  <Button
                    variant="contained"
                    color="error"
                    onClick={() => handleDelete(personnel.id)}
                  >
                    Delete
                  </Button>
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={() => handleOpenModal(personnel)}
                    sx={{ ml: 1 }}
                  >
                    Update
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      {/* Warning Alert */}
      {showAlert && (
        <Alert severity="warning" onClose={() => setShowAlert(false)}>
          Please fill out all required fields.
        </Alert>
      )}

      {/* Update Modal */}
      <Modal open={open} onClose={handleCloseModal}>
        <Box
          sx={{
            position: 'absolute',
            top: '50%',
            left: '50%',
            transform: 'translate(-50%, -50%)',
            width: 400,
            bgcolor: 'background.paper',
            border: '2px solid #000',
            boxShadow: 24,
            p: 4,
          }}
        >
          <Typography variant="h6" gutterBottom>
            Update Personnel
          </Typography>
          <TextField
            label="First Name"
            name="firstName"
            value={formData.firstName}
            onChange={handleChange}
            fullWidth
            margin="normal"
            error={!!formErrors.firstName}
            helperText={formErrors.firstName}
          />
          <TextField
            label="Last Name"
            name="lastName"
            value={formData.lastName}
            onChange={handleChange}
            fullWidth
            margin="normal"
            error={!!formErrors.lastName}
            helperText={formErrors.lastName}
          />
          <TextField
            label="Position"
            name="position"
            value={formData.position}
            onChange={handleChange}
            fullWidth
            margin="normal"
            error={!!formErrors.position}
            helperText={formErrors.position}
          />
          <Button
            variant="contained"
            color="primary"
            onClick={handleUpdate}
            sx={{ mt: 2 }}
          >
            Update
          </Button>
        </Box>
      </Modal>
    </div>
  );
};

export default PersonnelList;
