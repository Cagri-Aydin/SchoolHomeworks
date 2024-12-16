public class SingleLinkedList {
    public Node head;
    public void add(Object data ){
        Node newNode = new Node(data);
        if (head == null){
            head = newNode;
        }else{
            Node temp = head;
            while(temp.getLink() != null){
                temp = temp.getLink();
            }
            temp.setLink(newNode);
        }
    }
    public int size (){
        if(head == null)
            return 0;
        else{
            Node temp = head;
            int count =0;
            while(temp != null ){
                count++;
                temp = temp.getLink();
            }
            return count;
        }
    }
    public void display(){
        if(head == null){
        }else{
            Node temp = head;
            while(temp != null){
                System.out.print(temp.getData() + " ");
                temp = temp.getLink();
            }
        }
    }
    public Object delete_Node(int n) {
        if (head == null || n < 0) {
            return null; // or throw an exception
        }
        Node current = head;
        Node previous = null;
        for (int i = 0; i < n; i++) {
            if (current == null) {
                return null; // or throw an exception
            }
            previous = current;
            current = current.link;
        }
        if (previous == null) {
            head = head.link;
        } else {
            previous.link = current.link;
        }
        return current.data;
    }
    public Object get_Nth_Object(int n) {
        if (head == null || n < 0) {
            return null; // or throw an exception
        }
        Node current = head;
        for (int i = 0; i < n; i++) {
            if (current == null) {
                return null; // or throw an exception
            }
            current = current.link;
        }
        return current.data;
    }
}
