-- Main table for cards
CREATE TABLE cards (
    id VARCHAR(255) PRIMARY KEY,
    scryfall_id VARCHAR(255),
    name VARCHAR(255),
    description TEXT,
    type VARCHAR(255),
    mana_cost VARCHAR(255),
    total_mana_cost VARCHAR(255),
    power VARCHAR(50),
    toughness VARCHAR(50),
    rarity VARCHAR(100),
    artist VARCHAR(255),

    -- Embedded fields from Images
    small VARCHAR(500),
    normal VARCHAR(500),
    large VARCHAR(500),
    png VARCHAR(500),
    art_crop VARCHAR(500),
    border_crop VARCHAR(500),

    -- Audit fields
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Card colors (list of strings)
CREATE TABLE card_colors (
    card_id VARCHAR(255) NOT NULL,
    color VARCHAR(50) NOT NULL,
    PRIMARY KEY (card_id, color),
    CONSTRAINT fk_card_colors_card FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE
);

-- Card color identity (list of strings)
CREATE TABLE card_color_identity (
    card_id VARCHAR(255) NOT NULL,
    color_identity VARCHAR(50) NOT NULL,
    PRIMARY KEY (card_id, color_identity),
    CONSTRAINT fk_card_color_identity_card FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE
);

-- Faces (some cards have multiple faces)
CREATE TABLE faces (
    id VARCHAR(255) PRIMARY KEY,
    card_id VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    description TEXT,
    type VARCHAR(255),
    mana_cost VARCHAR(255),
    power VARCHAR(50),
    toughness VARCHAR(50),
    artist VARCHAR(255),

    -- Embedded fields from Images
    small VARCHAR(500),
    normal VARCHAR(500),
    large VARCHAR(500),
    png VARCHAR(500),
    art_crop VARCHAR(500),
    border_crop VARCHAR(500),

    -- Audit fields
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_faces_card FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE
);

-- Face colors (list of strings)
CREATE TABLE face_colors (
    face_id VARCHAR(255) NOT NULL,
    color VARCHAR(50) NOT NULL,
    PRIMARY KEY (face_id, color),
    CONSTRAINT fk_face_colors_face FOREIGN KEY (face_id) REFERENCES faces(id) ON DELETE CASCADE
);
