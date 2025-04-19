import { render, screen } from '@testing-library/react';
import App from './App';

test('should render app without crashing', () => {
  render(<App />);
});
