public class Cat extends Pet{
    private String name;
    Cat(){
        this.name = "undefined";
        System.out.println("无参数猫构造");
    }
    Cat(String name){
        this.name = name;
        System.out.println(name+"小猫");
    }
    // public void eat(){
    //     System.out.println(name+"小猫正在吃");
    // }
}
