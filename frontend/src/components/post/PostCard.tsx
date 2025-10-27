'use client';
import { Post } from "@/types/post";
import Link from "next/link";

interface PostCardProps {
  post: Post;
}

export default function PostCard({ post }: PostCardProps) {
  return (
    <Link href={`/posts/${post.postId}`}>
      <div className="bg-white rounded-2xl shadow p-5 mb-4 cursor-pointer hover:shadow-lg transition">
        <h2 className="text-xl font-semibold mb-2">{post.title}</h2>
        <p className="text-gray-700 mb-3">{post.body.slice(0, 150)}...</p>
        <div className="flex justify-between text-sm text-gray-500 items-center">
          <div className="flex gap-2 flex-wrap">
            {post.tags?.map((tag, i) => (
              <span
                key={i}
                className="bg-blue-100 text-blue-800 px-2 py-1 rounded-lg"
              >
                #{tag}
              </span>
            ))}
          </div>
          <div className="text-right">
            <p className="text-xs">Por {post.author.name}</p>
            <span>{new Date(post.createdAt).toLocaleDateString()}</span>
          </div>
        </div>
      </div>
    </Link>
  );
}
