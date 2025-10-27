import { api } from "./api";
import { Comment, CommentRequest } from "@/types/comment";

export const CommentService = {
  getByPost: async (postId: number): Promise<Comment[]> => {
    const response = await api.get(`/posts/${postId}/comments`);
    return response.data;
  },
  create: async (postId: number, data: CommentRequest): Promise<Comment> => {
    const response = await api.post(`/posts/${postId}/comments`, data);
    return response.data;
  },
  update: async (id: number, data: CommentRequest): Promise<Comment> => {
    const response = await api.put(`/posts/comments/${id}`, data);
    return response.data;
  },
  delete: async (id: number): Promise<void> => {
    await api.delete(`/posts/comments/${id}`);
  },
};
