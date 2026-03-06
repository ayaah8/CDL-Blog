package com.cdl.model;

import java.time.LocalDateTime;

public class Comment {
    private int id;
    private String content;
    private int articleId;
    private int authorId;
    private String authorUsername;
    private String authorAvatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Comment() {}

    public Comment(String content, int articleId, int authorId) {
        this.content = content;
        this.articleId = articleId;
        this.authorId = authorId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int getArticleId() { return articleId; }
    public void setArticleId(int articleId) { this.articleId = articleId; }
    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }
    public String getAuthorUsername() { return authorUsername; }
    public void setAuthorUsername(String authorUsername) { this.authorUsername = authorUsername; }
    public String getAuthorAvatar() { return authorAvatar; }
    public void setAuthorAvatar(String authorAvatar) { this.authorAvatar = authorAvatar; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Convertit LocalDateTime → java.util.Date pour JSTL fmt:formatDate
    public java.util.Date getCreatedAtAsDate() {
        if (createdAt == null) return null;
        return java.util.Date.from(
            createdAt.atZone(java.time.ZoneId.systemDefault()).toInstant());
    }
}