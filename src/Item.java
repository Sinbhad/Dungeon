public class Item{
    String name;
    String type;
    String description;
    int hpValue, attackValue, speedValue;


    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setAttackValue(int attackValue){
        this.attackValue = attackValue;
    }

    public int getAttackValue(){
        return attackValue;
    }

    public void setHpValue(int hpValue){
        this.hpValue = hpValue;
    }

    public int getHpValue(){
        return hpValue;
    }

    public void setSpeedValue(int speedValue){
        this.speedValue = speedValue;
    }

    public int getSpeedValue(){
        return speedValue;
    }
}
