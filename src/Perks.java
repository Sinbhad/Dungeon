public class Perks {
    String name, type, description;
    int cost;
    double value;

    public Perks(){
        this.name = "";
        this.type = "";
        this.description = "";
        this.value = 0;
        cost = 0;
    }

    public Perks(String name, String type, String description, double value, int cost){
        this.name = name;
        this.type = type;
        this.description = description;
        this.value = value;
        this.cost = 0;
    }

    public void setPerkName(String name){
        this.name = name;
    }

    public String getPerkName(){
        return name;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setValue(double value){
        this.value = value;
    }

    public double getValue(){
        return value;
    }

    public void setCost(int cost){
        this.cost = cost;
    }

    public int getCost(){
        return cost;
    }
}
