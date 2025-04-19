import { render, fireEvent } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom'; // Import BrowserRouter
import RegistrationScreen from './RegistrationScreen';

// Mock the react-router-dom module
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'), // Use actual module except for useNavigate
  useNavigate: jest.fn(), // Mock useNavigate
}));

test('displays error message when password is too short', () => {
  const { getByPlaceholderText, getByText } = render(
    <Router>
      <RegistrationScreen />
    </Router>
  );
  const passwordInput = getByPlaceholderText('Password');
  const confirmPasswordInput = getByPlaceholderText('Confirm Password');

  fireEvent.change(passwordInput, { target: { value: 'short' } });
  fireEvent.change(confirmPasswordInput, { target: { value: 'short' } });

  fireEvent.click(getByText('Sign Up'));

  expect(getByText('Password must be at least 6 characters long and contain both letters and numbers.')).toBeInTheDocument();
});

  test('displays error message when passwords do not match', () => {
    const { getByPlaceholderText, getByText } = render(
        <Router>
          <RegistrationScreen />
        </Router>
      );
    const passwordInput = getByPlaceholderText('Password');
    const confirmPasswordInput = getByPlaceholderText('Confirm Password');

    fireEvent.change(passwordInput, { target: { value: 'password123' } });
    fireEvent.change(confirmPasswordInput, { target: { value: 'differentpassword' } });

    fireEvent.click(getByText('Sign Up'));

    expect(getByText('Passwords do not match.')).toBeInTheDocument();
  });

  test('displays error message when name or nickname contains no characters', () => {
    const { getByPlaceholderText, getByText } = render(
        <Router>
          <RegistrationScreen />
        </Router>
      );
      
    const usernameInput = getByPlaceholderText('UserName');
    const nicknameInput = getByPlaceholderText('Nickname');
    const passwordInput = getByPlaceholderText('Password');
    const confirmPasswordInput = getByPlaceholderText('Confirm Password');

    fireEvent.change(passwordInput, { target: { value: 'short123' } });
    fireEvent.change(confirmPasswordInput, { target: { value: 'short123' } });
    fireEvent.change(usernameInput, { target: { value: '' } });
    fireEvent.change(nicknameInput, { target: { value: '' } });

    fireEvent.click(getByText('Sign Up'));

    expect(getByText('Name and nickName must contain characters.')).toBeInTheDocument();
  });
