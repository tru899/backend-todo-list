-- V100 Create Items table
CREATE TABLE items
(
    id      uuid PRIMARY KEY,
    text    VARCHAR(255) NOT NULL,
    user_id VARCHAR(80)  NOT NULL
);

CREATE INDEX idx_items_user_id ON items(user_id);
