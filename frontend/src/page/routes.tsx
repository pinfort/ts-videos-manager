import React from 'react';
import { Route, Routes } from 'react-router-dom';

import App from './App';

function RouteConfigurations() {
  return (
    <Routes>
      <Route path="/" element={<App />} />
    </Routes>
  );
}

export default RouteConfigurations;
