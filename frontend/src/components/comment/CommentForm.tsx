'use client';
import { useState } from "react";
import { CommentService } from "@/services/commentService";

interface CommentFormProps {
  postId: number;
  onCommentCreated?: () => void;
}

export default function CommentForm({ postId, onCommentCreated }: CommentFormProps) {
  const [body, setBody] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      await CommentService.create(postId, {
        body,
        userId: 1,
      });
      setBody("");
      onCommentCreated?.();
    } catch (err: any) {
      setError(err.response?.data?.message || "Erro ao criar comentário");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="bg-white p-4 rounded-lg shadow">
      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-3 py-2 rounded mb-3 text-sm">
          {error}
        </div>
      )}
      <textarea
        placeholder="Escreva um comentário..."
        value={body}
        onChange={(e) => setBody(e.target.value)}
        className="w-full border border-gray-300 p-3 rounded-lg mb-3 h-24 focus:outline-none focus:ring-2 focus:ring-blue-500"
        required
        disabled={loading}
      />
      <button
        type="submit"
        disabled={loading}
        className="bg-gray-800 text-white px-6 py-2 rounded-lg hover:bg-gray-900 transition disabled:bg-gray-400 disabled:cursor-not-allowed"
      >
        {loading ? "Enviando..." : "Comentar"}
      </button>
    </form>
  );
}
