# Database Description Language

```SQLite
CREATE TABLE IF NOT EXISTS `Ingredient`
(
    `ingredient_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `step_id`       INTEGER                           NOT NULL,
    `item_id`       INTEGER                           NOT NULL,
    `quantity`      INTEGER                           NOT NULL,
    `unit`          TEXT COLLATE NOCASE,
    FOREIGN KEY (`step_id`) REFERENCES `Step` (`step_id`) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `Item` (`item_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_Ingredient_step_id` ON `Ingredient` (`step_id`);

CREATE INDEX IF NOT EXISTS `index_Ingredient_item_id` ON `Ingredient` (`item_id`);

CREATE TABLE IF NOT EXISTS `Item`
(
    `item_id`   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `item_name` TEXT COLLATE NOCASE
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_Item_item_name` ON `Item` (`item_name`);

CREATE TABLE IF NOT EXISTS `Recipe`
(
    `recipe_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `url`       TEXT COLLATE NOCASE,
    `title`     TEXT COLLATE NOCASE                        DEFAULT 'Untitled Recipe',
    `edited`    INTEGER                           NOT NULL DEFAULT false,
    `favorite`  INTEGER                           NOT NULL DEFAULT false
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_Recipe_title_url` ON `Recipe` (`title`, `url`);

CREATE INDEX IF NOT EXISTS `index_Recipe_title` ON `Recipe` (`title`);

CREATE INDEX IF NOT EXISTS `index_Recipe_edited` ON `Recipe` (`edited`);

CREATE INDEX IF NOT EXISTS `index_Recipe_favorite` ON `Recipe` (`favorite`);

CREATE TABLE IF NOT EXISTS `Step`
(
    `step_id`      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `recipe_id`    INTEGER                           NOT NULL,
    `instructions` TEXT COLLATE NOCASE,
    `recipe_order` INTEGER                           NOT NULL,
    FOREIGN KEY (`recipe_id`) REFERENCES `Recipe` (`recipe_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_Step_recipe_id_recipe_order` ON `Step` (`recipe_id`, `recipe_order`);

CREATE INDEX IF NOT EXISTS `index_Step_recipe_id` ON `Step` (`recipe_id`);
```

[Download](ddl.sql)