
public interface BinaryTreeInterface<T> extends TreeInterface<T>, 
                                                TreeIteratorInterface<T>
{
   public void setRootData(T rootData);  
   public void setTree(T rootData, BinaryTreeInterface<T> leftTree,
                                   BinaryTreeInterface<T> rightTree);
} 
