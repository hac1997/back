export interface Author {
  userId: number;
  name: string;
  email: string;
}

export interface Post {
  postId: number;
  title: string;
  body: string;
  tags: string[];
  author: Author;
  createdAt: string;
  isLocked: boolean;
  visible: boolean;
}

export interface PostRequest {
  title: string;
  body: string;
  tags: string[];
  userId: number;
}
