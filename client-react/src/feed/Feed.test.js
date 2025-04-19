import React from 'react';
import { render, fireEvent,screen } from '@testing-library/react';
import Feed from './Feed';
import { BrowserRouter as Router } from 'react-router-dom';
// Mock the react-router-dom module
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'), // Use actual module except for useNavigate
  useNavigate: jest.fn(), // Mock useNavigate
}));
describe('Feed Component', () => {
  it('should render without crashing', () => {
    render(<Feed />);
  });
  test('menu and new post appears on the screen', () => {
    render(<Feed />);
    const menuElement1 = screen.getByText(/Events/i);
    const menuElement2 = screen.getByText(/News Feed/i);
    const menuElement3 = screen.getByText(/Messages/i);
    const Publish = screen.getByText(/Publish/i);
    expect(menuElement1).toBeInTheDocument();
    expect(menuElement2).toBeInTheDocument();
    expect(menuElement3).toBeInTheDocument();
    expect(Publish).toBeInTheDocument();
  });
  /*test('should render feed without crashing', () => {
    render(<Feed />);
    const hiElement = screen.getByText(Search);
    expect(hiElement).toBeInTheDocument();
  });*/
 
/* test('should add a new post', () => {
    const { getByTestId } = render(<Router> <Feed /> </Router>);
    const contentInput = getByTestId('content-input');
    const imageInput = getByTestId('image-input');
    const titleInput = getByTestId('title-input');
    const authorInput = getByTestId('author-input');
    const addButton = getByTestId('add-button');

    fireEvent.change(contentInput, { target: { value: 'Test content' } });
    fireEvent.change(imageInput, { target: { value: 'test.jpg' } });
    fireEvent.change(titleInput, { target: { value: 'Test Title' } });
    fireEvent.change(authorInput, { target: { value: 'Test Author' } });
    fireEvent.click(addPost);

    expect(contentInput.value).toBe('Test content');
    
    
    
  });
  */
  test('should toggle between dark and light mode', () => {
    const { getByTestId } = render(<Router> <Feed /> </Router>);
    const darkModeButton = getByTestId('dark-mode-button-dark');
    

    // Initially, dark mode button should be visible and light mode button should be hidden
    expect(darkModeButton).toBeInTheDocument();
   

    // Click the dark mode button
    fireEvent.click(darkModeButton);

    // After clicking, dark mode button should be hidden and light mode button should be visible
    const lightModeButton = getByTestId('dark-mode-button-light');
    expect(lightModeButton).toBeInTheDocument();
    
    // Click the light mode button
    
});
/*
  it('should toggle between dark and light mode', () => {
    const { getByTestId } = render(<Feed />);


    const lightModeButton = getByTestId('dark-mode-button-light');
    // Switch to dark mode
   

    fireEvent.click(lightModeButton); // Switch back to light mode
    expect(document.body.className).toContain('DarkModeFeed');
  });*/
});
