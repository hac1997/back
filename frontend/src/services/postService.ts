import { api } from "./api";
import { Post, PostRequest } from "@/types/post";

export const PostService = {
  getAll: async (): Promise<Post[]> => {
    const response = await api.get("/posts");
    return response.data;
  },
  getById: async (id: number): Promise<Post> => {
    const response = await api.get(`/posts/${id}`);
    return response.data;
  },
  create: async (data: PostRequest): Promise<Post> => {
    const response = await api.post("/posts", data);
    return response.data;
  },
  update: async (id: number, data: PostRequest): Promise<Post> => {
    const response = await api.put(`/posts/${id}`, data);
    return response.data;
  },
  delete: async (id: number): Promise<void> => {
    await api.delete(`/posts/${id}`);
  },
};
