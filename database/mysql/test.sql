INSERT INTO awesome_toilet.user(account, name, email, create_time, update_time)
VALUES ('15233925307', 'DAMAO', '3034906016@QQ.com', TIMESTAMP(NOW()), TIMESTAMP(NOW()));

INSERT INTO awesome_toilet.user_auth(uid, account, password)
VALUES (1, '15233925307', '123456789')