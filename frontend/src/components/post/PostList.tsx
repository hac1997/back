'use client';
import { useEffect, useState } from "react";
import { PostService } from "@/services/postService";
import PostCard from "./PostCard";
import { Post } from "@/types/post";

interface PostListProps {
  refreshTrigger?: number;
}

export default function PostList({ refreshTrigger }: PostListProps) {
  const [posts, setPosts] = useState<Post[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    loadPosts();
  }, [refreshTrigger]);

  const loadPosts = async () => {
    try {
      setLoading(true);
      setError("");
      const data = await PostService.getAll();
      setPosts(data);
    } catch (err: any) {
      setError("Erro ao carregar posts. Verifique se o backend está rodando.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="text-center py-12">
        <div className="inline-block animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
        <p className="mt-4 text-gray-600">Carregando posts...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="bg-red-100 border border-red-400 text-red-700 px-6 py-4 rounded-lg">
        <h3 className="font-bold mb-2">Erro ao carregar posts</h3>
        <p className="mb-3">{error}</p>
        <div className="bg-red-50 border-l-4 border-red-700 p-3 mt-3">
          <p className="font-semibold mb-1">Para iniciar o backend:</p>
          <ol className="list-decimal list-inside space-y-1 text-sm">
            <li>Certifique-se de que o PostgreSQL está rodando na porta 5432</li>
            <li>No diretório backend, execute: <code className="bg-red-200 px-1 rounded">./gradlew bootRun</code></li>
            <li>O backend estará disponível em: <code className="bg-red-200 px-1 rounded">http://localhost:8080/api/v1</code></li>
          </ol>
        </div>
        <button
          onClick={loadPosts}
          className="mt-4 bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700 transition"
        >
          Tentar novamente
        </button>
      </div>
    );
  }

  if (posts.length === 0) {
    return (
      <div className="text-center py-12 bg-gray-50 rounded-lg">
        <p className="text-gray-600">Nenhum post encontrado. Seja o primeiro a criar um!</p>
      </div>
    );
  }

  return (
    <div className="grid gap-4">
      {posts.map((p) => (
        <PostCard key={p.postId} post={p} />
      ))}
    </div>
  );
}
