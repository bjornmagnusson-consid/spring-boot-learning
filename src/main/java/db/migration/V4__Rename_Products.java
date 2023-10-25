package db.migration;

import java.sql.ResultSet;
import java.sql.Statement;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V4__Rename_Products extends BaseJavaMigration {

    private static final Logger LOGGER = LoggerFactory.getLogger(V4__Rename_Products.class);

    @Override
    public void migrate(Context context) throws Exception {
        try (Statement select = context.getConnection().createStatement()) {
            try (ResultSet rows = select.executeQuery("SELECT id,name,description FROM product")) {
                while (rows.next()) {
                    var id = rows.getInt("id");
                    var name = rows.getString("name");
                    var newName = name.replaceAll("post", "product");
                    var description = rows.getString("description").replaceAll("name", "description");
                    var newDescription = description.replaceAll("post", "product");
                    LOGGER.info("Renaming product (id={},name={},description={}) to product (name={},description={})", id, name, description, newName, newDescription);
                    try (Statement create = context.getConnection().createStatement()) {
                        String statement = "UPDATE product SET name='" + newName + "',description='" + newDescription + "' WHERE id=" + id;
                        LOGGER.info("Using statement {}", statement);
                        create.execute(statement);
                    }
                }
            }
        }
    }
}
