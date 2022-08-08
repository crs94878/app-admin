CREATE TABLE IF NOT EXISTS app_admin_schema.machines
(
	machine_id SERIAL NOT NULL
		CONSTRAINT machines_pkey
			PRIMARY KEY,
	host_name CHAR(255) NOT NULL,
	os CHAR(100) NOT NULL,
	os_type CHAR(100) NOT NULL,
	mac_addr CHAR(255),
	dttm_rec TIMESTAMP NOT NULL
);


CREATE TABLE IF NOT EXISTS app_admin_schema.logins
(
	user_id SERIAL NOT NULL
		CONSTRAINT logins_pkey
			PRIMARY KEY,
	login CHAR(255)
		CONSTRAINT logins_login_key
			UNIQUE,
	password CHAR(255) NOT NULL,
	user_name CHAR(255) NOT NULL,
	user_surname CHAR(255) NOT NULL,
	user_type NUMERIC NOT NULL,
	dttm_rec TIMESTAMP NOT NULL
);


CREATE TABLE IF NOT EXISTS app_admin_schema.tasks
(
	task_id SERIAL NOT NULL
		CONSTRAINT tasks_pkey
			PRIMARY KEY,
	name CHAR(255) NOT NULL,
	taskprocess_type CHAR(100) NOT NULL,
	version CHAR(255) NOT NULL,
	os CHAR(100) NOT NULL,
	os_type CHAR(100) NOT NULL,
	path_to_run_file CHAR(255) NOT NULL,
	torrent_file BYTEA NOT NULL,
	dttm_rec TIMESTAMP NOT NULL
);


CREATE TABLE IF NOT EXISTS app_admin_schema.client_tasks
(
	app_client_id INTEGER NOT NULL
		CONSTRAINT client_tasks_app_client_id_fkey
			REFERENCES app_admin_schema.machines,
	task_id INTEGER NOT NULL
		CONSTRAINT client_tasks_task_id_fkey
			REFERENCES app_admin_schema.tasks,
	status CHAR(100) NOT NULL,
	dttm_asing TIMESTAMP NOT NULL,
	dttm_update TIMESTAMP NOT NULL
);

alter table app_admin_schema.client_tasks owner to dba_app_admin_db;

CREATE TABLE IF NOT EXISTS app_admin_schema.sessions
(
	session_id SERIAL NOT NULL
		CONSTRAINT sessions_pkey
			PRIMARY KEY,
	user_id INTEGER NOT NULL UNIQUE,
	token CHAR(100) NOT NULL,
	dttm_rec TIMESTAMP NOT NULL,
	dttm_expired TIMESTAMP NOT NULL
);


CREATE TABLE IF NOT EXISTS app_admin_schema.users_machine
(
	user_id INTEGER NOT NULL
		CONSTRAINT users_machine_user_id_fkey
			REFERENCES app_admin_schema.logins,
	machine_id INTEGER NOT NULL
		CONSTRAINT users_machine_machine_id_fkey
			REFERENCES app_admin_schema.machines,
	enabled_state NUMERIC(1) default 0 NOT NULL
);

CREATE SEQUENCE app_admin_schema.machines_machine_id_seq
    AS INTEGER;


CREATE SEQUENCE app_admin_schema.logins_user_id_seq
    AS INTEGER;


CREATE SEQUENCE app_admin_schema.tasks_task_id_seq
    AS INTEGER;


CREATE SEQUENCE app_admin_schema.sessions_session_id_seq
    AS INTEGER;


