public class Test {
    


    public static void main(String[] args){
        Master master = new Master("litturtle");
        Pet dogPet = new Dog("北瓜");    //这会主动调用父类的构造方法
        Pet catPet = new Cat("南瓜");
        master.feed(catPet);
        master.feed(dogPet);
        // dogPet.ate();   报错
        if(dogPet instanceof Dog){
            Dog dog = (Dog)dogPet;
            dog.ate();
        }
        // Bird test = new Bird();

    } 
    
}


