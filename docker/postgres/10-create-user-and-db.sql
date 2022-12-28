-- file: 10-create-user-and-db.sql
CREATE DATABASE todo_list;
CREATE USER program WITH PASSWORD 'test';
GRANT ALL PRIVILEGES ON DATABASE todo_list TO program;