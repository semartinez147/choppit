# Database Description Language

```SQLite
CREATE TABLE IF NOT EXISTS `Ingredient`
(
    `ingredient_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `step_id`       INTEGER                           NOT NULL,
    `quantity`      TEXT,
    `name`          TEXT,
    `unit`          TEXT                              NOT NULL COLLATE NOCASE,
    `unit_alt`      TEXT COLLATE NOCASE,
    FOREIGN KEY (`step_id`) REFERENCES `Step` (`step_id`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_Ingredient_step_id` ON `Ingredient` (`step_id`);

CREATE TABLE IF NOT EXISTS `Recipe`
(
    `recipe_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `url`       TEXT COLLATE NOCASE,
    `title`     TEXT                              NOT NULL COLLATE NOCASE DEFAULT 'Untitled Recipe',
    `favorite`  INTEGER                           NOT NULL                DEFAULT false
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_Recipe_title_url` ON `Recipe` (`title`, `url`);

CREATE INDEX IF NOT EXISTS `index_Recipe_title` ON `Recipe` (`title`);

CREATE INDEX IF NOT EXISTS `index_Recipe_favorite` ON `Recipe` (`favorite`);

CREATE TABLE IF NOT EXISTS `Step`
(
    `step_id`      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `recipe_id`    INTEGER                           NOT NULL,
    `instructions` TEXT                              NOT NULL COLLATE NOCASE,
    `recipe_order` INTEGER                           NOT NULL,
    FOREIGN KEY (`recipe_id`) REFERENCES `Recipe` (`recipe_id`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_Step_recipe_id_recipe_order` ON `Step` (`recipe_id`, `recipe_order`);
```

[Download](ddl.sql)