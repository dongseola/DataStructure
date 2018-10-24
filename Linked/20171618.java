import java.util.*;

// Name : Tong Seol a
// Student ID : 20171618

class DblList <T> {
	class DblListNode <U> {
		private U data;	// storage for data
		private DblListNode<U> left;	// link to the left node
		private DblListNode<U> right;	// link to the right node

		// constructors come here
		DblListNode() {
			this.right = this;
			// the right field contains the DblListNode itself
		}
		DblListNode(U data) {
			// set the data field 
			this.data = data;
		}
		DblListNode(U data, DblListNode<U> right) {
			// set the data field and right field
			this.data = data;
			this.right = right;
		}
	};

	private DblListNode<T> first; // reference to the dummy head node
	private DblListNode<T> current; // current position of the hand
	private DblListNode<T> cutnode; // Node that has been cut


	DblList() { 
		// DblList constructor. Create a dummy head node

		first = new DblListNode<T>(); 
		first.right = first.left = first;
		current = first; // current position of the hand
		cutnode = null; // Node that has been cut
	}


	/**
	 * Show all the elements in DblList
	 */
	
	public String toString() {
	//	cerr << "NEED TO IMPLEMENT" << endl;
		DblListNode<T> moveNode = first.right;
		String str = new String();
		str = "";
		while(true) {
			if (moveNode == current) {
				if (current.data == null) break;
				str += ("[" + moveNode.data +"] ");
			}
			else	str +=(moveNode.data + " ");
			moveNode = moveNode.right;
			if(moveNode == first) break;
		}
		
		String cutStr = new String();
		cutStr = "";
		if (cutnode != null) cutStr +=(": " + cutnode.data);
		
		str += cutStr;
		
		return str;
	}


	boolean  PutLeft()  {
	//	cerr << "NEED TO IMPLEMENT" << endl;
		if (cutnode == null) return false;
		
		DblListNode<T> cutNode = new DblListNode<T>(cutnode.data);
		cutnode = null;
		
		if(current == first) {
			cutNode.right = first;
			cutNode.left = first;
			first.left = cutNode;
			first.right = cutNode;
		}
		else {
			cutNode.right = current;
			cutNode.left = current.left;
			current.left.right = cutNode;
			current.left = cutNode;
		}
		current = cutNode;
		return true;
	}



	boolean  PutRight()  {
		//	cerr << "NEED TO IMPLEMENT" << endl;
		if (cutnode == null) return false;
		
		DblListNode<T> cutNode = new DblListNode<T>(cutnode.data);
		cutnode = null;
		
		if(current == first) {
			cutNode.right = first;
			cutNode.left = first;
			first.left = cutNode;
			first.right = cutNode;
		}
		else {
			cutNode.right = current.right;
			cutNode.left = current;
			current.right.left = cutNode;
			current.right = cutNode;
		}
		current = cutNode;
		return true;

	}


	boolean  MoveLeft()  {
	//	cerr << "NEED TO IMPLEMENT" << endl;
		if (first.right == first.left) return false;
		if (current.left == first) return false;
		
		current = current.left;
		return true;
	}


	boolean  MoveRight()  {
		//	cerr << "NEED TO IMPLEMENT" << endl;
		if (first.right == first.left) return false;
		if (current.right == first)  return false;
		
		current = current.right;
		return true;
	}
	
	void  MoveEnd()  {
		//	cerr << "NEED TO IMPLEMENT" << endl;
		if (first.left == cutnode) current = first.left.left;
		else current = first.left;
	}


	boolean  Cut()  {
		if (first.right == first) return false;
		if (cutnode != null) return false;
		
		cutnode = new DblListNode<T>(current.data);
		
		if (current.left == current.right) {
//			System.out.println("------------this is 1 element------------");
			first.right = first.left = first;
			current = first; // current position of the hand
		}
		else if(current.right == first) {
			current = current.left;
			
			current.right.right.left = current;
			current.right = current.right.right;
		}
		else {
			current = current.right;
			
			current.left.left.right = current;
			current.left = current.left.left;
		}
		
		return true;
	}
	
	void Reverse() { 
		// Reverse the list
		//	cerr << "NEED TO IMPLEMENT" << endl;
		if (first.right != first) {
			// 원소가 없다
			if (first.right != first.left) {
				// 원소가 하나
				DblListNode<T> revNode = first.left.left;
				while(true) {
					DblListNode<T> newNode = new DblListNode<T>(revNode.data);
					// 만약 current를 newNode로 바꾼다면 그것을 current로 설정한다
					newNode.right = first;
					newNode.left = first.left;
					first.left.right = newNode;
					first.left = newNode;
					
					if(revNode == current) {
						current = newNode;
					}
					
					revNode = revNode.left;
					
					revNode.right.right.left = revNode;
					revNode.right = revNode.right.right; 
					
					if (revNode == first) break;
				}
			}
		}
	}

	void  InsertBack(final T e) {	
		// insert the element into the back of the list
		//	cerr << "NEED TO IMPLEMENT" << endl;
		DblListNode<T> theNode = new DblListNode<T>(e);
		
		theNode.right = first;
		theNode.left = first.left;
		first.left.right = theNode;
		first.left = theNode;
		
		current = theNode;
	}

	boolean equals(DblList<T> dbl) { 
		// check whether the two lists are equal
		//	cerr << "NEED TO IMPLEMENT" << endl;
		if(dbl.cutnode != null) return false;
		if (this.first.left.data != dbl.first.left.data) return false;
		DblListNode<T> orgP = this.first;
		DblListNode<T> scrP = dbl.first;
		while(true) {
			orgP = orgP.right;
			scrP = scrP.right;
			if(orgP == this.first) break;
			
			if (orgP.data.equals(scrP.data)) continue;
			else return false;
		}
		return true;
	}

}

