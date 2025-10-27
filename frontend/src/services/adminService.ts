import { api } from './api';

export const adminService = {
  async promoteModerator(userId: number, adminId: number) {
    const response = await api.post(`/admin/users/${userId}/promote`, null, {
      params: { adminId }
    });
    return response.data;
  },

  async demoteModerator(userId: number, adminId: number) {
    const response = await api.post(`/admin/users/${userId}/demote`, null, {
      params: { adminId }
    });
    return response.data;
  },

  async restoreUser(userId: number, adminId: number) {
    const response = await api.post(`/admin/users/${userId}/restore`, null, {
      params: { adminId }
    });
    return response.data;
  },

  async closeDiscussion(discussionId: number, adminId: number) {
    const response = await api.post(`/admin/discussions/${discussionId}/close`, null, {
      params: { adminId }
    });
    return response.data;
  },

  async reviewAppeal(appealId: number, adminId: number, approved: boolean, reviewNotes: string) {
    const response = await api.post(`/admin/appeals/${appealId}/review`, null, {
      params: { adminId, approved, reviewNotes }
    });
    return response.data;
  }
};
