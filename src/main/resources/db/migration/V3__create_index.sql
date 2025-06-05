CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX idx_authors_first_name_trgm ON authors USING gin (first_name gin_trgm_ops);
CREATE INDEX idx_books_title_trgm ON books USING gin (title gin_trgm_ops);