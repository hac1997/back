'use client';
import { useState } from "react";
import PostList from "@/components/post/PostList";
import PostForm from "@/components/post/PostForm";

export default function Home() {
  const [refreshTrigger, setRefreshTrigger] = useState(0);

  const handlePostCreated = () => {
    setRefreshTrigger(prev => prev + 1);
  };

  return (
    <div className="bg-black">
      <div className="min-h-screen bg-[url('/bg1.png')] opacity-80  ">
        <header className="bg-green shadow-sm">
          <div className="max-w-4xl mx-auto px-4 py-6">
            <h1 className="text-3xl font-bold text-gray-900">Fórum de Discussões</h1>
            <p className="text-gray-600 mt-1">Compartilhe suas ideias e ajude a comunidade</p>
          </div>
        </header>

        <main className="max-w-4xl mx-auto px-4 py-8">
          <PostForm onPostCreated={handlePostCreated} />
          <PostList refreshTrigger={refreshTrigger} />
        </main>
      </div>
    </div>
    
  );
}
