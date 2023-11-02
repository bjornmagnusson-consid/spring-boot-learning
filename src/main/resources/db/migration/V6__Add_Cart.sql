CREATE TABLE IF NOT EXISTS cart (
    id SERIAL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS cart_item (
    id SERIAL,
    cart_id int,
    product_id int,
    number int,
    CONSTRAINT fk_cart
      FOREIGN KEY(cart_id) 
	  REFERENCES cart(id)
);