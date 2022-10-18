import React from 'react';
import { Route, Routes } from 'react-router-dom';

import App from './App';
import ProgramDetail from './ProgramDetail';

function RouteConfigurations() {
  return (
    <Routes>
      <Route path="/" element={<App />} />
      <Route path="/programs/:id" element={<ProgramDetail />} />
    </Routes>
  );
}

export default RouteConfigurations;
