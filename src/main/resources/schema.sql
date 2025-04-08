CREATE TABLE IF NOT EXISTS notification_log (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255),
    dia_hora VARCHAR(255),
    location_name VARCHAR(255),
    location_region VARCHAR(255),
    country VARCHAR(255),
    forecast_description VARCHAR(500)
);