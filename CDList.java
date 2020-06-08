package CDlist;

import java.util.logging.Level;
import java.util.logging.Logger;

// Author: Christian Vincent
// Date last edited: 2/6/2019

public class CDList<E> implements Cloneable {
    //--------------nested node class------------
    private static class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }
        public E getElement() { return element; }
        public Node<E> getPrev() { return prev; }
        public Node<E> getNext() { return next; }
        public void setPrev(Node<E> p) { prev = p; }
        public void setNext(Node<E> n) { next = n; }
        public String toString() {
          return "(" + prev.getElement() +"," + element + "," + next.getElement() +")";
        }
    }
    //---------------end of nested node class------------

    //Member data
    private Node<E> tail = null;
    private int size = 0;

    //Member Methods
    public CDList() { 
      //default contructor
     
      
    }
    public CDList(CDList<E> cdl) {
      //the copy constructor
     this.tail = cdl.tail;
     Node<E> walk = cdl.tail.getNext();
    for(int i = 0; i < cdl.size(); i++){
         addLast(walk.getElement());
         walk = walk.getNext();
    }
     
    }
    
  
    public int size() {
      //returns the size of the list
      return size;
    }
    public boolean isEmpty() {
      //checks if the list is empty
      return size == 0;
    }
    public E first() {
      //return the first element of the list
      if (isEmpty()) 
          return null;
      return tail.getNext().getElement();
    }
    public E last() {
      //return the last element of the list
      if (isEmpty()) 
          return null;
      return tail.getElement();
    }
    public void rotate() {
      //rotate the first element to the back of the list
      tail = tail.getNext();
    }
    public void rotateBackward() {
      //rotate the last element to the front of the list
      tail = tail.getPrev();
    }
    public void addFirst(E e) {
      //add element e to the front of the list
      Node<E> newest = new Node(e, null, null);
      if(size == 0)
      {
          newest.setPrev(newest);
          newest.setNext(newest);
          tail = newest;
      }
      else
      {
          newest.setPrev(tail);
          tail.getNext().setPrev(newest);
          newest.setNext(tail.getNext());
          tail.setNext(newest);
      }
      
      size++;
    }
    public void addLast(E e) {
      //add element e to the back of the list
      addFirst(e);
      tail = tail.getNext();

    }
    
    public E removeFirst() {
      //remove and return the element at the front of the list
      if(isEmpty()) 
          return null;
      
      Node<E> removedNode = tail.getNext();
      Node<E> predecessor = removedNode.getPrev();
      Node<E> successor = removedNode.getNext();
      predecessor.setNext(successor);
      successor.setPrev(predecessor);
      size--;
      
      return removedNode.getElement();          
    }
    public E removeLast() {
      //remove and return the element at the back of the list
      Node<E> removedNode = tail;
      Node<E> predecessor = tail.getPrev();
      Node<E> successor = tail.getNext();
      predecessor.setNext(successor);
      successor.setPrev(predecessor);
      tail = tail.getPrev();
      size--;
      
      return removedNode.getElement();
      
    }
    
    
    public CDList<E> clone() {
       //clone and return the new list(deep clone)     
       CDList<E> other = new CDList<>();
       
       Node<E> walk = tail.getNext();
       for(int i = 0; i < size; i++) {
           other.addLast(walk.getElement());
           walk = walk.getNext();
       }
        
        return other; 
    }
    

    @Override
    public boolean equals(Object o) {
      //check if the current instance and o are from the same class, have the same type
      //and have the same sequence of elements despite 
      //having possibly different starting points.
      
      if(o == null) 
          return false;
      
      if(getClass()!= o.getClass())
          return false;

      CDList<E> other =  (CDList<E>)o;
      
      if(size != other.size())
          return false;
      
      Node<E> walkA = tail;
      Node<E> walkB = other.tail;
      while(walkA != tail.getPrev() && walkB != other.tail.getPrev()) {
          if(!walkA.getElement().equals(walkB.getElement()))
              return false;
          walkA = walkA.getNext();
          walkB = walkB.getNext();
      }
      return true;
      
    }
      
    public void attach(CDList cdl) {
      //insert cdl after the tail of the current list
      
      Node<E> other = cdl.tail.getNext();
      for(int i = 0; i < cdl.size; i++) {
          addLast(other.getElement());
          other = other.getNext();
      }
      
         
      }
    public void removeDuplicates() {
      //remove all elements that happen more than once in the list. If the tail gets //deleted, the element immediately before the tailÂ will become the new tail.
      Node<E> prevNode = tail;
      Node<E> currNode = tail.getNext();
      
           for(currNode = currNode; currNode != tail; currNode = currNode.getNext()) { 
               
               //check if duplicate before prevNode is iterated
                if((prevNode.getElement() == currNode.getElement()) && !(prevNode).equals(currNode)) {
                   if(prevNode.equals(tail)) 
                   {
                      removeLast();
                   }
                  else
                   {
                     currNode.getPrev().setNext(currNode.getNext());
                     currNode.getNext().setPrev(currNode.getPrev());
                   }  
                }
               
              for(prevNode = prevNode; prevNode != tail.getPrev() ; prevNode = prevNode.getNext()) {
                if((prevNode.getElement() == currNode.getElement()) && !(prevNode).equals(currNode)) {
                   if(prevNode.equals(tail)) 
                    {
                        removeLast();
                    }
                   else
                   {
                        currNode.getPrev().setNext(currNode.getNext());
                        currNode.getNext().setPrev(currNode.getPrev());
                    }  
                } 
            }
    }
}
    
      
    public void printList() {
      //prints all elements in the list
      if (size == 0)
        System.out.println("List is empty.");
      else {
        Node<E> walk = tail.getNext();

        while (!(walk.getNext().equals(tail.getNext()))) {
          System.out.print(walk.toString() + ", ");
          walk = walk.getNext();
        }
        System.out.println(walk.toString());
      }
    }
} 
