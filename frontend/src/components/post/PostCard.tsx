'use client';
import { Post } from "@/types/post";
import Link from "next/link";

interface PostCardProps {
  post: Post;
}

export default function PostCard({ post }: PostCardProps) {
  return (
    <Link href={`/posts/${post.postId}`}>
      <div className="bg-slate-800/50 backdrop-blur-sm border border-slate-700 rounded-xl shadow-lg p-6 mb-4 cursor-pointer hover:border-emerald-500/50 hover:shadow-emerald-500/10 transition-all">
        <div className="flex items-start justify-between mb-3">
          <h2 className="text-xl font-semibold text-white flex-1">{post.title}</h2>
          {post.isLocked && (
            <span className="ml-3 px-2 py-1 bg-red-500/20 text-red-400 text-xs rounded-lg border border-red-500/50">
              🔒 Trancado
            </span>
          )}
        </div>

        <p className="text-slate-300 mb-4 line-clamp-3">{post.body}</p>

        <div className="flex justify-between items-center text-sm">
          <div className="flex gap-2 flex-wrap">
            {post.tags?.map((tag, i) => (
              <span
                key={i}
                className="bg-emerald-500/10 text-emerald-400 px-3 py-1 rounded-lg border border-emerald-500/30"
              >
                #{tag}
              </span>
            ))}
          </div>
          <div className="text-right">
            <p className="text-slate-400 text-xs mb-1">Por {post.author.name}</p>
            <span className="text-slate-500 text-xs">
              {new Date(post.createdAt).toLocaleDateString('pt-BR')}
            </span>
          </div>
        </div>
      </div>
    </Link>
  );
}
