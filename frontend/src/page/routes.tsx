import React from 'react';
import { Route, Routes } from 'react-router-dom';

import App from './App';
import ProgramDetail from './ProgramDetail';
import Stream from './Stream';

function RouteConfigurations() {
  return (
    <Routes>
      <Route path="/" element={<App />} />
      <Route path="/programs/:id" element={<ProgramDetail />} />
      <Route path="/video/:id/view" element={<Stream/>} />
    </Routes>
  );
}

export default RouteConfigurations;
