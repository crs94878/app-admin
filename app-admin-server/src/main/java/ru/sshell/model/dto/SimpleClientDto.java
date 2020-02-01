package ru.sshell.model.dto;

public class SimpleClientDto {
    private Long id;
    private Boolean blocked;

    public Long getId() {
        return id;
    }

    public SimpleClientDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public SimpleClientDto setBlocked(Boolean blocked) {
        this.blocked = blocked;
        return this;
    }

    @Override
    public String toString() {
        return "SimpleClientDto{" +
                "id=" + id +
                ", blocked=" + blocked +
                '}';
    }
}
