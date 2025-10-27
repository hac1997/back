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
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <div className="inline-block animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
          <p className="mt-4 text-gray-600">Carregando...</p>
        </div>
      </div>
    );
  }

  if (error || !post) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="bg-red-100 border border-red-400 text-red-700 px-6 py-4 rounded">
          {error || "Post não encontrado"}
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <header className="bg-white shadow-sm">
        <div className="max-w-4xl mx-auto px-4 py-6">
          <button
            onClick={() => router.push("/")}
            className="text-blue-600 hover:text-blue-800 mb-2"
          >
            ← Voltar
          </button>
          <h1 className="text-3xl font-bold text-gray-900">Detalhes do Post</h1>
        </div>
      </header>

      <main className="max-w-4xl mx-auto px-4 py-8">
        <div className="bg-white rounded-2xl shadow p-6 mb-6">
          {!isEditing ? (
            <>
              <div className="flex justify-between items-start mb-4">
                <h2 className="text-2xl font-bold text-gray-900">{post.title}</h2>
                {canEdit(post.createdAt) && (
                  <div className="flex gap-2">
                    <button
                      onClick={() => setIsEditing(true)}
                      className="text-blue-600 hover:text-blue-800 text-sm"
                    >
                      Editar
                    </button>
                    <button
                      onClick={handleDelete}
                      className="text-red-600 hover:text-red-800 text-sm"
                    >
                      Excluir
                    </button>
                  </div>
                )}
              </div>
              <p className="text-gray-700 mb-4 whitespace-pre-wrap">{post.body}</p>
              <div className="flex flex-wrap gap-2 mb-4">
                {post.tags?.map((tag, i) => (
                  <span
                    key={i}
                    className="bg-blue-100 text-blue-800 px-3 py-1 rounded-lg text-sm"
                  >
                    #{tag}
                  </span>
                ))}
              </div>
              <div className="text-sm text-gray-500 border-t pt-3">
                <p>Por <span className="font-semibold">{post.author.name}</span></p>
                <p>{new Date(post.createdAt).toLocaleString()}</p>
              </div>
            </>
          ) : (
            <form onSubmit={handleEdit}>
              <input
                type="text"
                value={editTitle}
                onChange={(e) => setEditTitle(e.target.value)}
                className="w-full border border-gray-300 p-3 rounded-lg mb-3 text-xl font-bold"
                required
              />
              <textarea
                value={editBody}
                onChange={(e) => setEditBody(e.target.value)}
                className="w-full border border-gray-300 p-3 rounded-lg mb-3 h-48"
                required
              />
              <input
                type="text"
                value={editTags}
                onChange={(e) => setEditTags(e.target.value)}
                placeholder="Tags (separadas por vírgula)"
                className="w-full border border-gray-300 p-3 rounded-lg mb-4"
              />
              <div className="flex gap-2">
                <button
                  type="submit"
                  className="bg-blue-600 text-white py-2 px-6 rounded-lg hover:bg-blue-700"
                >
                  Salvar
                </button>
                <button
                  type="button"
                  onClick={() => setIsEditing(false)}
                  className="bg-gray-300 text-gray-700 py-2 px-6 rounded-lg hover:bg-gray-400"
                >
                  Cancelar
                </button>
              </div>
            </form>
          )}
        </div>

        <div className="mb-6">
          <h3 className="text-xl font-semibold mb-4">Comentários ({comments.length})</h3>
          <CommentForm postId={postId} onCommentCreated={loadPostData} />
        </div>

        <CommentList comments={comments} onCommentUpdated={loadPostData} />
      </main>
    </div>
  );
}
