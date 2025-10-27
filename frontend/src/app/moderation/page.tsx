'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/contexts/AuthContext';
import { moderationService } from '@/services/moderationService';
import Link from 'next/link';

export default function ModerationPage() {
  const { user, isModerator, isAdmin, loading } = useAuth();
  const router = useRouter();
  const [reports, setReports] = useState<any[]>([]);
  const [discussions, setDiscussions] = useState<any[]>([]);
  const [activeTab, setActiveTab] = useState<'reports' | 'discussions'>('reports');
  const [loadingData, setLoadingData] = useState(true);

  useEffect(() => {
    if (!loading && !isModerator && !isAdmin) {
      router.push('/feed');
    }
  }, [loading, isModerator, isAdmin, router]);

  useEffect(() => {
    if (isModerator || isAdmin) {
      loadData();
    }
  }, [isModerator, isAdmin]);

  const loadData = async () => {
    try {
      setLoadingData(true);
      const [reportsData, discussionsData] = await Promise.all([
        moderationService.getPendingReports(),
        moderationService.getAllDiscussions()
      ]);
      setReports(reportsData);
      setDiscussions(discussionsData);
    } catch (error) {
      console.error('Erro ao carregar dados:', error);
    } finally {
      setLoadingData(false);
    }
  };

  const handleReviewReport = async (reportId: number, status: string) => {
    try {
      await moderationService.reviewReport(
        reportId,
        user?.userId!,
        status,
        `Revisado por ${user?.name}`
      );
      loadData();
    } catch (error) {
      alert('Erro ao revisar report');
    }
  };

  const handleLockPost = async (postId: number) => {
    try {
      await moderationService.lockPost(postId, user?.userId!);
      alert('Post trancado com sucesso');
    } catch (error) {
      alert('Erro ao trancar post');
    }
  };

  if (loading || !user) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-slate-900">
        <div className="text-white text-lg">Carregando...</div>
      </div>
    );
  }

  if (!isModerator && !isAdmin) {
    return null;
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900">
      <header className="bg-slate-800/80 backdrop-blur-sm border-b border-slate-700">
        <div className="max-w-7xl mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-2xl font-bold text-white">Painel de Moderação</h1>
              <p className="text-slate-400 text-sm mt-1">Gerenciar reports e discussões</p>
            </div>
            <Link
              href="/feed"
              className="px-4 py-2 bg-slate-700 hover:bg-slate-600 text-white rounded-lg transition"
            >
              Voltar ao Feed
            </Link>
          </div>
        </div>
      </header>

      <main className="max-w-7xl mx-auto px-4 py-8">
        <div className="flex gap-4 mb-6">
          <button
            onClick={() => setActiveTab('reports')}
            className={`px-6 py-3 rounded-lg font-medium transition ${
              activeTab === 'reports'
                ? 'bg-amber-600 text-white'
                : 'bg-slate-800/50 text-slate-400 hover:bg-slate-700'
            }`}
          >
            Reports Pendentes ({reports.length})
          </button>
          <button
            onClick={() => setActiveTab('discussions')}
            className={`px-6 py-3 rounded-lg font-medium transition ${
              activeTab === 'discussions'
                ? 'bg-amber-600 text-white'
                : 'bg-slate-800/50 text-slate-400 hover:bg-slate-700'
            }`}
          >
            Discussões ({discussions.length})
          </button>
        </div>

        {loadingData ? (
          <div className="text-center text-white py-12">Carregando...</div>
        ) : (
          <>
            {activeTab === 'reports' && (
              <div className="space-y-4">
                {reports.length === 0 ? (
                  <div className="bg-slate-800/50 border border-slate-700 rounded-xl p-8 text-center">
                    <p className="text-slate-400">Nenhum report pendente</p>
                  </div>
                ) : (
                  reports.map((report) => (
                    <div
                      key={report.reportId}
                      className="bg-slate-800/50 border border-slate-700 rounded-xl p-6"
                    >
                      <div className="flex justify-between items-start mb-4">
                        <div>
                          <span className="px-3 py-1 bg-red-500/20 text-red-400 rounded-lg text-sm border border-red-500/50">
                            {report.reportType}
                          </span>
                          <span className="ml-2 px-3 py-1 bg-yellow-500/20 text-yellow-400 rounded-lg text-sm border border-yellow-500/50">
                            {report.status}
                          </span>
                        </div>
                        <span className="text-slate-500 text-sm">
                          {new Date(report.createdAt).toLocaleDateString('pt-BR')}
                        </span>
                      </div>

                      <p className="text-white mb-2">
                        <strong>Motivo:</strong> {report.reason}
                      </p>
                      <p className="text-slate-400 text-sm mb-4">
                        Reportado por: {report.reporter?.name || 'Desconhecido'}
                      </p>

                      {report.reportedUser && (
                        <p className="text-slate-400 text-sm mb-4">
                          Usuário reportado: {report.reportedUser.name}
                        </p>
                      )}

                      <div className="flex gap-2">
                        <button
                          onClick={() => handleReviewReport(report.reportId, 'RESOLVED')}
                          className="px-4 py-2 bg-emerald-600 hover:bg-emerald-700 text-white rounded-lg transition text-sm"
                        >
                          Resolver
                        </button>
                        <button
                          onClick={() => handleReviewReport(report.reportId, 'DISMISSED')}
                          className="px-4 py-2 bg-slate-700 hover:bg-slate-600 text-white rounded-lg transition text-sm"
                        >
                          Descartar
                        </button>
                      </div>
                    </div>
                  ))
                )}
              </div>
            )}

            {activeTab === 'discussions' && (
              <div className="space-y-4">
                {discussions.length === 0 ? (
                  <div className="bg-slate-800/50 border border-slate-700 rounded-xl p-8 text-center">
                    <p className="text-slate-400">Nenhuma discussão ativa</p>
                  </div>
                ) : (
                  discussions.map((discussion) => (
                    <div
                      key={discussion.discussionId}
                      className="bg-slate-800/50 border border-slate-700 rounded-xl p-6"
                    >
                      <div className="flex justify-between items-start mb-4">
                        <div>
                          <span className="px-3 py-1 bg-purple-500/20 text-purple-400 rounded-lg text-sm border border-purple-500/50">
                            {discussion.discussionType}
                          </span>
                          <span className="ml-2 px-3 py-1 bg-blue-500/20 text-blue-400 rounded-lg text-sm border border-blue-500/50">
                            {discussion.status}
                          </span>
                        </div>
                        <span className="text-slate-500 text-sm">
                          {new Date(discussion.createdAt).toLocaleDateString('pt-BR')}
                        </span>
                      </div>

                      <p className="text-white mb-4">{discussion.description}</p>

                      <Link
                        href={`/moderation/discussions/${discussion.discussionId}`}
                        className="inline-block px-4 py-2 bg-amber-600 hover:bg-amber-700 text-white rounded-lg transition text-sm"
                      >
                        Ver Detalhes e Votar
                      </Link>
                    </div>
                  ))
                )}
              </div>
            )}
          </>
        )}
      </main>
    </div>
  );
}
