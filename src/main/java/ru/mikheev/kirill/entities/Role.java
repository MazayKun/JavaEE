package ru.mikheev.kirill.entities;

import ru.mikheev.kirill.accessory.RoleDefinitions;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс записи в таблице ROLE
 * @author Kirill Mikheev
 * @version 1.0
 */

public class Role {

    /** Глобальный ID роли  */
    private static int global_role_id = 0;

    /** Поля каждой записи */
    private int id;
    private RoleDefinitions name;
    private String description;

    /**
     * Конструктор принимающий на вход значения всех полей кроме id, который генерируется отдельно
     */
    public Role(RoleDefinitions name, String description) {
        this.id = global_role_id;
        global_role_id++;
        this.name = name;
        this.description = description;
    }

    /**
     * Приватный конструктор для создания объекта на основе данны из таблицы
     */
    private Role(int id, RoleDefinitions name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * @return id роли в формате int
     */
    public int getId() {
        return id;
    }

    /**
     * @return значение роли, объект типа RoleDefinition
     */
    public RoleDefinitions getName() {
        return name;
    }

    /**
     * @return описание роли, объект типа String
     */
    public String getDescription() {
        return description;
    }


    /**
     * Создание нового объекта на основе резалт сета, полученного из таблицы
     * @param resultSet данные, полученные из таблицы
     * @return новую запись
     * @throws SQLException
     */
    public static Role getInstanceBasedOnResultSet(ResultSet resultSet) throws SQLException {
        Role role = null;
        if (resultSet.next()){
            role = new Role(
                    resultSet.getInt(1),
                    RoleDefinitions.getByName(resultSet.getString(2)),
                    resultSet.getString(3)
            );
        }
        return role;
    }
}
