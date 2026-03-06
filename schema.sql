-- ============================================================
--  CDL Blog — Schéma de base de données
--  MySQL 8.0+  |  Moteur InnoDB  |  Encodage utf8mb4
--  Auteur  : CDL / EST Agadir
--  Date    : 2026-03-04
-- ============================================================

CREATE DATABASE IF NOT EXISTS cdl_blog
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE cdl_blog;

-- ------------------------------------------------------------
-- Table : users
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    id                 INT            NOT NULL AUTO_INCREMENT,
    username           VARCHAR(50)    NOT NULL,
    email              VARCHAR(100)   NOT NULL,
    password_hash      VARCHAR(255)   NOT NULL,
    bio                TEXT,
    avatar_url         VARCHAR(255)   DEFAULT 'default-avatar.png',
    role               ENUM('MEMBER','ADMIN') DEFAULT 'MEMBER',
    is_verified        TINYINT(1)     DEFAULT 0,
    verification_token VARCHAR(255),
    token_expiry       DATETIME,
    created_at         TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uq_username (username),
    UNIQUE KEY uq_email    (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ------------------------------------------------------------
-- Table : categories
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS categories (
    id       INT          NOT NULL AUTO_INCREMENT,
    name_fr  VARCHAR(100) NOT NULL,
    name_en  VARCHAR(100) NOT NULL,
    slug     VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uq_slug (slug)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ------------------------------------------------------------
-- Table : articles
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS articles (
    id          INT           NOT NULL AUTO_INCREMENT,
    title       VARCHAR(255)  NOT NULL,
    content     LONGTEXT      NOT NULL,
    summary     TEXT,
    author_id   INT           NOT NULL,
    category_id INT,
    status      ENUM('DRAFT','PUBLISHED') DEFAULT 'PUBLISHED',
    views       INT           DEFAULT 0,
    created_at  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_article_author   FOREIGN KEY (author_id)   REFERENCES users(id)      ON DELETE CASCADE,
    CONSTRAINT fk_article_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ------------------------------------------------------------
-- Table : comments
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS comments (
    id         INT       NOT NULL AUTO_INCREMENT,
    content    TEXT      NOT NULL,
    article_id INT       NOT NULL,
    author_id  INT       NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_comment_article FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE,
    CONSTRAINT fk_comment_author  FOREIGN KEY (author_id)  REFERENCES users(id)    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Données initiales
-- ============================================================

-- Catégories par défaut
INSERT INTO categories (name_fr, name_en, slug) VALUES
  ('Technologie', 'Technology', 'technologie'),
  ('Science',     'Science',    'science'),
  ('Actualité',   'News',       'actualite'),
  ('Tutoriels',   'Tutorials',  'tutoriels'),
  ('Autre',       'Other',      'autre');

-- Compte admin (mot de passe : Admin@2026)
-- Hash BCrypt généré avec cost=12
INSERT INTO users (username, email, password_hash, role, is_verified) VALUES
  ('admin', 'admin@cdlblog.com',
   '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LeAJ9vV5/V/NaVpJO',
   'ADMIN', 1);

-- Articles de démonstration
INSERT INTO articles (title, content, summary, author_id, category_id, status) VALUES
  ('Bienvenue sur CDL Blog',
   '<p>CDL Blog est une application web développée en <strong>Jakarta EE</strong> avec Servlets, JSP et JSTL 3.0.</p><p>Elle utilise MySQL 9.5 comme base de données et BCrypt pour le hashage des mots de passe.</p>',
   'Présentation du projet CDL Blog construit avec Jakarta EE.',
   1, 1, 'PUBLISHED'),

  ('Introduction aux Servlets Jakarta EE',
   '<p>Les <strong>Servlets Jakarta EE</strong> sont la base du développement web Java côté serveur.</p><p>Elles s\'exécutent dans un conteneur comme Tomcat 10+ et répondent aux requêtes HTTP via <code>doGet()</code> et <code>doPost()</code>.</p>',
   'Guide d\'introduction aux Servlets Jakarta EE avec Tomcat 10.',
   1, 4, 'PUBLISHED'),

  ('JSTL 3.0 avec Jakarta EE',
   '<p>La <strong>JSTL 3.0</strong> (Jakarta Standard Tag Library) simplifie les pages JSP avec des balises comme <code>&lt;c:forEach&gt;</code>, <code>&lt;c:if&gt;</code> et <code>&lt;fmt:message&gt;</code>.</p><p>Elle remplace le code Java scriptlet par des balises déclaratives plus lisibles.</p>',
   'Utilisation de la JSTL 3.0 dans les pages JSP Jakarta EE.',
   1, 4, 'PUBLISHED');

-- ============================================================
-- Vérification
-- ============================================================
SELECT 'Tables créées avec succès !' AS statut;
SELECT table_name, table_rows
FROM information_schema.tables
WHERE table_schema = 'cdl_blog';
