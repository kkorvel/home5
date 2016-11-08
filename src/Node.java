//Ideid ja mõtteid sain klassikaaslaste koodist ja ka Internetist!
//https://bitbucket.org/akopolov/algorithms
//https://docs.oracle.com/javase/7/docs/api/javax/swing/tree/TreeNode.html
//http://stackoverflow.com/questions/10290649/tree-of-nodet-explaination
//http://programtalk.com/java/java-tree-implementation/

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
      sb = s;
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

      Node root = treebuild(new Node(null, null, null), items);
      if (root.firstChild == null && root.nextSibling == null) {
         if (root.name != null) {
            return root;
         }
      } else if (root.firstChild == null && root.nextSibling != null) {
         throw new RuntimeException("Pole sulgusid!");
      }

      return root;
   }

   public static Node treebuild(Node root, LinkedList<String> items) {
      while (!items.isEmpty()) {
         String item = items.pop();
         if (item.equals("(")) {
            root.firstChild = treebuild(new Node(null, null, null), items);
            if (!items.isEmpty() && root.name != null){
               throw new RuntimeException("Tal pole vanemat! Kontrolli " + sb);
            }
         }
         else if (item.equals(",")) {
            root.nextSibling = treebuild(new Node(null, null, null), items);
            if (root.name == null) {
               throw new RuntimeException("empty,  OR   ,empty OR ,," + "  Kontrolli " + sb);
            }
            return root;
         } else if (item.equals(")")) {
            if (root.name == null) {
               throw new RuntimeException("empty(X) OR X(empty) OR (empty)X OR (X)empty OR empty(empty)"+ "  Kontrolli " + sb );
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
   public static Node puuEhitus(Node root, int open) {
      StringBuilder stringBuilder = new StringBuilder();
      if (root.firstChild != null) {
         open++;
         stringBuilder.append("(");
         stringBuilder.append(puuEhitus(root.firstChild, open).name);
         stringBuilder.append(")");
      }

      if (root.nextSibling != null) {
         stringBuilder.append(",");
         stringBuilder.append(puuEhitus(root.nextSibling, open).name);
      }
      root.name += stringBuilder.toString();
      return root;
   }

   public String leftParentheticRepresentation() {
      String text = puuEhitus(this, 0).name;
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
      String s = "(B1(ff),C)A";
      Node t = Node.parsePostfix (s);
      String v = t.leftParentheticRepresentation();
      System.out.println (s + " ==> " + v); // (B1,C)A ==> A(B1,C)
   }

   private static String sb = null;
}