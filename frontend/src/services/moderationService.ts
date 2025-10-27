import { api } from './api';

export const moderationService = {
  async createReport(data: {
    reporterId: number;
    targetUserId?: number;
    targetPostId?: number;
    targetCommentId?: number;
    reportType: string;
    reason: string;
  }) {
    const response = await api.post('/moderation/reports', data);
    return response.data;
  },

  async getAllReports() {
    const response = await api.get('/moderation/reports');
    return response.data;
  },

  async getPendingReports() {
    const response = await api.get('/moderation/reports/pending');
    return response.data;
  },

  async getReportsByUser(userId: number) {
    const response = await api.get(`/moderation/reports/user/${userId}`);
    return response.data;
  },

  async reviewReport(reportId: number, reviewerId: number, status: string, notes: string) {
    const response = await api.put(`/moderation/reports/${reportId}/review`, null, {
      params: { reviewerId, status, notes }
    });
    return response.data;
  },

  async lockPost(postId: number, moderatorId: number) {
    const response = await api.post(`/moderation/posts/${postId}/lock`, null, {
      params: { moderatorId }
    });
    return response.data;
  },

  async unlockPost(postId: number, moderatorId: number) {
    const response = await api.post(`/moderation/posts/${postId}/unlock`, null, {
      params: { moderatorId }
    });
    return response.data;
  },

  async pinComment(commentId: number, moderatorId: number) {
    const response = await api.post(`/moderation/comments/${commentId}/pin`, null, {
      params: { moderatorId }
    });
    return response.data;
  },

  async unpinComment(commentId: number, moderatorId: number) {
    const response = await api.post(`/moderation/comments/${commentId}/unpin`, null, {
      params: { moderatorId }
    });
    return response.data;
  },

  async restorePost(postId: number, moderatorId: number) {
    const response = await api.post(`/moderation/posts/${postId}/restore`, null, {
      params: { moderatorId }
    });
    return response.data;
  },

  async restoreComment(commentId: number, moderatorId: number) {
    const response = await api.post(`/moderation/comments/${commentId}/restore`, null, {
      params: { moderatorId }
    });
    return response.data;
  },

  async createDiscussion(data: {
    creatorId: number;
    discussionType: string;
    description: string;
    reportId?: number;
    targetUserId?: number;
    targetPostId?: number;
    targetCommentId?: number;
  }) {
    const response = await api.post('/moderation/discussions', data);
    return response.data;
  },

  async getAllDiscussions() {
    const response = await api.get('/moderation/discussions');
    return response.data;
  },

  async getDiscussion(discussionId: number) {
    const response = await api.get(`/moderation/discussions/${discussionId}`);
    return response.data;
  },

  async getDiscussionVotes(discussionId: number) {
    const response = await api.get(`/moderation/discussions/${discussionId}/votes`);
    return response.data;
  },

  async voteOnDiscussion(discussionId: number, data: {
    moderatorId: number;
    inFavor: boolean;
    justification: string;
  }) {
    const response = await api.post(`/moderation/discussions/${discussionId}/vote`, data);
    return response.data;
  },

  async createAppeal(data: {
    userId: number;
    reason: string;
    discussionId?: number;
  }) {
    const response = await api.post('/moderation/appeals', data);
    return response.data;
  },

  async getPendingAppeals() {
    const response = await api.get('/moderation/appeals/pending');
    return response.data;
  },

  async getAppealsByUser(userId: number) {
    const response = await api.get(`/moderation/appeals/user/${userId}`);
    return response.data;
  },

  async getAllLogs() {
    const response = await api.get('/moderation/logs');
    return response.data;
  },

  async getLogsByUser(userId: number) {
    const response = await api.get(`/moderation/logs/user/${userId}`);
    return response.data;
  }
};
