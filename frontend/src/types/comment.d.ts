import { Author } from './post';

export interface Comment {
  commentId: number;
  body: string;
  author: Author;
  createdAt: string;
  visible: boolean;
  pinned: boolean;
}

export interface CommentRequest {
  body: string;
  userId: number;
}
