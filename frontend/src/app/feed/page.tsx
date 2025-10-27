'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/contexts/AuthContext';
import PostList from '@/components/post/PostList';
import PostForm from '@/components/post/PostForm';
import Link from 'next/link';

export default function FeedPage() {
  const [refreshTrigger, setRefreshTrigger] = useState(0);
  const { user, loading, logout, isAuthenticated, isModerator, isAdmin } = useAuth();
  const router = useRouter();

  useEffect(() => {
    if (!loading && !isAuthenticated) {
      router.push('/login');
    }
  }, [loading, isAuthenticated, router]);

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-slate-900">
        <div className="text-white text-lg">Carregando...</div>
      </div>
    );
  }

  if (!isAuthenticated) {
    return null;
  }

  const handlePostCreated = () => {
    setRefreshTrigger(prev => prev + 1);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900">
      <header className="bg-slate-800/80 backdrop-blur-sm border-b border-slate-700 sticky top-0 z-50">
        <div className="max-w-6xl mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-2xl font-bold text-white">Fórum de Discussões</h1>
              <p className="text-slate-400 text-sm mt-1">Bem-vindo, {user?.name}</p>
            </div>

            <div className="flex items-center gap-4">
              {(isModerator || isAdmin) && (
                <Link
                  href="/moderation"
                  className="px-4 py-2 bg-amber-600 hover:bg-amber-700 text-white rounded-lg transition text-sm font-medium"
                >
                  Moderação
                </Link>
              )}

              {isAdmin && (
                <Link
                  href="/admin"
                  className="px-4 py-2 bg-purple-600 hover:bg-purple-700 text-white rounded-lg transition text-sm font-medium"
                >
                  Admin
                </Link>
              )}

              <button
                onClick={logout}
                className="px-4 py-2 bg-slate-700 hover:bg-slate-600 text-white rounded-lg transition text-sm font-medium"
              >
                Sair
              </button>
            </div>
          </div>
        </div>
      </header>

      <main className="max-w-4xl mx-auto px-4 py-8">
        <div className="mb-8">
          <PostForm onPostCreated={handlePostCreated} />
        </div>

        <div>
          <h2 className="text-xl font-semibold text-white mb-4">Feed de Posts</h2>
          <PostList refreshTrigger={refreshTrigger} />
        </div>
      </main>
    </div>
  );
}
