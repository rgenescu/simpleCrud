# Simple CRUD API for Testing

This repository contains a very simple backend (BE) API designed for testing CRUD (Create, Read, Update, Delete) operations.

## Database Setup

Before you can run the API, you need to set up the database. This API uses a PostgreSQL database with the following table structure:

\```sql
CREATE TABLE users (
  user_id SERIAL PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  phone VARCHAR(20)
);
\```

## Getting Started

To get started with this API, follow these steps:

1. Clone the repository to your local machine.
2. Ensure you have PostgreSQL installed and running on your system. If you don't, you can download and install it from [the PostgreSQL official website](https://www.postgresql.org/download/).
3. Create a database and run the above SQL script to create the `users` table.
4. Update the database connection settings in the API configuration to point to your local database.

For more detailed information on how to get started with PostgreSQL, you can visit the [PostgreSQL documentation](https://www.postgresql.org/docs/).

## Running the API

After setting up your database and configuring the API, you can start the server by running the appropriate command from your project directory (this command may vary depending on how your project is set up). Make sure to check the project's documentation for specific instructions on running the API.
