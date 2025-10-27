--
-- Complete data initialization for TPJ Forum System
-- Password for all users: 'Password123!'
-- BCrypt hash: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
--

-- ====================================
-- Users
-- ====================================

INSERT INTO users (user_id, name, email, password, is_email_verified, email_verification_code, status, suspended_until, suspension_reason)
SELECT 1, 'Alice Johnson', 'alice@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true, NULL, 'ACTIVE', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 1);

INSERT INTO users (user_id, name, email, password, is_email_verified, email_verification_code, status, suspended_until, suspension_reason)
SELECT 2, 'Bob Williams', 'bob@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true, NULL, 'ACTIVE', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 2);

INSERT INTO users (user_id, name, email, password, is_email_verified, email_verification_code, status, suspended_until, suspension_reason)
SELECT 3, 'Charlie Brown', 'charlie@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true, NULL, 'ACTIVE', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 3);

INSERT INTO users (user_id, name, email, password, is_email_verified, email_verification_code, status, suspended_until, suspension_reason)
SELECT 4, 'Diana Prince', 'diana@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true, NULL, 'ACTIVE', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 4);

INSERT INTO users (user_id, name, email, password, is_email_verified, email_verification_code, status, suspended_until, suspension_reason)
SELECT 5, 'Admin User', 'admin@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true, NULL, 'ACTIVE', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 5);

-- ====================================
-- User Roles
-- ====================================

INSERT INTO user_role (user_id, role)
SELECT 1, 'ROLE_USER'
WHERE NOT EXISTS (SELECT 1 FROM user_role WHERE user_id = 1 AND role = 'ROLE_USER');

INSERT INTO user_role (user_id, role)
SELECT 2, 'ROLE_USER'
WHERE NOT EXISTS (SELECT 1 FROM user_role WHERE user_id = 2 AND role = 'ROLE_USER');

INSERT INTO user_role (user_id, role)
SELECT 3, 'ROLE_USER'
WHERE NOT EXISTS (SELECT 1 FROM user_role WHERE user_id = 3 AND role = 'ROLE_USER');

INSERT INTO user_role (user_id, role)
SELECT 4, 'ROLE_USER'
WHERE NOT EXISTS (SELECT 1 FROM user_role WHERE user_id = 4 AND role = 'ROLE_USER');

INSERT INTO user_role (user_id, role)
SELECT 4, 'ROLE_MODERATOR'
WHERE NOT EXISTS (SELECT 1 FROM user_role WHERE user_id = 4 AND role = 'ROLE_MODERATOR');

INSERT INTO user_role (user_id, role)
SELECT 5, 'ROLE_USER'
WHERE NOT EXISTS (SELECT 1 FROM user_role WHERE user_id = 5 AND role = 'ROLE_USER');

INSERT INTO user_role (user_id, role)
SELECT 5, 'ROLE_MODERATOR'
WHERE NOT EXISTS (SELECT 1 FROM user_role WHERE user_id = 5 AND role = 'ROLE_MODERATOR');

INSERT INTO user_role (user_id, role)
SELECT 5, 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM user_role WHERE user_id = 5 AND role = 'ROLE_ADMIN');

-- ====================================
-- Tags
-- ====================================

INSERT INTO tags (tag_id, name)
SELECT 1, 'java'
WHERE NOT EXISTS (SELECT 1 FROM tags WHERE tag_id = 1);

INSERT INTO tags (tag_id, name)
SELECT 2, 'spring-boot'
WHERE NOT EXISTS (SELECT 1 FROM tags WHERE tag_id = 2);

INSERT INTO tags (tag_id, name)
SELECT 3, 'jpa'
WHERE NOT EXISTS (SELECT 1 FROM tags WHERE tag_id = 3);

INSERT INTO tags (tag_id, name)
SELECT 4, 'microservices'
WHERE NOT EXISTS (SELECT 1 FROM tags WHERE tag_id = 4);

INSERT INTO tags (tag_id, name)
SELECT 5, 'docker'
WHERE NOT EXISTS (SELECT 1 FROM tags WHERE tag_id = 5);

INSERT INTO tags (tag_id, name)
SELECT 6, 'security'
WHERE NOT EXISTS (SELECT 1 FROM tags WHERE tag_id = 6);

INSERT INTO tags (tag_id, name)
SELECT 7, 'performance'
WHERE NOT EXISTS (SELECT 1 FROM tags WHERE tag_id = 7);

-- ====================================
-- Posts
-- ====================================

INSERT INTO posts (post_id, title, body, visible, is_locked, answer_id, created_at, user_id)
SELECT 101, 'Spring Boot Best Practices', 'What are the best practices for building production-ready Spring Boot applications? Looking for insights on configuration, security, and performance optimization.', true, false, NULL, CURRENT_TIMESTAMP, 1
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE post_id = 101);

INSERT INTO posts (post_id, title, body, visible, is_locked, answer_id, created_at, user_id)
SELECT 102, 'JPA N+1 Problem Solution', 'How can I solve the N+1 query problem in JPA? I have a one-to-many relationship and it is generating too many queries.', true, false, NULL, CURRENT_TIMESTAMP, 2
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE post_id = 102);

INSERT INTO posts (post_id, title, body, visible, is_locked, answer_id, created_at, user_id)
SELECT 103, 'Docker Multi-stage Builds', 'Best practices for Docker multi-stage builds to reduce image size for Java applications. Share your experiences!', true, false, NULL, CURRENT_TIMESTAMP, 3
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE post_id = 103);

INSERT INTO posts (post_id, title, body, visible, is_locked, answer_id, created_at, user_id)
SELECT 104, 'Microservices Communication Patterns', 'What are the pros and cons of synchronous vs asynchronous communication in microservices architecture?', true, false, NULL, CURRENT_TIMESTAMP, 1
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE post_id = 104);

INSERT INTO posts (post_id, title, body, visible, is_locked, answer_id, created_at, user_id)
SELECT 105, 'Spring Security JWT Implementation', 'How to properly implement JWT authentication with Spring Security? Looking for a complete guide with refresh tokens.', true, false, NULL, CURRENT_TIMESTAMP, 4
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE post_id = 105);

-- ====================================
-- Post Categories (ElementCollection)
-- ====================================

INSERT INTO posts_categories (post_post_id, categories)
SELECT 101, 'WEB_DEVELOPMENT'
WHERE NOT EXISTS (SELECT 1 FROM posts_categories WHERE post_post_id = 101 AND categories = 'WEB_DEVELOPMENT');

INSERT INTO posts_categories (post_post_id, categories)
SELECT 102, 'SOFTWARE'
WHERE NOT EXISTS (SELECT 1 FROM posts_categories WHERE post_post_id = 102 AND categories = 'SOFTWARE');

INSERT INTO posts_categories (post_post_id, categories)
SELECT 103, 'DEVOPS'
WHERE NOT EXISTS (SELECT 1 FROM posts_categories WHERE post_post_id = 103 AND categories = 'DEVOPS');

INSERT INTO posts_categories (post_post_id, categories)
SELECT 104, 'SOFTWARE'
WHERE NOT EXISTS (SELECT 1 FROM posts_categories WHERE post_post_id = 104 AND categories = 'SOFTWARE');

INSERT INTO posts_categories (post_post_id, categories)
SELECT 105, 'SECURITY'
WHERE NOT EXISTS (SELECT 1 FROM posts_categories WHERE post_post_id = 105 AND categories = 'SECURITY');

-- ====================================
-- Post Tags (ManyToMany)
-- ====================================

INSERT INTO post_tags (post_id, tag_id)
SELECT 101, 2
WHERE NOT EXISTS (SELECT 1 FROM post_tags WHERE post_id = 101 AND tag_id = 2);

INSERT INTO post_tags (post_id, tag_id)
SELECT 101, 1
WHERE NOT EXISTS (SELECT 1 FROM post_tags WHERE post_id = 101 AND tag_id = 1);

INSERT INTO post_tags (post_id, tag_id)
SELECT 102, 3
WHERE NOT EXISTS (SELECT 1 FROM post_tags WHERE post_id = 102 AND tag_id = 3);

INSERT INTO post_tags (post_id, tag_id)
SELECT 102, 7
WHERE NOT EXISTS (SELECT 1 FROM post_tags WHERE post_id = 102 AND tag_id = 7);

INSERT INTO post_tags (post_id, tag_id)
SELECT 103, 5
WHERE NOT EXISTS (SELECT 1 FROM post_tags WHERE post_id = 103 AND tag_id = 5);

INSERT INTO post_tags (post_id, tag_id)
SELECT 104, 4
WHERE NOT EXISTS (SELECT 1 FROM post_tags WHERE post_id = 104 AND tag_id = 4);

INSERT INTO post_tags (post_id, tag_id)
SELECT 105, 2
WHERE NOT EXISTS (SELECT 1 FROM post_tags WHERE post_id = 105 AND tag_id = 2);

INSERT INTO post_tags (post_id, tag_id)
SELECT 105, 6
WHERE NOT EXISTS (SELECT 1 FROM post_tags WHERE post_id = 105 AND tag_id = 6);

-- ====================================
-- Comments
-- ====================================

INSERT INTO comments (comment_id, body, visible, pinned, created_at, post_id, user_id)
SELECT 501, 'I recommend using Spring Boot DevTools for faster development cycles and proper externalized configuration with application.yml files.', true, false, CURRENT_TIMESTAMP, 101, 2
WHERE NOT EXISTS (SELECT 1 FROM comments WHERE comment_id = 501);

INSERT INTO comments (comment_id, body, visible, pinned, created_at, post_id, user_id)
SELECT 502, 'Use @EntityGraph or JOIN FETCH to solve the N+1 problem. EntityGraph is more flexible for complex scenarios.', true, true, CURRENT_TIMESTAMP, 102, 4
WHERE NOT EXISTS (SELECT 1 FROM comments WHERE comment_id = 502);

INSERT INTO comments (comment_id, body, visible, pinned, created_at, post_id, user_id)
SELECT 503, 'Great question! I also struggled with this. Batch fetching is another option worth considering.', true, false, CURRENT_TIMESTAMP, 102, 1
WHERE NOT EXISTS (SELECT 1 FROM comments WHERE comment_id = 503);

INSERT INTO comments (comment_id, body, visible, pinned, created_at, post_id, user_id)
SELECT 504, 'Multi-stage builds are essential. First stage for build with Maven/Gradle, second stage with lightweight JRE.', true, false, CURRENT_TIMESTAMP, 103, 2
WHERE NOT EXISTS (SELECT 1 FROM comments WHERE comment_id = 504);

INSERT INTO comments (comment_id, body, visible, pinned, created_at, post_id, user_id)
SELECT 505, 'For microservices, async communication with message queues provides better decoupling but adds complexity.', true, false, CURRENT_TIMESTAMP, 104, 3
WHERE NOT EXISTS (SELECT 1 FROM comments WHERE comment_id = 505);

-- ====================================
-- Votes
-- ====================================

INSERT INTO votes (vote_id, vote, user_id, post_id)
SELECT 1001, true, 2, 101
WHERE NOT EXISTS (SELECT 1 FROM votes WHERE vote_id = 1001);

INSERT INTO votes (vote_id, vote, user_id, post_id)
SELECT 1002, true, 3, 101
WHERE NOT EXISTS (SELECT 1 FROM votes WHERE vote_id = 1002);

INSERT INTO votes (vote_id, vote, user_id, post_id)
SELECT 1003, true, 4, 102
WHERE NOT EXISTS (SELECT 1 FROM votes WHERE vote_id = 1003);

INSERT INTO votes (vote_id, vote, user_id, post_id)
SELECT 1004, true, 1, 103
WHERE NOT EXISTS (SELECT 1 FROM votes WHERE vote_id = 1004);

INSERT INTO votes (vote_id, vote, user_id, post_id)
SELECT 1005, false, 3, 104
WHERE NOT EXISTS (SELECT 1 FROM votes WHERE vote_id = 1005);

-- ====================================
-- Reports (Example: one pending report)
-- ====================================

INSERT INTO reports (report_id, report_type, status, reason, reporter_id, reported_user_id, reported_post_id, reported_comment_id, reviewed_by, review_notes, created_at, reviewed_at)
SELECT 2001, 'POST', 'PENDING', 'This post contains spam and irrelevant content.', 3, NULL, 104, NULL, NULL, NULL, CURRENT_TIMESTAMP, NULL
WHERE NOT EXISTS (SELECT 1 FROM reports WHERE report_id = 2001);

-- ====================================
-- Discussions (Example: one open discussion)
-- ====================================

INSERT INTO discussions (discussion_id, discussion_type, status, description, report_id, target_user_id, target_post_id, target_comment_id, created_by, created_at, closed_at, resolution)
SELECT 3001, 'CONTENT_REMOVAL', 'OPEN', 'Discussion about removing potentially spam post. Community input needed.', 2001, NULL, 104, NULL, 5, CURRENT_TIMESTAMP, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM discussions WHERE discussion_id = 3001);

-- ====================================
-- Discussion Votes
-- ====================================

INSERT INTO discussion_votes (vote_id, discussion_id, moderator_id, in_favor, justification, voted_at)
SELECT 4001, 3001, 4, true, 'The post seems to lack technical depth and appears promotional.', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM discussion_votes WHERE vote_id = 4001);

-- ====================================
-- Moderation Logs
-- ====================================

INSERT INTO moderation_logs (log_id, action, moderator_id, target_user_id, target_post_id, target_comment_id, related_discussion_id, notes, performed_at)
SELECT 5001, 'HIDE_POST', 4, NULL, 104, NULL, 3001, 'Temporarily hidden pending discussion resolution.', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM moderation_logs WHERE log_id = 5001);

-- ====================================
-- Appeals (Example: one pending appeal)
-- ====================================

INSERT INTO appeals (appeal_id, user_id, status, reason, related_discussion_id, reviewed_by, review_notes, created_at, reviewed_at)
SELECT 6001, 1, 'PENDING', 'I believe my post was removed unfairly. It was relevant to the discussion topic.', 3001, NULL, NULL, CURRENT_TIMESTAMP, NULL
WHERE NOT EXISTS (SELECT 1 FROM appeals WHERE appeal_id = 6001);
