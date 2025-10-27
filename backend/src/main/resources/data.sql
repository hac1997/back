--
-- Data for table 'users'
-- Corresponds to the User.java entity
-- Fields: user_id, name, email, password, is_email_verified, email_verification_code, status
--

INSERT INTO users (user_id, name, email, password, is_email_verified, email_verification_code, status)
SELECT 1, 'Alice Johnson', 'alice.j@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true, NULL, 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 1);

INSERT INTO users (user_id, name, email, password, is_email_verified, email_verification_code, status)
SELECT 2, 'Bob Williams', 'bob.w@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true, NULL, 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 2);

INSERT INTO users (user_id, name, email, password, is_email_verified, email_verification_code, status)
SELECT 3, 'Charlie Brown', 'charlie.b@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', false, 'VERIFY123', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 3);

INSERT INTO users (user_id, name, email, password, is_email_verified, email_verification_code, status)
SELECT 4, 'Diana Prince', 'diana.p@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true, NULL, 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 4);

INSERT INTO users (user_id, name, email, password, is_email_verified, email_verification_code, status)
SELECT 5, 'Admin User', 'admin@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true, NULL, 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 5);

--
-- Data for table 'user_roles'
-- Associating users with roles
--

INSERT INTO user_roles (user_id, roles)
SELECT 1, 'USER'
WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE user_id = 1 AND roles = 'USER');

INSERT INTO user_roles (user_id, roles)
SELECT 2, 'USER'
WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE user_id = 2 AND roles = 'USER');

INSERT INTO user_roles (user_id, roles)
SELECT 3, 'USER'
WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE user_id = 3 AND roles = 'USER');

INSERT INTO user_roles (user_id, roles)
SELECT 4, 'USER'
WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE user_id = 4 AND roles = 'USER');

INSERT INTO user_roles (user_id, roles)
SELECT 4, 'MODERATOR'
WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE user_id = 4 AND roles = 'MODERATOR');

INSERT INTO user_roles (user_id, roles)
SELECT 5, 'USER'
WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE user_id = 5 AND roles = 'USER');

INSERT INTO user_roles (user_id, roles)
SELECT 5, 'MODERATOR'
WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE user_id = 5 AND roles = 'MODERATOR');

INSERT INTO user_roles (user_id, roles)
SELECT 5, 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE user_id = 5 AND roles = 'ADMIN');

--
-- Data for table 'posts'
-- Corresponds to the Post.java entity
-- Fields: post_id, title, body, visible, is_locked, created_at, user_id
-- Note: 'tags' is an ElementCollection and handled separately by JPA (e.g., in a 'posts_tags' table)
--

INSERT INTO posts (post_id, title, body, visible, is_locked, created_at, user_id)
SELECT 101, 'Spring Boot Tips', 'Here are some quick tips for optimizing your Spring Boot application startup time. Use lazy loading, configure proper connection pools, and leverage Spring Boot DevTools for faster development cycles.', true, false, CURRENT_TIMESTAMP, 1
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE post_id = 101);

INSERT INTO posts (post_id, title, body, visible, is_locked, created_at, user_id)
SELECT 102, 'The Future of Java', 'Discussing Project Loom and other exciting features coming to the Java ecosystem. Virtual threads will revolutionize concurrent programming in Java, making it easier to write scalable applications.', true, false, CURRENT_TIMESTAMP, 2
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE post_id = 102);

INSERT INTO posts (post_id, title, body, visible, is_locked, created_at, user_id)
SELECT 103, 'JPA Entity Mapping Basics', 'A guide to mapping basic fields, relationships, and auditing listeners. Learn about @OneToMany, @ManyToOne, @ManyToMany relationships and how to optimize your database queries with proper fetch strategies.', true, false, CURRENT_TIMESTAMP, 1
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE post_id = 103);

INSERT INTO posts (post_id, title, body, visible, is_locked, created_at, user_id)
SELECT 104, 'Microservices Architecture Patterns', 'Exploring common patterns in microservices architecture including API Gateway, Circuit Breaker, and Service Discovery. Learn how to build resilient distributed systems.', true, false, CURRENT_TIMESTAMP, 2
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE post_id = 104);

INSERT INTO posts (post_id, title, body, visible, is_locked, created_at, user_id)
SELECT 105, 'Docker Best Practices', 'Essential Docker tips for production environments. Multi-stage builds, layer caching, and security considerations for containerized applications.', true, false, CURRENT_TIMESTAMP, 3
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE post_id = 105);

--
-- Data for table 'posts_tags' (JPA ElementCollection for Post.tags)
-- Fields: post_post_id, tags
--

INSERT INTO posts_tags (post_post_id, tags)
SELECT 101, 'spring-boot'
WHERE NOT EXISTS (SELECT 1 FROM posts_tags WHERE post_post_id = 101 AND tags = 'spring-boot');

INSERT INTO posts_tags (post_post_id, tags)
SELECT 101, 'performance'
WHERE NOT EXISTS (SELECT 1 FROM posts_tags WHERE post_post_id = 101 AND tags = 'performance');

INSERT INTO posts_tags (post_post_id, tags)
SELECT 101, 'java'
WHERE NOT EXISTS (SELECT 1 FROM posts_tags WHERE post_post_id = 101 AND tags = 'java');

INSERT INTO posts_tags (post_post_id, tags)
SELECT 102, 'java'
WHERE NOT EXISTS (SELECT 1 FROM posts_tags WHERE post_post_id = 102 AND tags = 'java');

INSERT INTO posts_tags (post_post_id, tags)
SELECT 102, 'project-loom'
WHERE NOT EXISTS (SELECT 1 FROM posts_tags WHERE post_post_id = 102 AND tags = 'project-loom');

INSERT INTO posts_tags (post_post_id, tags)
SELECT 102, 'concurrency'
WHERE NOT EXISTS (SELECT 1 FROM posts_tags WHERE post_post_id = 102 AND tags = 'concurrency');

INSERT INTO posts_tags (post_post_id, tags)
SELECT 103, 'jpa'
WHERE NOT EXISTS (SELECT 1 FROM posts_tags WHERE post_post_id = 103 AND tags = 'jpa');

INSERT INTO posts_tags (post_post_id, tags)
SELECT 103, 'hibernate'
WHERE NOT EXISTS (SELECT 1 FROM posts_tags WHERE post_post_id = 103 AND tags = 'hibernate');

INSERT INTO posts_tags (post_post_id, tags)
SELECT 103, 'database'
WHERE NOT EXISTS (SELECT 1 FROM posts_tags WHERE post_post_id = 103 AND tags = 'database');

INSERT INTO posts_tags (post_post_id, tags)
SELECT 104, 'microservices'
WHERE NOT EXISTS (SELECT 1 FROM posts_tags WHERE post_post_id = 104 AND tags = 'microservices');

INSERT INTO posts_tags (post_post_id, tags)
SELECT 104, 'architecture'
WHERE NOT EXISTS (SELECT 1 FROM posts_tags WHERE post_post_id = 104 AND tags = 'architecture');

INSERT INTO posts_tags (post_post_id, tags)
SELECT 104, 'distributed-systems'
WHERE NOT EXISTS (SELECT 1 FROM posts_tags WHERE post_post_id = 104 AND tags = 'distributed-systems');

INSERT INTO posts_tags (post_post_id, tags)
SELECT 105, 'docker'
WHERE NOT EXISTS (SELECT 1 FROM posts_tags WHERE post_post_id = 105 AND tags = 'docker');

INSERT INTO posts_tags (post_post_id, tags)
SELECT 105, 'containers'
WHERE NOT EXISTS (SELECT 1 FROM posts_tags WHERE post_post_id = 105 AND tags = 'containers');

INSERT INTO posts_tags (post_post_id, tags)
SELECT 105, 'devops'
WHERE NOT EXISTS (SELECT 1 FROM posts_tags WHERE post_post_id = 105 AND tags = 'devops');


--
-- Data for table 'comments'
-- Corresponds to the Comment.java entity
-- Fields: comment_id, body, visible, pinned, created_at, post_id, user_id
--

INSERT INTO comments (comment_id, body, visible, pinned, created_at, post_id, user_id)
SELECT 501, 'Great tips, especially the lazy loading one!', true, false, CURRENT_TIMESTAMP, 101, 2
WHERE NOT EXISTS (SELECT 1 FROM comments WHERE comment_id = 501);

INSERT INTO comments (comment_id, body, visible, pinned, created_at, post_id, user_id)
SELECT 502, 'I agree, Spring Boot is fantastic for quick setup.', true, false, CURRENT_TIMESTAMP, 101, 3
WHERE NOT EXISTS (SELECT 1 FROM comments WHERE comment_id = 502);

INSERT INTO comments (comment_id, body, visible, pinned, created_at, post_id, user_id)
SELECT 503, 'Loom looks incredibly promising. Cannot wait for it to be mainstream.', true, false, CURRENT_TIMESTAMP, 102, 1
WHERE NOT EXISTS (SELECT 1 FROM comments WHERE comment_id = 503);

INSERT INTO comments (comment_id, body, visible, pinned, created_at, post_id, user_id)
SELECT 504, 'Very clear explanation of the @JoinColumn annotation.', true, true, CURRENT_TIMESTAMP, 103, 2
WHERE NOT EXISTS (SELECT 1 FROM comments WHERE comment_id = 504);

INSERT INTO comments (comment_id, body, visible, pinned, created_at, post_id, user_id)
SELECT 505, 'This is exactly what I needed. Thank you for sharing!', true, false, CURRENT_TIMESTAMP, 103, 4
WHERE NOT EXISTS (SELECT 1 FROM comments WHERE comment_id = 505);

INSERT INTO comments (comment_id, body, visible, pinned, created_at, post_id, user_id)
SELECT 506, 'API Gateway pattern is crucial for microservices. Great post!', true, false, CURRENT_TIMESTAMP, 104, 1
WHERE NOT EXISTS (SELECT 1 FROM comments WHERE comment_id = 506);

INSERT INTO comments (comment_id, body, visible, pinned, created_at, post_id, user_id)
SELECT 507, 'Multi-stage builds are a game changer. Reduced my image size by 70%!', true, false, CURRENT_TIMESTAMP, 105, 2
WHERE NOT EXISTS (SELECT 1 FROM comments WHERE comment_id = 507);