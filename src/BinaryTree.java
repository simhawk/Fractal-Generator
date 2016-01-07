
public class BinaryTree {
	
	private class Node {
		public int data;
		public Node left;
		public Node right;
		public Node parent;
		 
		public Node() {
			this(0);
		}
		
		public Node(int data) {
			this.data = data;
			left = right = parent = null;
		}
	}
	
	private Node root;
	private int size;
	
	
	public BinaryTree () {
		this.size = 0;
		this.root = null;
	}
	
	public boolean exists(int val) {
		return exists(root, val);
	}
	
	public boolean exists(Node node, int val) {
		if(node == null) {
			return false;
		} else if(node.data > val) {
			return exists(node.left, val);
		} else if(node.data < val) {
			return exists(node.right, val);
		}
		return true;
	}
	
	public void insert(int val) {
		void insert(root, val);
	}
	
	public void inesert(Node node, int val) {
		if(node == null) {
			node = new Node(val);
		} else if(node.data > val) {
			insert(node.left, val);
		} else if(node.data < val) {
			insert(node.right, val);
		} 
	}
	
	
	
	
	
}
