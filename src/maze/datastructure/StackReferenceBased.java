package maze.datastructure;
public class StackReferenceBased implements StackInterface
{
  private Node top;

  public StackReferenceBased()
  {
    top = null;
  }  // end default constructor
  //============================================================================
  //============================================================================
  //============================================================================

  public boolean isEmpty()
  {
    return top ==  null;
  }  // end isEmpty
//============================================================================
//============================================================================
//============================================================================

  public void push(Object newItem)
  {
    top = new Node(newItem, top);
  }  // end push
  //============================================================================
  //============================================================================
  //============================================================================

  public Object pop() throws StackException
  {
    if (!isEmpty())
    {
      Node temp = top;
      top = top.getNext();
      return temp.getItem();
    }
    else
    {
      throw new StackException("StackException on " + "pop: stack empty");
    }  // end if
  }  // end pop
  //============================================================================
  //============================================================================
  //============================================================================

  public void popAll()
  {
    top = null;
  }  // end popAll

//============================================================================
//============================================================================
//============================================================================
  public Object peek() throws StackException
  {
    if (!isEmpty())
    {
      return top.getItem();
    }
    else
    {
      throw new StackException("StackException on " + "peek: stack empty");
    }  // end if

  } // end peek
//============================================================================
//============================================================================
//============================================================================
  public void displayStack() {
	  if(isEmpty())
	  {
		  System.out.println("Stack is empty");
		  return;
	  }
	  for(Node curr = top; curr != null;curr = curr.getNext()) 
	  {
		  if (curr == top)
		  {
			  System.out.println(curr.getItem() + " <--top");
		  }
		  else
		  {
			  System.out.println(curr.getItem());
		  }
		  
	  }
	  
  }
}  // end StackReferenceBased