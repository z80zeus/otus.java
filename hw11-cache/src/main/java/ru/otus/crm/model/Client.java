package ru.otus.crm.model;

import ru.otus.crm.annotations.Id;

/**
 * Класс "Клиент".
 * В данной учебной работе характеризуется тем, что поле идентификатор называется id - так же как и в БД.
 * Но аннотация всё-равно необходима: ORM опознаёт поле идентификатора только по аннотации Id.
 */
public class Client {
    @Id
    private Long id;
    private String name;

    @SuppressWarnings("unused")
    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
