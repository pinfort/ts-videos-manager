import React from 'react';
import { render, screen } from '@testing-library/react';
import App from '../../../page/App';

test('renders learn react link', () => {
  render(<App />);
  const linkElement = screen.getByText(/executedFileId/i);
  expect(linkElement).toBeInTheDocument();
});
