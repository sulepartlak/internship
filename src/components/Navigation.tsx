import React from 'react';
import { Link } from 'react-router-dom';
import { AppBar, Toolbar, Button } from '@mui/material';

const Navigation: React.FC = () => {
  return (
    <AppBar position="static">
      <Toolbar>
        <Button color="inherit" component={Link} to="/personnel-list">
          Personnel List
        </Button>
        <Button color="inherit" component={Link} to="/add-personnel">
          Add Personnel
        </Button>
      </Toolbar>
    </AppBar>
  );
};

export default Navigation;
