//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        SingleLinkedList list = new SingleLinkedList();

        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        list.addLast(5);


        list.addIn(-5, 0);
        list.addIn(22,8);

        list.printList();
        System.out.println(list.size());

    }
}