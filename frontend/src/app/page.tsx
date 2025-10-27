'use client';

import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/contexts/AuthContext';
import Link from 'next/link';

export default function Home() {
  const { isAuthenticated, loading } = useAuth();
  const router = useRouter();

  useEffect(() => {
    if (!loading && isAuthenticated) {
      router.push('/feed');
    }
  }, [loading, isAuthenticated, router]);

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-slate-900">
        <div className="text-white text-lg">Carregando...</div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900 flex items-center justify-center">
      <div className="max-w-4xl mx-auto px-4 text-center">
        <h1 className="text-5xl md:text-6xl font-bold text-white mb-6">
          Fórum de Discussões
        </h1>
        <p className="text-xl text-slate-300 mb-12 max-w-2xl mx-auto">
          Uma plataforma moderna para compartilhar ideias, fazer perguntas e construir conhecimento em comunidade.
        </p>

        <div className="flex flex-col sm:flex-row gap-4 justify-center">
          <Link
            href="/login"
            className="px-8 py-4 bg-emerald-600 hover:bg-emerald-700 text-white font-medium rounded-lg transition text-lg"
          >
            Entrar
          </Link>
          <Link
            href="/register"
            className="px-8 py-4 bg-slate-700 hover:bg-slate-600 text-white font-medium rounded-lg transition text-lg"
          >
            Criar Conta
          </Link>
        </div>

        <div className="mt-16 grid md:grid-cols-3 gap-8 text-left">
          <div className="bg-slate-800/50 backdrop-blur-sm border border-slate-700 rounded-xl p-6">
            <div className="text-3xl mb-3">💬</div>
            <h3 className="text-xl font-semibold text-white mb-2">Discussões</h3>
            <p className="text-slate-400">Participe de conversas significativas e compartilhe seu conhecimento.</p>
          </div>

          <div className="bg-slate-800/50 backdrop-blur-sm border border-slate-700 rounded-xl p-6">
            <div className="text-3xl mb-3">🛡️</div>
            <h3 className="text-xl font-semibold text-white mb-2">Moderação</h3>
            <p className="text-slate-400">Sistema robusto de moderação para manter a comunidade saudável.</p>
          </div>

          <div className="bg-slate-800/50 backdrop-blur-sm border border-slate-700 rounded-xl p-6">
            <div className="text-3xl mb-3">🎯</div>
            <h3 className="text-xl font-semibold text-white mb-2">Organização</h3>
            <p className="text-slate-400">Tags e categorias para encontrar exatamente o que você procura.</p>
          </div>
        </div>
      </div>
    </div>
  );
}
