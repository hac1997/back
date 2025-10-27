'use client';
import { useState } from "react";
import { useAuth } from "@/contexts/AuthContext";
import { PostService } from "@/services/postService";

interface PostFormProps {
  onPostCreated?: () => void;
}

export default function PostForm({ onPostCreated }: PostFormProps) {
  const { user } = useAuth();
  const [title, setTitle] = useState("");
  const [body, setBody] = useState("");
  const [tags, setTags] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      await PostService.create({
        title,
        body,
        tags: tags.split(",").map(t => t.trim()).filter(t => t),
        userId: user?.userId || 1,
      });
      setTitle("");
      setBody("");
      setTags("");
      onPostCreated?.();
    } catch (err: any) {
      setError(err.response?.data?.message || "Erro ao criar post");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="bg-slate-800/50 backdrop-blur-sm border border-slate-700 shadow-lg p-6 rounded-xl mb-6"
    >
      <h2 className="text-lg font-semibold mb-4 text-white">Criar novo post</h2>
      {error && (
        <div className="bg-red-500/10 border border-red-500/50 text-red-400 px-4 py-3 rounded-lg mb-4">
          {error}
        </div>
      )}
      <input
        type="text"
        placeholder="Título"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        className="w-full bg-slate-900/50 border border-slate-600 text-white placeholder-slate-500 p-3 rounded-lg mb-3 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-transparent transition"
        required
        disabled={loading}
      />
      <textarea
        placeholder="Escreva seu post..."
        value={body}
        onChange={(e) => setBody(e.target.value)}
        className="w-full bg-slate-900/50 border border-slate-600 text-white placeholder-slate-500 p-3 rounded-lg mb-3 h-32 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-transparent transition resize-none"
        required
        disabled={loading}
      />
      <input
        type="text"
        placeholder="Tags (separadas por vírgula)"
        value={tags}
        onChange={(e) => setTags(e.target.value)}
        className="w-full bg-slate-900/50 border border-slate-600 text-white placeholder-slate-500 p-3 rounded-lg mb-4 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-transparent transition"
        disabled={loading}
      />
      <button
        type="submit"
        disabled={loading}
        className="bg-emerald-600 hover:bg-emerald-700 text-white font-medium py-2 px-6 rounded-lg transition disabled:bg-slate-600 disabled:cursor-not-allowed"
      >
        {loading ? "Publicando..." : "Publicar"}
      </button>
    </form>
  );
}
