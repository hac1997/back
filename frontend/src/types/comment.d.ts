import { Author } from './post';

export interface Comment {
  commentId: number;
  body: string;
  author: Author;
  createdAt: string;
}

export interface CommentRequest {
  body: string;
  userId: number;
}
