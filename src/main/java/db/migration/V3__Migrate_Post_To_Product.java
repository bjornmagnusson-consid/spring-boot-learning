package db.migration;

import java.sql.ResultSet;
import java.sql.Statement;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V3__Migrate_Post_To_Product extends BaseJavaMigration {

    private static final Logger LOGGER = LoggerFactory.getLogger(V3__Migrate_Post_To_Product.class);

    @Override
    public void migrate(Context context) throws Exception {
        try (Statement select = context.getConnection().createStatement()) {
            try (ResultSet rows = select.executeQuery("SELECT id,body,title FROM post")) {
                while (rows.next()) {
                    var id = rows.getInt("id");
                    var body = rows.getString("body");
                    var title = rows.getString("title");
                    LOGGER.info("Migrating post (id={},body={},title={}) to product", id, body, title);
                    try (Statement create = context.getConnection().createStatement()) {
                        String statement = "INSERT INTO product(name,description) VALUES ('" + title + "'," + "'" + body + "')";
                        LOGGER.info("Using statement {}", statement);
                        create.execute(statement);
                    }
                }
            }
        }
    }
}
