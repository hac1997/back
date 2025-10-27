'use client';
import { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import { PostService } from "@/services/postService";
import { CommentService } from "@/services/commentService";
import { Post } from "@/types/post";
import { Comment } from "@/types/comment";
import CommentForm from "@/components/comment/CommentForm";
import CommentList from "@/components/comment/CommentList";

export default function PostPage() {
  const params = useParams();
  const router = useRouter();
  const postId = Number(params.id);

  const [post, setPost] = useState<Post | null>(null);
  const [comments, setComments] = useState<Comment[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [isEditing, setIsEditing] = useState(false);
  const [editTitle, setEditTitle] = useState("");
  const [editBody, setEditBody] = useState("");
  const [editTags, setEditTags] = useState("");

  useEffect(() => {
    loadPostData();
  }, [postId]);

  const loadPostData = async () => {
    try {
      setLoading(true);
      setError("");
      const [postData, commentsData] = await Promise.all([
        PostService.getById(postId),
        CommentService.getByPost(postId)
      ]);
      setPost(postData);
      setComments(commentsData);
      setEditTitle(postData.title);
      setEditBody(postData.body);
      setEditTags(postData.tags?.join(", ") || "");
    } catch (err: any) {
      setError("Erro ao carregar post");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!post) return;

    try {
      const updatedPost = await PostService.update(postId, {
        title: editTitle,
        body: editBody,
        tags: editTags.split(",").map(t => t.trim()).filter(t => t),
        userId: 1,
      });
      setPost(updatedPost);
      setIsEditing(false);
    } catch (err: any) {
      alert(err.response?.data?.message || "Erro ao atualizar post");
    }
  };

  const handleDelete = async () => {
    if (!confirm("Tem certeza que deseja excluir este post?")) return;

    try {
      await PostService.delete(postId);
      router.push("/");
    } catch (err: any) {
      alert("Erro ao excluir post");
    }
  };

  const canEdit = (createdAt: string) => {
    const created = new Date(createdAt);
    const now = new Date();
    const hoursDiff = (now.getTime() - created.getTime()) / (1000 * 60 * 60);
    return hoursDiff < 24;
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900 flex items-center justify-center">
        <div className="text-center">
          <div className="inline-block animate-spin rounded-full h-12 w-12 border-b-2 border-emerald-500"></div>
          <p className="mt-4 text-slate-300">Carregando...</p>
        </div>
      </div>
    );
  }

  if (error || !post) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900 flex items-center justify-center">
        <div className="bg-red-500/10 border border-red-500/50 text-red-400 px-6 py-4 rounded-lg">
          {error || "Post não encontrado"}
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900">
      <header className="bg-slate-800/80 backdrop-blur-sm border-b border-slate-700">
        <div className="max-w-4xl mx-auto px-4 py-6">
          <button
            onClick={() => router.push("/feed")}
            className="text-emerald-400 hover:text-emerald-300 mb-2 transition"
          >
            ← Voltar
          </button>
          <h1 className="text-3xl font-bold text-white">Detalhes do Post</h1>
        </div>
      </header>

      <main className="max-w-4xl mx-auto px-4 py-8">
        <div className="bg-slate-800/50 backdrop-blur-sm border border-slate-700 rounded-xl shadow-lg p-6 mb-6">
          {!isEditing ? (
            <>
              <div className="flex justify-between items-start mb-4">
                <div className="flex-1">
                  <h2 className="text-2xl font-bold text-white mb-2">{post.title}</h2>
                  {post.isLocked && (
                    <span className="inline-block px-3 py-1 bg-red-500/20 text-red-400 text-sm rounded-lg border border-red-500/50">
                      🔒 Post Trancado - Comentários desabilitados
                    </span>
                  )}
                </div>
                {canEdit(post.createdAt) && (
                  <div className="flex gap-2">
                    <button
                      onClick={() => setIsEditing(true)}
                      className="text-emerald-400 hover:text-emerald-300 text-sm transition"
                    >
                      Editar
                    </button>
                    <button
                      onClick={handleDelete}
                      className="text-red-400 hover:text-red-300 text-sm transition"
                    >
                      Excluir
                    </button>
                  </div>
                )}
              </div>
              <p className="text-slate-300 mb-4 whitespace-pre-wrap">{post.body}</p>
              <div className="flex flex-wrap gap-2 mb-4">
                {post.tags?.map((tag, i) => (
                  <span
                    key={i}
                    className="bg-emerald-500/10 text-emerald-400 px-3 py-1 rounded-lg text-sm border border-emerald-500/30"
                  >
                    #{tag}
                  </span>
                ))}
              </div>
              <div className="text-sm text-slate-400 border-t border-slate-700 pt-3">
                <p>Por <span className="font-semibold text-slate-300">{post.author.name}</span></p>
                <p className="text-slate-500">{new Date(post.createdAt).toLocaleString('pt-BR')}</p>
              </div>
            </>
          ) : (
            <form onSubmit={handleEdit}>
              <input
                type="text"
                value={editTitle}
                onChange={(e) => setEditTitle(e.target.value)}
                className="w-full bg-slate-900/50 border border-slate-600 text-white p-3 rounded-lg mb-3 text-xl font-bold focus:outline-none focus:ring-2 focus:ring-emerald-500"
                required
              />
              <textarea
                value={editBody}
                onChange={(e) => setEditBody(e.target.value)}
                className="w-full bg-slate-900/50 border border-slate-600 text-white p-3 rounded-lg mb-3 h-48 focus:outline-none focus:ring-2 focus:ring-emerald-500 resize-none"
                required
              />
              <input
                type="text"
                value={editTags}
                onChange={(e) => setEditTags(e.target.value)}
                placeholder="Tags (separadas por vírgula)"
                className="w-full bg-slate-900/50 border border-slate-600 text-white placeholder-slate-500 p-3 rounded-lg mb-4 focus:outline-none focus:ring-2 focus:ring-emerald-500"
              />
              <div className="flex gap-2">
                <button
                  type="submit"
                  className="bg-emerald-600 hover:bg-emerald-700 text-white py-2 px-6 rounded-lg transition"
                >
                  Salvar
                </button>
                <button
                  type="button"
                  onClick={() => setIsEditing(false)}
                  className="bg-slate-700 hover:bg-slate-600 text-white py-2 px-6 rounded-lg transition"
                >
                  Cancelar
                </button>
              </div>
            </form>
          )}
        </div>

        <div className="mb-6">
          <h3 className="text-xl font-semibold text-white mb-4">Comentários ({comments.length})</h3>
          {!post.isLocked ? (
            <CommentForm postId={postId} onCommentCreated={loadPostData} />
          ) : (
            <div className="bg-red-500/10 border border-red-500/50 text-red-400 px-4 py-3 rounded-lg text-center">
              Este post está trancado e não aceita novos comentários.
            </div>
          )}
        </div>

        <CommentList comments={comments} onCommentUpdated={loadPostData} />
      </main>
    </div>
  );
}
