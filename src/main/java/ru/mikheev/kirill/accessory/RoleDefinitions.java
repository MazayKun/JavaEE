package ru.mikheev.kirill.accessory;

/**
 * Enum, который отвечает за состояние поля name в таблице ROLE
 * @author Kirill Mikheev
 * @version 1.0
 */

public enum RoleDefinitions {
    /**
     * Возможные наименования
     */
    ADMINISTRATION("Administration"),
    CLIENTS("Clients"),
    BILLING("Billing");

    /**
     * Стринговое представление в соответствии с ТЗ
     */
    String name;

    /**
     * Конструктор
     * @param name Стринговое представление объекта
     */
    RoleDefinitions(String name){
        this.name = name;
    }

    /**
     * @return стринговое представление объекта для записи в базу в соответствии с тз
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Метод возвращает Объект по его имени
     * @param name стринговое имся объекта
     * @return null, если имя неверное, иначе объект с соостветсвующим именем
     */
    public static RoleDefinitions getByName(String name){
        switch (name){
            case "Administration":{return ADMINISTRATION;}
            case "Clients":{return CLIENTS;}
            case "Billing":{return BILLING;}
            default:{
                return null;
            }
        }
    }
}
