import { api } from './api';

export const authService = {
  async login(email: string, password: string) {
    const response = await api.post('/auth/login', { email, password });
    return response.data;
  },

  async register(name: string, email: string, password: string) {
    const response = await api.post('/auth/register', { name, email, passwd: password });
    return response.data;
  },

  async verifyEmail(email: string, code: string) {
    const response = await api.post('/auth/verify', { email, code });
    return response.data;
  },

  logout() {
    localStorage.removeItem('user');
  }
};
