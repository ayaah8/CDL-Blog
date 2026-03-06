# CDL Blog — Jakarta EE Web Application

Projet de blog développé avec **Jakarta EE** (Servlets + JSP + JSTL 3.0), **MySQL 9.5** et **BCrypt**.  
Réalisé dans le cadre du cours de Développement Web — CDL / EST Agadir.

---

## 🚀 Technologies utilisées

| Bibliothèque | Version | Rôle |
|---|---|---|
| `jakarta.servlet.jsp.jstl` | **3.0.0** | JSTL runtime |
| `jakarta.servlet.jsp.jstl-api` | **3.0.0** | JSTL API |
| `mysql-connector-j` | **9.5.0** | Driver JDBC MySQL |
| `jbcrypt` | 0.4 | Hashage BCrypt |
| Jakarta Servlet API | 6.0 | Servlets |
| Apache Tomcat | **10.1+** | Conteneur |

> Les 3 JARs fournis sont déjà placés dans `WEB-INF/lib/` — **aucun téléchargement nécessaire**.

---

## 📁 Structure du projet

```
CDL-Blog/
├── pom.xml
├── schema.sql                         ← Schéma MySQL + données initiales
├── src/main/
│   ├── java/com/cdl/
│   │   ├── model/      User, Article, Comment, Category
│   │   ├── dao/        UserDAO, ArticleDAO, CommentDAO
│   │   ├── servlet/    HomeServlet, LoginServlet, RegisterServlet,
│   │   │               LogoutServlet, VerifyServlet, ArticleServlet,
│   │   │               CommentServlet, ProfileServlet, LangServlet
│   │   ├── filter/     AuthFilter, EncodingFilter, LangFilter
│   │   └── util/       DBUtil
│   ├── resources/
│   │   ├── database.properties
│   │   ├── messages_fr.properties
│   │   └── messages_en.properties
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── web.xml
│       │   ├── lib/    ← 3 JARs fournis + jbcrypt (Maven)
│       │   └── jsp/    header, footer, index, articles, article-view,
│       │               article-form, my-articles, login, register,
│       │               profile, error
│       ├── css/style.css
│       └── js/app.js
```

---

## ⚙️ Installation

### 1. Prérequis
- Java 17+
- Apache Tomcat **10.1+** (obligatoire pour Jakarta EE 10 / jakarta.* namespace)
- MySQL 8.0+ ou MariaDB 10.6+
- Maven 3.8+

### 2. Base de données

```sql
-- Exécuter dans MySQL :
source schema.sql;
```

Cela crée la base `cdl_blog` avec les tables et un compte admin :
- **Email :** `admin@cdlblog.com`
- **Mot de passe :** `Admin@2026`

### 3. Configuration

Modifier `src/main/resources/database.properties` :
```properties
db.url=jdbc:mysql://localhost:3306/cdl_blog?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8
db.username=root
db.password=VOTRE_MOT_DE_PASSE
```

### 4. Build & Déploiement

```bash
mvn clean package
# Copier le WAR dans Tomcat :
cp target/CDL-Blog.war $TOMCAT_HOME/webapps/
# Démarrer Tomcat et accéder à :
# http://localhost:8080/CDL-Blog/
```

---

## 🗄️ Tables de la base de données

```
users        — Comptes membres (id, username, email, password_hash, role, is_verified, …)
categories   — Catégories d'articles (id, name_fr, name_en, slug)
articles     — Articles (id, title, content, summary, author_id, category_id, status, views, …)
comments     — Commentaires (id, content, article_id, author_id, …)
```

---

## 🔐 Sécurité

- Mots de passe hashés avec **BCrypt** (cost=12)
- **AuthFilter** protège toutes les URLs `/member/*`
- **EncodingFilter** force UTF-8 sur toutes les requêtes
- Protection SQL injection via **PreparedStatement**
- Sessions invalidées à la déconnexion

---

## 🌍 Internationalisation

- Deux langues : **Français** (défaut) et **English**
- Fichiers : `messages_fr.properties` / `messages_en.properties`
- Bascule via `/lang?lang=fr` ou `/lang?lang=en`
- `LangFilter` applique la langue à chaque requête

---

## 📌 URLs principales

| URL | Description |
|---|---|
| `/` | Accueil |
| `/articles` | Liste des articles |
| `/articles?action=view&id=X` | Lire un article |
| `/login` | Connexion |
| `/register` | Inscription |
| `/logout` | Déconnexion |
| `/verify?token=XXX` | Vérification email |
| `/member/articles` | Mes articles (auth) |
| `/member/articles?action=new` | Nouvel article (auth) |
| `/member/profile` | Mon profil (auth) |
| `/lang?lang=en` | Changer la langue |

---

© 2026 CDL Blog — EST Agadir
