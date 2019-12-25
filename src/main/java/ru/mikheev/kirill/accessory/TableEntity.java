package ru.mikheev.kirill.accessory;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Интерфейс, который обобщает все возможные классы записей в таблицах
 * @author Kirill Mikheev
 * @version 1.0
 */

public interface TableEntity {

    /**
     * Создаем запись на освнове резалт сета, который был получен из базы
     * @param resultSet данные, полученные из таблицы
     * @return новую запись
     */
    TableEntity getInstanceBasedOnResultSet(ResultSet resultSet) throws SQLException;
}
