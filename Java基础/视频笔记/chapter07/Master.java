// import Pet.Dog;
// package 

public class Master {
    private String name;
    Master(){
        this.name = "undefined";
        System.out.println("无参主人构造");
    }
    Master(String name){
        this.name = name;
        System.out.println(name + "主人被构造");
    }
    public void feed(Pet pet){
        System.out.println(name + "主人喂食");
        pet.eat();
        return;
    }    
}
