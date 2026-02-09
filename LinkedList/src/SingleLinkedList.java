public class SingleLinkedList {

    Node head = null;

    public void addLast(int data){
        Node node = new Node();
        node.data = data;
        node.next = null;

        if(head == null){
            head = node;
        }
        else{
            Node temp = head;

            while(temp.next != null){
                temp = temp.next;
            }
            temp.next = node;
        }
    }

    public void addFirst(int data){
        Node node = new Node();
        node.data = data;
        node.next = null;
        if(head == null){
            head = node;
        }
        else{
            Node temp = head;
            node.next = temp;
            head = node;
        }
    }

    public void addIn(int data, int index){
        Node node = new Node();
        node.data = data;
        node.next = null;
        if(head == null){
            head = node;
        }
        else{
            Node temp = head;
            if(index == 0){
                addFirst(data);
            } else if (index==size()) {
                addLast(data);

            } else if (index>size()) {
                return;

            } else {
                for(int i = 0; i < index-1; i++){
                    temp = temp.next;
                }
                node.next = temp.next;
                temp.next = node;
            }
        }

    }

    public int size(){
        if(head == null){
            return 0;
        }
        else{
            Node temp = head;
            int count = 1;
            while(temp.next != null){
                count++;
                temp = temp.next;
            }
            return count;
        }
    }




    public void printList(){
        Node temp = head;
        while(temp != null){
            System.out.print(temp.data+" ");
            temp = temp.next;
        }
        System.out.println();
    }
}
