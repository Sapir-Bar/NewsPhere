import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import NewPost from './NewPost';

describe('NewPost component', () => {
  test('renders input fields and "Publish" button', () => {
    const addPost = jest.fn(); // Mock function to simulate addPost callback

    const { getByPlaceholderText, getByText } = render(
      <NewPost addPost={addPost} />
    );

    // Find input field by placeholder text and simulate typing
    const contentInput = getByPlaceholderText("What's on your mind?");
    fireEvent.change(contentInput, { target: { value: 'Test content' } });

    // Find "Publish" button and simulate click
    const publishButton = getByText('Publish');
    fireEvent.click(publishButton);

    // Assert that addPost function is called with the correct content
    expect(addPost).toHaveBeenCalledWith({
      content: 'Test content',
      imageUrl: '', // Assuming no image is uploaded in this test
      author: 'Alice', // Assuming author is hardcoded in the component
      avatarUrl: 'details.avatar', // Assuming no avatar URL is set in this test
      date: expect.any(String), // Expecting any string for the date
      icon: '', // Assuming no icon is set in this test
    });
  });
});
