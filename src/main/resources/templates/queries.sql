-- Insert initial admin user
INSERT INTO users (id,email, password, username, verified)
VALUES (2,'terissagwendjo@dci-student.org', '$2a$10$SWJ8jXcmRxDuLWr7HnxeJ.5FDUPD/Z.NqoHdEQ5x.HNcMh2QTtL6y', 'admin', true);

INSERT INTO roles (id, name)
VALUES (2, 'ROLE_ADMIN');

-- Insert user and ROLE_ADMIN into user_roles junction table
INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE username = 'admin'), 2);