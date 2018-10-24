import java.util.*;

// Name :
// Student ID :

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

	boolean  Insert(T item)  {
		// first search the key
		if(root == null) {
			TreeNode<T> theNode = new TreeNode<T>(item);
			root = theNode;
			return true;
		}
		TreeNode<T> ptr, parent;
		// 내가 넣기
		TreeNode<T> theNode = new TreeNode<T>(item); // 들어온 item에 대해 새로운 node 만들기
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
				if(ptr.data.GetKey() < item.GetKey()) ptr.rightChild = theNode;
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
		while (ptr.data != null)
		{
			if(ptr.data.GetKey() < item.GetKey())
			{
				ptr = ptr.rightChild;
			}
			else if(ptr.data.GetKey() > item.GetKey())
			{
				ptr = ptr.leftChild;
			}
			else if(ptr.data.GetKey() == item.GetKey())
			{
				return ptr.data;
			}
			else
			{
				return null;
			}
		}
		// 내가 넣기
		return null;
	} 


	boolean Delete(T item)  {
		if(root == null)
			return false;	// non existing key

		// 내가 넣기
		TreeNode<T> ptr, parent;
		ptr = parent = root;
		// left right가 둘다 null인경우 그냥 없애기
		// 있는 경우 우의 것이랑 바꾸기
		if(ptr.data.GetKey() < item.GetKey())
		{
			parent = ptr;
			ptr = ptr.rightChild;
		}
		else if(ptr.data.GetKey() > item.GetKey())
		{
			parent = ptr;
			ptr = ptr.leftChild;
		}
		else
		{
			if(ptr.leftChild == null && ptr.rightChild == null)
			{ // 밑에 없는경우
				parent.rightChild = null;
				parent.leftChild = null;
			}
			else if (ptr.leftChild == null && ptr.rightChild != null)
			{ // 왼쪽은 없고 오른쪽만 있는 경우
				if(ptr == parent.leftChild)
				{// 이전에 왼쪽으로 내려옴
					parent.leftChild = ptr.rightChild;
				}
				else if(ptr == parent.rightChild)
				{// 이전에 오른쪽으로 내려옴
					parent.rightChild = ptr.rightChild;
				}
			}
			else if(ptr.leftChild != null && ptr.rightChild == null)
			{ // 오른쪽은 없고 왼쪽만 있는 경우
				if(ptr == parent.leftChild)
				{
					parent.leftChild = ptr.leftChild;
				}
				else if(ptr == parent.rightChild)
				{
					parent.rightChild = ptr.leftChild;
				}
			}
			else
			{ // 양쪽다 있는 경우 - 왼쪽에서 가장 큰것을 가져온다
//				TreeNode<T> findMax = ptr.leftChild;
//				TreeNode<T> maxParent = ptr;
//				TreeNode<T> maxRight = findMax;
//				while(findMax.rightChild != null) 
//				{
//					maxParent = findMax;
//					findMax = findMax.rightChild;
//				}
//				if(findMax.rightChild != null)	maxRight = findMax.rightChild;
//				TreeNode<T> inputNode = new TreeNode<T>(findMax.data);
//				if(ptr == parent.leftChild)
//				{
//					parent.leftChild = maxParent;
//				}
//				else if(ptr == parent.rightChild)
//				{
//					parent.rightChild = ptr.leftChild;
//				}
			}
		}
		// 내가 넣기
		
		return true;
	}

	void  PreOrder(TreeNode<T> t)  {
		// parent - left - right
		if(t.data != null)
		{
			System.out.print(t.data.GetKey() + "(" + t.data.GetValue() + ") " );
			if (t.leftChild != null) PreOrder(t.leftChild);
			if (t.rightChild != null) PreOrder(t.rightChild);
		}
		nodeCount = 0;
	}

	void  InOrder(TreeNode<T> t)  {
		// left - parent - right
		if(t.data != null)
		{
			if (t.leftChild != null) InOrder(t.leftChild);
			System.out.print(t.data.GetKey() + "(" + t.data.GetValue() + ") " );
			if (t.rightChild != null) InOrder(t.rightChild);
		}
	}

	void  PostOrder(TreeNode<T> t)  {
		// left - right - parent
		if(t.data != null)
		{
			if (t.leftChild != null) PostOrder(t.leftChild);
			if (t.rightChild != null) PostOrder(t.rightChild);
			System.out.print(t.data.GetKey() + "(" + t.data.GetValue() + ") " );
		}
	}

	int  Count(TreeNode<T> t)  {
		//  how many node
		if(t.data != null)
		{
			nodeCount++;
			if (t.leftChild != null) Count(t.leftChild);
			if (t.rightChild != null) Count(t.rightChild);
		}
		
		treeHeight = 0;
		return nodeCount;
	}
	

	int  Height(TreeNode<T> t)  {
		// = 밑으로 내려가면 ++ 위로 올라가면 -- 더이상 내려갈 곳이 없는 경우
		// -- 하기 전에 따로 저장하기 # 따로 저장한것이랑 크기비교해서 큰것 저장
		if(t.data != null)
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
