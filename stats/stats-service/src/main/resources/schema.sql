DROP TABLE IF EXISTS hits;

CREATE TABLE IF NOT EXISTS hits
(
    id
    BIGINT
    GENERATED
    BY
    DEFAULT AS
    IDENTITY
    PRIMARY
    KEY,
    app
    VARCHAR
(
    64
) NOT NULL,
    uri VARCHAR
(
    512
) NOT NULL,
    ip VARCHAR
(
    64
) NOT NULL,
    timestamp TIMESTAMP NOT NULL
    );