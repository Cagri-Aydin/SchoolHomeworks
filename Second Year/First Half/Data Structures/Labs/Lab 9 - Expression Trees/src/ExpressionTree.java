public class ExpressionTree extends BinaryTree<String> implements ExpressionTreeInterface {
	public ExpressionTree() {
	}

	public ExpressionTree(String rootData) {
		super(rootData);
	}

	public double evaluate() {
		return evaluate(getRootNode());
	}

	private double evaluate(BinaryNode<String> rootNode) {
		double result = 0;

		if (rootNode == null)
			result = 0;
		else if (rootNode.isLeaf()) {
			String variable = rootNode.getData();
			result = Integer.parseInt(variable);
		} else {
			
		   // calculate the result for nonleaf nodes (Hint: use recursive function) 
    	   // and use "compute" function in the final step

		}

		return result;
	}

	private double compute(String operator, double firstOperand, double secondOperand) {
		double result = 0;

		if (operator.equals("+"))
			result = firstOperand + secondOperand;
		else if (operator.equals("-"))
			result = firstOperand - secondOperand;
		else if (operator.equals("*"))
			result = firstOperand * secondOperand;
		else if (operator.equals("/"))
			result = firstOperand / secondOperand;

		return result;
	}

	public void displayPrefixExpression() {
		BinaryNode<String> rootNode = (BinaryNode<String>) getRootNode();
		System.out.println("preorder:");
		preorder(rootNode);
		System.out.println();
	}

	public void displayPostfixExpression() {
		BinaryNode<String> rootNode = (BinaryNode<String>) getRootNode();
		System.out.println("postorder:");
		rootNode = (BinaryNode<String>) getRootNode();
		postorder(rootNode);
		System.out.println();
	}

	private void postorder(BinaryNode<String> rootNode) {
	
		// This function should print the tree in the postorder
	
	}

	private void preorder(BinaryNode<String> rootNode) {
	
		// This function should print the tree in the preorder
		
	}
}
