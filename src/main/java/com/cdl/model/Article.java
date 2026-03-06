package com.cdl.model;

import java.time.LocalDateTime;
import java.util.List;

public class Article {
    private int id;
    private String title;
    private String content;
    private String summary;
    private int authorId;
    private String authorUsername;
    private String authorAvatar;
    private Integer categoryId;
    private String categoryName;
    private String status;
    private int views;
    private List<Comment> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Article() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }
    public String getAuthorUsername() { return authorUsername; }
    public void setAuthorUsername(String u) { this.authorUsername = u; }
    public String getAuthorAvatar() { return authorAvatar; }
    public void setAuthorAvatar(String a) { this.authorAvatar = a; }
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public boolean isPublished() { return "PUBLISHED".equals(status); }
    public String getShortContent() {
        if (summary != null && !summary.isBlank()) return summary;
        if (content != null && content.length() > 220)
            return content.replaceAll("<[^>]+>", "").substring(0, 220) + "…";
        return content == null ? "" : content.replaceAll("<[^>]+>", "");
    }
}
