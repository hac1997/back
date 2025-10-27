'use client';
import { useState } from "react";
import { PostService } from "@/services/postService";

interface PostFormProps {
  onPostCreated?: () => void;
}

export default function PostForm({ onPostCreated }: PostFormProps) {
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
        userId: 1,
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
      className="bg-white shadow p-6 rounded-2xl mb-6"
    >
      <h2 className="text-lg font-semibold mb-4">Criar novo post</h2>
      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
          {error}
        </div>
      )}
      <input
        type="text"
        placeholder="Título"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        className="w-full border border-gray-300 p-3 rounded-lg mb-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
        required
        disabled={loading}
      />
      <textarea
        placeholder="Escreva seu post..."
        value={body}
        onChange={(e) => setBody(e.target.value)}
        className="w-full border border-gray-300 p-3 rounded-lg mb-3 h-32 focus:outline-none focus:ring-2 focus:ring-blue-500"
        required
        disabled={loading}
      />
      <input
        type="text"
        placeholder="Tags (separadas por vírgula)"
        value={tags}
        onChange={(e) => setTags(e.target.value)}
        className="w-full border border-gray-300 p-3 rounded-lg mb-4 focus:outline-none focus:ring-2 focus:ring-blue-500"
        disabled={loading}
      />
      <button
        type="submit"
        disabled={loading}
        className="bg-blue-600 text-white py-2 px-6 rounded-lg hover:bg-blue-700 transition disabled:bg-gray-400 disabled:cursor-not-allowed"
      >
        {loading ? "Publicando..." : "Publicar"}
      </button>
    </form>
  );
}
