CREATE TABLE IF NOT EXISTS category (
    id SERIAL,
    name VARCHAR(255),  
    PRIMARY KEY(id)
);

ALTER TABLE product 
ADD COLUMN category_id int;

ALTER TABLE product
ADD CONSTRAINT fk_category
FOREIGN KEY(category_id) 
REFERENCES category(id);