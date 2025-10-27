--
-- Data for table 'users'
-- Corresponds to the User.java entity
-- Fields: user_id, name, email, password, is_email_verified, email_verification_code
--

INSERT INTO users (user_id, name, email, password, is_email_verified, email_verification_code)
SELECT 1, 'Alice Johnson', 'alice.j@example.com', 'SecurePass123!', true, NULL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 1);

INSERT INTO users (user_id, name, email, password, is_email_verified, email_verification_code)
SELECT 2, 'Bob Williams', 'bob.w@example.com', 'MyPassword456!', true, NULL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 2);

INSERT INTO users (user_id, name, email, password, is_email_verified, email_verification_code)
SELECT 3, 'Charlie Brown', 'charlie.b@example.com', 'TestPassword789!', false, 'VERIFY123'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 3);

--
-- Data for table 'posts'
-- Corresponds to the Post.java entity
-- Fields: post_id, title, body, visible, created_at, user_id
-- Note: 'tags' is an ElementCollection and handled separately by JPA (e.g., in a 'posts_tags' table)
--

INSERT INTO posts (post_id, title, body, visible, created_at, user_id)
SELECT 101, 'Spring Boot Tips', 'Here are some quick tips for optimizing your Spring Boot application startup time.', true, CURRENT_TIMESTAMP, 1
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE post_id = 101);

INSERT INTO posts (post_id, title, body, visible, created_at, user_id)
SELECT 102, 'The Future of Java', 'Discussing Project Loom and other exciting features coming to the Java ecosystem.', true, CURRENT_TIMESTAMP, 2
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE post_id = 102);

INSERT INTO posts (post_id, title, body, visible, created_at, user_id)
SELECT 103, 'JPA Entity Mapping Basics', 'A guide to mapping basic fields, relationships, and auditing listeners.', true, CURRENT_TIMESTAMP, 1
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE post_id = 103);

--
-- Data for table 'posts_tags' (JPA ElementCollection for Post.tags)
-- Fields: Post_postId, tags
--


--
-- Data for table 'comments'
-- Corresponds to the Comment.java entity
-- Fields: comment_id, body, visible, created_at, post_id, user_id
--

INSERT INTO comments (comment_id, body, visible, created_at, post_id, user_id)
SELECT 501, 'Great tips, especially the lazy loading one!', true, CURRENT_TIMESTAMP, 101, 2
WHERE NOT EXISTS (SELECT 1 FROM comments WHERE comment_id = 501);

INSERT INTO comments (comment_id, body, visible, created_at, post_id, user_id)
SELECT 502, 'I agree, Spring Boot is fantastic for quick setup.', true, CURRENT_TIMESTAMP, 101, 3
WHERE NOT EXISTS (SELECT 1 FROM comments WHERE comment_id = 502);

INSERT INTO comments (comment_id, body, visible, created_at, post_id, user_id)
SELECT 503, 'Loom looks incredibly promising. Cannot wait for it to be mainstream.', true, CURRENT_TIMESTAMP, 102, 1
WHERE NOT EXISTS (SELECT 1 FROM comments WHERE comment_id = 503);

INSERT INTO comments (comment_id, body, visible, created_at, post_id, user_id)
SELECT 504, 'Very clear explanation of the @JoinColumn annotation.', true, CURRENT_TIMESTAMP, 103, 2
WHERE NOT EXISTS (SELECT 1 FROM comments WHERE comment_id = 504);