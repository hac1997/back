'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/contexts/AuthContext';
import { adminService } from '@/services/adminService';
import { moderationService } from '@/services/moderationService';
import Link from 'next/link';

export default function AdminPage() {
  const { user, isAdmin, loading } = useAuth();
  const router = useRouter();
  const [appeals, setAppeals] = useState<any[]>([]);
  const [logs, setLogs] = useState<any[]>([]);
  const [activeTab, setActiveTab] = useState<'appeals' | 'logs'>('appeals');
  const [loadingData, setLoadingData] = useState(true);

  useEffect(() => {
    if (!loading && !isAdmin) {
      router.push('/feed');
    }
  }, [loading, isAdmin, router]);

  useEffect(() => {
    if (isAdmin) {
      loadData();
    }
  }, [isAdmin]);

  const loadData = async () => {
    try {
      setLoadingData(true);
      const [appealsData, logsData] = await Promise.all([
        moderationService.getPendingAppeals(),
        moderationService.getAllLogs()
      ]);
      setAppeals(appealsData);
      setLogs(logsData.slice(0, 50));
    } catch (error) {
      console.error('Erro ao carregar dados:', error);
    } finally {
      setLoadingData(false);
    }
  };

  const handleReviewAppeal = async (appealId: number, approved: boolean) => {
    try {
      await adminService.reviewAppeal(
        appealId,
        user?.userId!,
        approved,
        approved ? 'Apelação aprovada' : 'Apelação rejeitada'
      );
      loadData();
      alert(approved ? 'Apelação aprovada com sucesso' : 'Apelação rejeitada');
    } catch (error) {
      alert('Erro ao revisar apelação');
    }
  };

  if (loading || !user) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-slate-900">
        <div className="text-white text-lg">Carregando...</div>
      </div>
    );
  }

  if (!isAdmin) {
    return null;
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900">
      <header className="bg-slate-800/80 backdrop-blur-sm border-b border-slate-700">
        <div className="max-w-7xl mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-2xl font-bold text-white">Painel Admin</h1>
              <p className="text-slate-400 text-sm mt-1">Gestão completa do sistema</p>
            </div>
            <div className="flex gap-3">
              <Link
                href="/moderation"
                className="px-4 py-2 bg-amber-600 hover:bg-amber-700 text-white rounded-lg transition"
              >
                Moderação
              </Link>
              <Link
                href="/feed"
                className="px-4 py-2 bg-slate-700 hover:bg-slate-600 text-white rounded-lg transition"
              >
                Voltar ao Feed
              </Link>
            </div>
          </div>
        </div>
      </header>

      <main className="max-w-7xl mx-auto px-4 py-8">
        <div className="flex gap-4 mb-6">
          <button
            onClick={() => setActiveTab('appeals')}
            className={`px-6 py-3 rounded-lg font-medium transition ${
              activeTab === 'appeals'
                ? 'bg-purple-600 text-white'
                : 'bg-slate-800/50 text-slate-400 hover:bg-slate-700'
            }`}
          >
            Apelações Pendentes ({appeals.length})
          </button>
          <button
            onClick={() => setActiveTab('logs')}
            className={`px-6 py-3 rounded-lg font-medium transition ${
              activeTab === 'logs'
                ? 'bg-purple-600 text-white'
                : 'bg-slate-800/50 text-slate-400 hover:bg-slate-700'
            }`}
          >
            Logs de Moderação ({logs.length})
          </button>
        </div>

        {loadingData ? (
          <div className="text-center text-white py-12">Carregando...</div>
        ) : (
          <>
            {activeTab === 'appeals' && (
              <div className="space-y-4">
                {appeals.length === 0 ? (
                  <div className="bg-slate-800/50 border border-slate-700 rounded-xl p-8 text-center">
                    <p className="text-slate-400">Nenhuma apelação pendente</p>
                  </div>
                ) : (
                  appeals.map((appeal) => (
                    <div
                      key={appeal.appealId}
                      className="bg-slate-800/50 border border-slate-700 rounded-xl p-6"
                    >
                      <div className="flex justify-between items-start mb-4">
                        <div>
                          <h3 className="text-white font-semibold text-lg mb-2">
                            Apelação de {appeal.user?.name || 'Usuário'}
                          </h3>
                          <span className="px-3 py-1 bg-yellow-500/20 text-yellow-400 rounded-lg text-sm border border-yellow-500/50">
                            {appeal.status}
                          </span>
                        </div>
                        <span className="text-slate-500 text-sm">
                          {new Date(appeal.createdAt).toLocaleDateString('pt-BR')}
                        </span>
                      </div>

                      <p className="text-slate-300 mb-4">{appeal.reason}</p>

                      <div className="flex gap-2">
                        <button
                          onClick={() => handleReviewAppeal(appeal.appealId, true)}
                          className="px-4 py-2 bg-emerald-600 hover:bg-emerald-700 text-white rounded-lg transition text-sm"
                        >
                          Aprovar
                        </button>
                        <button
                          onClick={() => handleReviewAppeal(appeal.appealId, false)}
                          className="px-4 py-2 bg-red-600 hover:bg-red-700 text-white rounded-lg transition text-sm"
                        >
                          Rejeitar
                        </button>
                      </div>
                    </div>
                  ))
                )}
              </div>
            )}

            {activeTab === 'logs' && (
              <div className="space-y-3">
                {logs.length === 0 ? (
                  <div className="bg-slate-800/50 border border-slate-700 rounded-xl p-8 text-center">
                    <p className="text-slate-400">Nenhum log disponível</p>
                  </div>
                ) : (
                  logs.map((log) => (
                    <div
                      key={log.logId}
                      className="bg-slate-800/50 border border-slate-700 rounded-lg p-4"
                    >
                      <div className="flex justify-between items-start">
                        <div className="flex-1">
                          <span className="px-2 py-1 bg-blue-500/20 text-blue-400 rounded text-xs border border-blue-500/50">
                            {log.action}
                          </span>
                          <p className="text-slate-300 text-sm mt-2">
                            <strong>{log.moderator?.name || 'Moderador'}</strong>
                            {log.targetUser && ` → ${log.targetUser.name}`}
                          </p>
                          {log.notes && (
                            <p className="text-slate-500 text-xs mt-1">{log.notes}</p>
                          )}
                        </div>
                        <span className="text-slate-500 text-xs">
                          {new Date(log.performedAt).toLocaleString('pt-BR')}
                        </span>
                      </div>
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
