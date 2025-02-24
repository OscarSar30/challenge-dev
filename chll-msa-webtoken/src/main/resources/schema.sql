-- Drop table
-- DROP TABLE public.users;

CREATE TABLE IF NOT EXISTS public.users (
	user_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	full_name varchar(255) NOT NULL,
	email VARCHAR(255) NULL,
	identification VARCHAR(255) NOT NULL UNIQUE,
	status VARCHAR(20) DEFAULT 'ACTIVE',
	registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- Drop table
-- DROP TABLE public.tokens;

CREATE TABLE IF NOT EXISTS public.tokens (
	token_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	token VARCHAR(6) NOT NULL UNIQUE,
	generation_date TIMESTAMP NOT NULL,
	expiration_date TIMESTAMP NOT NULL,
	user_id UUID NOT NULL,
	is_used BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_users FOREIGN KEY (user_id) REFERENCES public.users(user_id)
);