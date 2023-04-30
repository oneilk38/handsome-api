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
    company_id uuid not null,
    project_owner uuid,
    created_by uuid,
    created_at timestamp not null,
    deleted_at timestamp,
    FOREIGN KEY (company_id) REFERENCES companies (id)
);

CREATE TABLE project_assignments(
    id uuid primary key,
    project_id uuid not null,
    company_id uuid not null,
    employee_id uuid,
    created_at timestamp not null,
    deleted_at timestamp,
    FOREIGN KEY (company_id) REFERENCES companies (id),
    FOREIGN KEY (project_id) REFERENCES projects (id),
    FOREIGN KEY (employee_id) REFERENCES employees (id)
);

CREATE TABLE tickets(
    id uuid primary key,
    project_id uuid not null,
    company_id uuid not null,
    title varchar(255) not null,
    description varchar(255),
    status varchar(255) not null,
    reporter uuid not null,
    assignee uuid,
    created_at timestamp not null,
    updated_at timestamp,
    deleted_at timestamp,
    FOREIGN KEY (company_id) REFERENCES companies (id),
    FOREIGN KEY (project_id) REFERENCES projects (id),
    FOREIGN KEY (reporter) REFERENCES employees (id)
)

