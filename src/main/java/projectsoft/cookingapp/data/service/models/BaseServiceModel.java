package projectsoft.cookingapp.data.service.models;

public abstract class BaseServiceModel {

    private String id;

    protected BaseServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
