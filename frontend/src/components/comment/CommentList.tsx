'use client';
import { useState } from "react";
import { CommentService } from "@/services/commentService";
import { Comment } from "@/types/comment";

interface CommentListProps {
  comments: Comment[];
  onCommentUpdated?: () => void;
}

export default function CommentList({ comments, onCommentUpdated }: CommentListProps) {
  const [editingId, setEditingId] = useState<number | null>(null);
  const [editBody, setEditBody] = useState("");

  const canEdit = (createdAt: string) => {
    const created = new Date(createdAt);
    const now = new Date();
    const hoursDiff = (now.getTime() - created.getTime()) / (1000 * 60 * 60);
    return hoursDiff < 24;
  };

  const handleEdit = (comment: Comment) => {
    setEditingId(comment.commentId);
    setEditBody(comment.body);
  };

  const handleSave = async (commentId: number) => {
    try {
      await CommentService.update(commentId, {
        body: editBody,
        userId: 1,
      });
      setEditingId(null);
      onCommentUpdated?.();
    } catch (err: any) {
      alert(err.response?.data?.message || "Erro ao atualizar comentário");
    }
  };

  const handleDelete = async (commentId: number) => {
    if (!confirm("Tem certeza que deseja excluir este comentário?")) return;

    try {
      await CommentService.delete(commentId);
      onCommentUpdated?.();
    } catch (err: any) {
      alert("Erro ao excluir comentário");
    }
  };

  if (comments.length === 0) {
    return (
      <div className="text-center py-8 bg-gray-50 rounded-lg">
        <p className="text-gray-600">Nenhum comentário ainda. Seja o primeiro a comentar!</p>
      </div>
    );
  }

  return (
    <div className="space-y-4">
      {comments.map((comment) => (
        <div key={comment.commentId} className="bg-white p-4 rounded-lg shadow">
          {editingId === comment.commentId ? (
            <div>
              <textarea
                value={editBody}
                onChange={(e) => setEditBody(e.target.value)}
                className="w-full border border-gray-300 p-3 rounded-lg mb-3 h-24"
              />
              <div className="flex gap-2">
                <button
                  onClick={() => handleSave(comment.commentId)}
                  className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 text-sm"
                >
                  Salvar
                </button>
                <button
                  onClick={() => setEditingId(null)}
                  className="bg-gray-300 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-400 text-sm"
                >
                  Cancelar
                </button>
              </div>
            </div>
          ) : (
            <>
              <div className="flex justify-between items-start">
                <p className="text-gray-800 flex-1 whitespace-pre-wrap">{comment.body}</p>
                {canEdit(comment.createdAt) && (
                  <div className="flex gap-2 ml-4">
                    <button
                      onClick={() => handleEdit(comment)}
                      className="text-blue-600 hover:text-blue-800 text-sm"
                    >
                      Editar
                    </button>
                    <button
                      onClick={() => handleDelete(comment.commentId)}
                      className="text-red-600 hover:text-red-800 text-sm"
                    >
                      Excluir
                    </button>
                  </div>
                )}
              </div>
              <div className="text-sm text-gray-500 mt-2">
                <span className="font-semibold">{comment.author.name}</span> • {new Date(comment.createdAt).toLocaleString()}
              </div>
            </>
          )}
        </div>
      ))}
    </div>
  );
}
