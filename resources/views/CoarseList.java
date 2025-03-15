/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.coarselisttest;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * List using coarse-grained synchronization.
 * @param T Item type.
 * @author Maurice Herlihy
 * (minor refactoring by Emanuel Onica)
 */
public class CoarseList<T> {
  /**
   * First list Node
   */
  private Node head;
  /**
   * Last list Node
   */
  private Node tail;
  /**
   * Synchronizes access to list
   */
  private Lock lock = new ReentrantLock();
  
  /**
   * Constructor
   */
  public CoarseList() {
    // Add sentinels to start and end
    head  = new Node(Integer.MIN_VALUE);
    tail  = new Node(Integer.MAX_VALUE);
    head.next = this.tail;
  }
  
  /**
   * Add an element.
   * @param item element to add
   * @return true iff element was not there already
   */
  public boolean add(T item) {
 
    Node pred, current;
    int key = item.hashCode();

    lock.lock();
    try {
      pred = head;
      current = pred.next; 
      while (current.key < key) {
        pred = current;
        current = current.next;
      }
      if (key == current.key) {
        return false;
        //In acest moment, operatia decide că nu poate modifica lista, iar aceasta rămâne neschimbată.
      } else {
        Node node = new Node(item);
        node.next = current;
        pred.next = node;
        //In acest moment elementul este adaugat in lista, iar alte thread-uri care acceseaza structura vor vedea acest nod nou
        return true;
      }
    } finally {
      lock.unlock();
    }
  }

  /**
   * Remove an element.
   * @param item element to remove
   * @return true iff element was present
   */
  public boolean remove(T item) {

    Node pred, current;
    int key = item.hashCode();

    lock.lock();
    try {
      pred = this.head;
      curr = pred.next;
      while (current.key < key) {
        pred = current;
        current = current.next;
      }
      if (key == current.key) {  // present
        pred.next = current.next;
        return true;
      } else {
        return false;         // not present
      }
    } finally {               // always unlock
      lock.unlock();
    }
  }

  /**
   * Test whether element is present
   * @param item element to test
   * @return true iff element is present
   */
  public boolean contains(T item) {
    Node pred, current;
    int key = item.hashCode();
    lock.lock();
    try {
      pred = head;
      current = pred.next;
      while (current.key < key) {
        pred = current;
        current = current.next;
      }
      return (key == current.key);
    } finally {               // always unlock
      lock.unlock();
    }
  }

  /**
   * list Node
   */
  private class Node {
    /**
     * actual item
     */
    T item;
    /**
     * item's hash code
     */
    int key;
    /**
     * next Node in list
     */
    Node next;
    /**
     * Constructor for usual Node
     * @param item element in list
     */
    Node(T item) {
      this.item = item;
      this.key = item.hashCode();
    }
    /**
     * Constructor for sentinel Node
     * @param key should be min or max int value
     */
    Node(int key) {
      this.item = null;
      this.key = key;
    }
  }
}
