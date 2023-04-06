DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS categories;


CREATE TABLE IF NOT EXISTS users
(
    id
    BIGINT
    GENERATED
    BY
    DEFAULT AS
    IDENTITY
    PRIMARY
    KEY,
    name
    VARCHAR
(
    255
) NOT NULL,
    email VARCHAR
(
    255
) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS categories
(
    id
    BIGINT
    GENERATED
    BY
    DEFAULT AS
    IDENTITY
    PRIMARY
    KEY,
    name
    VARCHAR
(
    255
) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS events
(
--
    id
    BIGINT
    GENERATED
    BY
    DEFAULT AS
    IDENTITY
    PRIMARY
    KEY,
    title
    VARCHAR
(
    120
) NOT NULL,
    annotation VARCHAR
(
    2000
) NOT NULL,
    description VARCHAR
(
    7000
) NOT NULL,
    lat REAL NOT NULL,
    lon REAL NOT NULL,
    event_date TIMESTAMP NOT NULL,
    paid BOOLEAN DEFAULT FALSE,
--
    category_id BIGINT REFERENCES categories
(
    id
),
    request_moderation BOOLEAN DEFAULT TRUE,
    participant_limit INTEGER DEFAULT 0,
    confirmed_requests INTEGER DEFAULT 0,
    views BIGINT DEFAULT 0,
--
    initiator_id BIGINT REFERENCES users
(
    id
),
    created_on TIMESTAMP NOT NULL,
    published_on TIMESTAMP,
    state VARCHAR
(
    64
)
    );

CREATE TABLE IF NOT EXISTS requests
(
    id
    BIGINT
    GENERATED
    BY
    DEFAULT AS
    IDENTITY
    PRIMARY
    KEY,
    requester_id
    BIGINT
    REFERENCES
    users
(
    id
),
    event_id BIGINT REFERENCES events
(
    id
),
    created TIMESTAMP NOT NULL,
    status VARCHAR
(
    64
) NOT NULL
    );

CREATE TABLE IF NOT EXISTS compilations
(
    id
    BIGINT
    GENERATED
    BY
    DEFAULT AS
    IDENTITY
    PRIMARY
    KEY,
    title
    VARCHAR
(
    120
) NOT NULL,
    pinned BOOLEAN DEFAULT FALSE
    );

CREATE TABLE IF NOT EXISTS compilations
(
    id
    BIGINT
    GENERATED
    BY
    DEFAULT AS
    IDENTITY
    PRIMARY
    KEY,
    title
    VARCHAR
(
    120
) NOT NULL,
    pinned BOOLEAN DEFAULT FALSE
    );

CREATE TABLE IF NOT EXISTS event_compilations
(
    compilation_id BIGINT REFERENCES compilations
(
    id
),
    event_id BIGINT REFERENCES events
(
    id
),
    PRIMARY KEY
(
    compilation_id,
    event_id
)
    );

