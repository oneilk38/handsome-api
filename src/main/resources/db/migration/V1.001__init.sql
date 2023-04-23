CREATE TABLE companies (
    id uuid primary key,
    company_name varchar(255) not null,
    country_code varchar(2) not null,
    created_at timestamp not null,
    deleted_at timestamp
);

CREATE TABLE employees (
   id uuid primary key,
   first_name varchar(255) not null,
   last_name varchar(255) not null,
   company_id uuid not null,
   email varchar(255) not null,
   job_title varchar(255) not null,
   created_at timestamp not null,
   deleted_at timestamp,
   FOREIGN KEY (company_id) REFERENCES companies (id)
);

CREATE TABLE projects (
    id uuid primary key,
    project_name varchar(255) not null,
    project_description varchar(255) not null,
    company_id uuid,
    created_at timestamp not null,
    deleted_at timestamp,
    FOREIGN KEY (company_id) REFERENCES companies (id)
);