package army.helpful.persistha.actions;

public enum EnumActionTypes {

    publishContent("publish content");


    String description;
    EnumActionTypes(String description ){

        this.description= description;

    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
