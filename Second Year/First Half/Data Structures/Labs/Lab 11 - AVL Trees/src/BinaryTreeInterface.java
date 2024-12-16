public interface BinaryTreeInterface<T> extends TreeInterface<T>, 
                                                TreeIteratorInterface<T>
{
   /** Sets the data in the root of this binary tree.
       @param rootData  The object that is the data for the tree's root.
   */
   public void setRootData(T rootData);

   /** Sets this binary tree to a new binary tree.
      @param rootData   The object that is the data for the new tree's root.
      @param leftTree   The left subtree of the new tree.
      @param rightTree  The right subtree of the new tree. */
   public void setTree(T rootData, BinaryTreeInterface<T> leftTree,
                                   BinaryTreeInterface<T> rightTree);
} // end BinaryTreeInterface
