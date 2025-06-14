package Pet.Society.models.dto.pet;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PetDTO {
    @NotNull
    private String name;
    @Positive
    private int age;
    private boolean active = true;
    @NotNull
    private Long clientId;

    public PetDTO() {
    }

    public PetDTO(String name, int age, boolean active, Long clientId) {
        this.name = name;
        this.age = age;
        this.active = active;
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "PetDTO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", active=" + active +
                ", clientId=" + clientId +
                '}';
    }
}
