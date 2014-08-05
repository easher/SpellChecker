
import java.util.*;
//http://users.cis.fiu.edu/~weiss/dsaajava3/code/
//Class originally private in AvlTree.java
//Node counting methods were added to the file
public class AvlNode<AnyType>
{
        AnyType           element;      // The data in the node
        AvlNode<AnyType>  left;         // Left child
        AvlNode<AnyType>  right;        // Right child
        int               height;       // Height
        LinkedList<Integer> lineNumber;


            // Constructors
        AvlNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        AvlNode( AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
            height   = 0;
            lineNumber = new LinkedList();
        }
        AvlNode(AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt, Integer lineN){
            element  = theElement;
            left     = lt;
            right    = rt;
            height   = 0;
            lineNumber = new LinkedList();
            lineNumber.add(lineN);

        }

    public int countNodes(){
        return countNodes(this);
    }
    public int countLeaves(){
        return countLeaves(this);
    }

    public int countFullNodes(){
        return countFullNodes(this);
    }
    private int countNodes(AvlNode<AnyType> t){
     if(t.right != null && t.left != null)
        return (countNodes(t.left) + countNodes(t.right) + 1);
     else if(t.right == null && t.left == null)
        return 1;
     else if(t.left != null)
        return 1+ countNodes(t.left);
     else
        return 1 + countNodes(t.right);
    }
    
    private int countLeaves(AvlNode<AnyType> t){
     if(t.right != null && t.left != null)
        return (countLeaves(t.right) + countLeaves(t.left));   
    else if(t.left == null && t.right == null)
        return 1;
    else if(t.left != null)
        return countLeaves(t.left);
    else
        return countLeaves(t.right);
    }  
    private int countFullNodes(AvlNode<AnyType> t){
        if(t.left != null && t.right != null)
            return (1 + countFullNodes(t.left) + countFullNodes(t.right));
        else if(t.left != null)
            return countFullNodes(t.left);
        else if(t.right != null)
            return countFullNodes(t.right);
        else 
            return 0; //leaf node 
    }






}   