import java.util.*;
//Name :
//Student ID :

@SuppressWarnings("unchecked")
class BST <T extends KeyValue> {

	class TreeNode <U extends KeyValue> {
		U data;	// storage for data : in HW 3, T will be Item
		TreeNode<U> leftChild;	// link to the left Child
		TreeNode<U> rightChild;	// link to the right Child

		// constructors come here
		TreeNode() {
			leftChild = rightChild = null;
		}
		TreeNode(U d) {
			// data is given
			data = d;
			// the leftChild and rightChild field are null
			leftChild = rightChild = null;
		}
	};

	TreeNode <T> root;// the reference to the root node
	int treeHeight; //내가 넣음
	int nodeCount;
	int preTreeHeight;

	BST() { 
		// BST constructor. 
		root = null;
		treeHeight = 0; //내가 넣
		nodeCount = 0;
		preTreeHeight = 0;
	}

 void Show() {
		System.out.print( "Pre  Order : ");
		PreOrder(root);
		System.out.println("");
		System.out.print("In   Order : ");
		InOrder(root);
		System.out.println("");
		System.out.print("Post Order : ");
		PostOrder(root);
		System.out.println("");
		System.out.print("Count      : ");
		System.out.print( Count(root));
		System.out.println("");
		System.out.print("Height      : ");
		System.out.println( Height(root));
		System.out.println("");
	}


	// IMPLEMENT THE FOLLOWING FUNCTIONS
 
 
	boolean Delete(T item)  {
		// non existing key
		if(root == null)
			return false;
		/*item의 key가 root의 key보다 크면 오른쪽
		 *작으면 왼쪽으로 기준을 옮긴다*/
		// 안에 존재하는 경우
		if(root.data.GetKey() == item.GetKey())
		{ // root인경우 (왼+오/왼/오/무)
			if(root.rightChild !=null)
			{ // 왼+오 - 오
				TreeNode<T> theNode = root.rightChild;
				TreeNode<T> parent = root;
				if(theNode.leftChild == null)
				{ // 오른쪽이 존재하지 않는 경우
					root.data = theNode.data;
					root.rightChild = theNode.rightChild;
					return true;
				}
				while(theNode.leftChild != null)
				{ // 오른쪽이 존재한경우
					parent = theNode;
					theNode = theNode.leftChild;
				}
				root.data = theNode.data;
				parent.leftChild = theNode.rightChild;
				return true;
			}
			else if(root.rightChild == null && root.leftChild !=null)
			{ // 왼
				root = root.leftChild;
				return true;
			}
			else if(root.leftChild == null && root.rightChild ==null)
			{ // 무
				root = null;
				return true;
			}
		}
		else
		{ // root가 아닌 경우 (왼+오/왼/오/무)
			TreeNode<T> theNode = root;
			TreeNode<T> parent = theNode;
			while (theNode.data.GetKey() != item.GetKey())
			{
				if(theNode.data == null) return false; // 안에 존재하지 않는 경우
				if(theNode.data.GetKey() < item.GetKey()) 
				{
					parent = theNode;
					if(theNode.rightChild == null) return false; // 안에 존재하지 않는 경우
					theNode = theNode.rightChild;
				}
				else if(theNode.data.GetKey() > item.GetKey())
				{
					parent = theNode;
					if(theNode.leftChild == null) return false; // 안에 존재하지 않는 경우
					theNode = theNode.leftChild;
				}
			}
			
//			System.out.println("theNode is "+theNode.data.GetKey());
//			if(theNode.rightChild.leftChild !=null)
//			{ // 사이에 값이 있는 경우
//				TreeNode<T> findNode = theNode.rightChild.leftChild;
//				TreeNode<T> findParent = theNode.rightChild;
//				if(findNode.leftChild == null)
//				{
//					theNode.data = findNode.data;
//					findParent.leftChild = null;
//					return true;
//				}
//				while(findNode.leftChild !=null)
//				{
//					findParent = findNode;
//					findNode = findNode.leftChild;
//				}
//				theNode.data = findNode.data;
//				findParent.leftChild = null;
//				return true;
//			}
			if(theNode.rightChild !=null)
			{ // 왼+오 - 오
				TreeNode<T> findNode = theNode.rightChild;
				TreeNode<T> findParent = theNode;
				
				if(theNode.leftChild == null)
				{ // 왼쪽이 존재하지 않는 경우 
					if(parent.leftChild == theNode)
					{
						parent.leftChild = findNode;
					}
					else
					{
						parent.rightChild = findNode;
					}
//					theNode.data = findNode.data;
//					theNode.leftChild = findNode.leftChild;
					return true;
				}
				
				while(findNode.leftChild != null)
				{ // 왼쪽이 존재한경우
					findParent = findNode;
					findNode = findNode.leftChild;
				}
//				System.out.println("findNode is : "+findNode.data.GetKey());
				theNode.data = findNode.data;
				findParent.leftChild = findNode.rightChild;
				return true;
			}
			else if(theNode.rightChild == null)
			{ 
				if(theNode.leftChild == null)
				{ // 밑에 없음
					if(theNode == parent.rightChild)
					{
						parent.rightChild = null;
						return true;
					}
					else if(theNode == parent.leftChild)
					{
						parent.leftChild = null;
						return true;
					}
				}
				else if(theNode.leftChild != null)
				{ // 왼쪽만 있음
					if(theNode == parent.rightChild)
					{
						parent.rightChild = theNode.leftChild;
						return true;
					}
					else if(theNode == parent.leftChild)
					{
						parent.leftChild = theNode.leftChild;
						return true;
					}
				}
			}
		}
		return false;
	}

 
	boolean  Insert(T item)  {
		// first search the key
		TreeNode<T> theNode = new TreeNode<T>(item); // 들어온 item에 대해 새로운 node 만들기
		if(root == null) {
			root = theNode;
			return true;
		}
		TreeNode<T> ptr, parent;
		// 내가 넣기
		ptr = parent = root;

		while (parent.leftChild != theNode && parent.rightChild != theNode) 
		{ // 새로운 노드 추가하기 전까지
			if(ptr.rightChild != null && ptr.leftChild != null)
			{ // 밑에 노드가 양쪽다 있는 경우
				int dataKey = ptr.data.GetKey();
				int itemKey = item.GetKey();
				
				if(dataKey < itemKey)
				{ // 들어온것이 오른쪽 값보다 큰 경우 -> 오른쪽으로 이동(ptr)
					parent = ptr;
					ptr = ptr.rightChild;
				}
				else if(dataKey > itemKey)
				{ // 들어온것이 왼쪽 값보다 작은 경우 -> 왼쪽으로 이동(ptr)
					parent = ptr;
					ptr = ptr.leftChild;
				}
				else 
				{ // 키값이 일치한 경우 기존의 노드를 없애고 다시 넣는다
					if(parent.leftChild == ptr)
					{
						theNode.leftChild = ptr.leftChild;
						theNode.rightChild = ptr.rightChild;
						parent.leftChild = theNode;
					}
					else if (parent.rightChild == ptr)
					{
						theNode.leftChild = ptr.leftChild;
						theNode.rightChild = ptr.rightChild;
						parent.rightChild = theNode;
					}
					else
					{
						theNode.rightChild = root.rightChild;
						theNode.leftChild = root.leftChild;
						root = theNode;
					}
					return false;
				}
			}
			else if(ptr.rightChild != null && ptr.leftChild == null)
			{ // 왼쪽에 노드가 없는 경우
				int dataKey = ptr.data.GetKey();
				int itemKey = item.GetKey();
				if(dataKey < itemKey)
				{
					parent = ptr;
					ptr = ptr.rightChild;
				}
				else if(dataKey > itemKey) ptr.leftChild = theNode;
				else
				{
					if(parent.leftChild == ptr)
					{
						parent.leftChild = theNode;
						theNode.rightChild = ptr.rightChild;
					}
					else if (parent.rightChild == ptr)
					{
						parent.rightChild = theNode;
						theNode.rightChild = ptr.rightChild;
					}
					else
					{
						theNode.rightChild = root.rightChild;
						theNode.leftChild = root.leftChild;
						root = theNode;
					}
//					ptr = theNode;
					return false;
				}
			}
			else if(ptr.rightChild == null && ptr.leftChild != null)
			{ // 오른쪽에 노드가 없는 경우
				int dataKey = ptr.data.GetKey();
				int itemKey = item.GetKey();
				if(dataKey > itemKey)
				{
					parent = ptr;
					ptr = ptr.leftChild;
				}
				else if(dataKey < itemKey) ptr.rightChild = theNode;
				else
				{	
					if(parent.leftChild == ptr)
					{
						theNode.leftChild = ptr.leftChild;
						parent.leftChild = theNode;
					}
					else if (parent.rightChild == ptr)
					{ 
						theNode.leftChild = ptr.leftChild;
						parent.rightChild = theNode;
					}
					else
					{
						theNode.rightChild = root.rightChild;
						theNode.leftChild = root.leftChild;
						root = theNode;
					}
					return false;
				}
			}
			else
			{ // 밑에 노드가 없는 경우
				if(ptr.data.GetKey() < item.GetKey()) 
				{
					if (root == null)
					{
						root = theNode;
					}
					ptr.rightChild = theNode;
				}
				else if(ptr.data.GetKey() > item.GetKey()) ptr.leftChild = theNode;
				else 
				{
					if(parent.leftChild == ptr)
					{
						parent.leftChild = theNode;
					}
					else if (parent.rightChild == ptr)
					{
						parent.rightChild = theNode;
					}
					else
					{
						theNode.rightChild = root.rightChild;
						theNode.leftChild = root.leftChild;
						root = theNode;
					}
					return false;
				}
			}
		}
		// 내가 넣기
		return true;
	}

	T Get(T item)  {
		// use the key field of item and find the node
		// do not use val field of item
		TreeNode<T> ptr;
		ptr = root;
		
		// 내가 넣기
		while(ptr.data.GetKey() != item.GetKey())
		{
//			System.out.println("ptr : "+ ptr.data.GetKey());
			if(ptr.rightChild == null && ptr.leftChild == null) return null;
			if(ptr.rightChild == null && ptr.data.GetKey() < item.GetKey()) return null;
			if(ptr.leftChild == null && ptr.data.GetKey() > item.GetKey()) return null;
			if(ptr.data.GetKey() < item.GetKey())
			{
				ptr = ptr.rightChild;
			}
			else if(ptr.data.GetKey() > item.GetKey())
			{
				ptr = ptr.leftChild;
			}
		}
		return ptr.data;
		// 내가 넣기
	} 

	void  PreOrder(TreeNode<T> t)  {
		// parent - left - right
		if(t == null) {}
		else if(t.data != null)
		{
			System.out.print(t.data.GetKey() + "(" + t.data.GetValue() + ") " );
			if (t.leftChild != null) PreOrder(t.leftChild);
			if (t.rightChild != null) PreOrder(t.rightChild);
		}
		else System.out.print("");
		nodeCount = 0;
	}

	void  InOrder(TreeNode<T> t)  {
		// left - parent - right
		if(t == null) {}
		else if(t.data != null)
		{
			if (t.leftChild != null) InOrder(t.leftChild);
			System.out.print(t.data.GetKey() + "(" + t.data.GetValue() + ") " );
			if (t.rightChild != null) InOrder(t.rightChild);
		}
		else System.out.print("");
	}

	void  PostOrder(TreeNode<T> t)  {
		// left - right - parent
		if(t == null) {}
		else if(t.data != null)
		{
			if (t.leftChild != null) PostOrder(t.leftChild);
			if (t.rightChild != null) PostOrder(t.rightChild);
			System.out.print(t.data.GetKey() + "(" + t.data.GetValue() + ") " );
		}
		else System.out.print("");
	}

	int  Count(TreeNode<T> t)  {
		//  how many node
		if(t == null) {}
		else if(t.data != null)
		{
			nodeCount++;
			if (t.leftChild != null) Count(t.leftChild);
			if (t.rightChild != null) Count(t.rightChild);
		}
		
		treeHeight = 0;
		preTreeHeight = 0;
		return nodeCount;
	}
	

	int  Height(TreeNode<T> t)  {
		// = 밑으로 내려가면 ++ 위로 올라가면 -- 더이상 내려갈 곳이 없는 경우
		// -- 하기 전에 따로 저장하기 # 따로 저장한것이랑 크기비교해서 큰것 저장
		if(t == null) {}
		else if(t.data != null)
		{
			treeHeight++;
			if (t.leftChild != null) Height(t.leftChild);
			if (t.rightChild != null) Height(t.rightChild);
			if(preTreeHeight < treeHeight) preTreeHeight = treeHeight;
			treeHeight --;
			
		}
		
		return preTreeHeight;
	}
	
}

