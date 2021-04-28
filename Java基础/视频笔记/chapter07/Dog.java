public class Dog extends Pet{
    private String name;
    Dog(){
        this.name = "undefined";
        System.out.println("无参数狗构造");
    }
    Dog(String name){
        System.out.println(name+"小狗");
        this.name = name;
    }
    public void eat(){
        System.out.println(name+"小狗正在吃");
    }
    public void ate(){
        System.out.println(name+"小狗正在咬人");
    }
}