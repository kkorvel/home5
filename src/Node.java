//Ideid ja mõtteid sain klassikaaslaste koodist ja ka Internetist!
import java.util.Arrays;
import java.util.LinkedList;
public class Node {
   private String name;
   private Node firstChild;
   private Node nextSibling;
   private static LinkedList<String> items;

   Node (String n, Node d, Node r) {
      name = n;
      firstChild = d;
      nextSibling = r;
   }

   public static Node parsePostfix (String s) {
      int n = 0;
      items = new LinkedList<String>(Arrays.asList(s.split("")));
      for (String item : items) {
         if(item.replaceAll("\\s+", " ").equals(" ")){
            throw new RuntimeException("Leht ei saa olla tühi");
         }
         if (item.equals("(")) {
            n++;
         } else if (item.equals(")")) {
            n--;
            if (n < 0) {
               throw new RuntimeException("Liiga palju sulgusid!");
            }
         } else if (item.equals(",") && n == 0) {
            throw new RuntimeException("Puuduvad sulud:" + s);
         }
      }

      Node root = Treebuild(new Node(null, null, null), items);
      if (root.firstChild == null && root.nextSibling == null) {
         if (root.name != null) {
            return root;
         }
      } else if (root.firstChild == null && root.nextSibling != null) {
         throw new RuntimeException("Pole sulgusid!");
      }
      return root;
   }

   public static Node Treebuild(Node root, LinkedList<String> items) {
      while (!items.isEmpty()) {
         String item = items.pop();
         if (item.equals("(")) {
            root.firstChild = Treebuild(new Node(null, null, null), items);
         }
         else if (item.equals(",")) {
            root.nextSibling = Treebuild(new Node(null, null, null), items);
            if (root.name == null) {
               throw new RuntimeException("empty,  OR   ,empty OR ,,");
            }
            return root;
         } else if (item.equals(")")) {
            if (root.name == null) {
               throw new RuntimeException("empty(X) OR X(empty) OR (empty)X OR (X)empty OR empty(empty)");
            }
            return root;
         } else {
            if (root.name == null) {
               root.name = item;
            } else {
               root.name += item;
            }
         }
      }
      if (root.name == null) {
         throw new RuntimeException("empty, OR ,empty");
      }
      return root;
   }
   public static Node PuuEhitus(Node root, int open) {
      if (root.firstChild != null) {
         open++;
         root.name += "(" + PuuEhitus(root.firstChild, open).name;
         root.name += ")";
         if (root.nextSibling != null) {
            root.name += "," + PuuEhitus(root.nextSibling, open).name;
            return root;
         }
      } else if (root.nextSibling != null) {
         root.name += "," + PuuEhitus(root.nextSibling, open).name;
         return root;
      } else {
         return root;
      }
      return root;
   }

   public String leftParentheticRepresentation() {
      String text = PuuEhitus(this, 0).name;
      return text;
   }

   public int count(Node a) {
      int i = 0;
      while (a.firstChild != null) {
         a = a.firstChild;
         i++;
      }
      return i;
   }

   public void push(Node a) {
      Node temp = new Node(this.name, this.firstChild, this.nextSibling);
      this.name=a.name;
      this.nextSibling = a.nextSibling;
      this.firstChild = temp;
   }

   public Node pop() {
      if (this.name != null) {
         Node temp = new Node(this.name, null, this.nextSibling);
         this.name=this.firstChild.name;
         this.nextSibling = this.firstChild.nextSibling;
         this.firstChild = this.firstChild.firstChild;
         return temp;
      } else {
         return new Node(null, null, null);
      }
   }
   public static void main (String[] param) {
      String s = "(B1,C)A";
      Node t = Node.parsePostfix (s);
      String v = t.leftParentheticRepresentation();
      System.out.println (s + " ==> " + v); // (B1,C)A ==> A(B1,C)
   }
}