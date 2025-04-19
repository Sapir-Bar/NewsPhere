import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import Comment from './Comment';

test('renders comment text and author correctly', () => {
  const comment = {
    author: 'John Doe',
    text: 'This is a test comment',
    date: '2024-02-18',
  };

  const { getByText } = render(<Comment comment={comment} />);
  
  expect(getByText(comment.author)).toBeInTheDocument();
  expect(getByText(comment.text)).toBeInTheDocument();
  expect(getByText(comment.date)).toBeInTheDocument();
});

/*test('allows editing comment text', () => {
  const comment = {
    author: 'John Doe',
    text: 'Initial comment text',
    date: '2024-02-18',
  };

  const onEdit = jest.fn();

  const { getByText, getByLabelText } = render(
    <Comment comment={comment} index={0} onEdit={onEdit} />
  );

  fireEvent.click(getByText('✎'));
  fireEvent.change(getByLabelText('Edit comment'), { target: { value: 'Edited comment text' } });
  fireEvent.click(getByText('Save'));

  expect(onEdit).toHaveBeenCalledWith(0, 'Edited comment text');
});
/*
test('cancels editing on click of cancel button', () => {
  const comment = {
    author: 'John Doe',
    text: 'Initial comment text',
    date: '2024-02-18',
  };

  const onEdit = jest.fn();

  const { getByText, getByLabelText } = render(
    <Comment comment={comment} index={0} onEdit={onEdit} />
  );

  fireEvent.click(getByText('✎'));
  fireEvent.change(getByLabelText('Edit comment'), { target: { value: 'Edited comment text' } });
  fireEvent.click(getByText('Cancel'));

  expect(onEdit).not.toHaveBeenCalled();
});*/
