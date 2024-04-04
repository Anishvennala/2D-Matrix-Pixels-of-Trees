public class QuadrantTree {
    QTreeNode root;

    public QuadrantTree (int[][] thePixels){

        int size = thePixels.length;
        root = buildTree(thePixels, 0, 0, size);

    }

    public QTreeNode getRoot(){
        return root;
    }

    private QTreeNode buildTree(int[][] pixels, int x, int y, int size) {
        QTreeNode node = new QTreeNode();
        node.setx(x);
        node.sety(y);
        node.setSize(size);

        // Calculate average color of the quadrant
        int averageColor = Gui.averageColor(pixels, x, y, size);
        node.setColor(averageColor);

        // Determine if the current node should have children
        if (size > 1) {
            int halfSize = size / 2;
            // Create children nodes recursively
            node.setChild(buildTree(pixels, x, y, halfSize), 0); // R1
            node.setChild(buildTree(pixels, x + halfSize, y, halfSize), 1); // R2
            node.setChild(buildTree(pixels, x, y + halfSize, halfSize), 2); // R3
            node.setChild(buildTree(pixels, x + halfSize, y + halfSize, halfSize), 3); // R4
        }

        return node;
    }

    public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel) {
        ListNode<QTreeNode> resultList = new ListNode<>(null); // Create an empty result list
        getPixelsRecursive(root, theLevel, 0, resultList); // Start recursion from the root
        return resultList.getNext(); // Return the list excluding the dummy node
    }

    // Recursive method to traverse the quadrant tree and get nodes at the specified level
    private void getPixelsRecursive(QTreeNode node, int targetLevel, int currentLevel, ListNode<QTreeNode> resultList) {
        if (node == null) {
            return; // Base case: reached leaf node or null node
        }

        if (currentLevel == targetLevel) {
            // Add the current node to the end of the result list
            ListNode<QTreeNode> newNode = new ListNode<>(node);
            ListNode<QTreeNode> current = resultList;
            while (current.getNext() != null) {
                current = current.getNext(); // Traverse to the last node
            }

            current.setNext(newNode);
            //resultList = resultList.getNext();
        } else if (currentLevel < targetLevel || node.isLeaf()) {
            // Recursively traverse children nodes if target level hasn't been reached or if it's a leaf node
            for (int i = 0; i < 4; i++) {
                getPixelsRecursive(node.getChild(i), targetLevel, currentLevel + 1, resultList);
            }
        }
    }

    private int getHeight(QTreeNode node) {

        //find root first
        QTreeNode parent = node;
        while(parent.getParent() != null){
            parent = parent.getParent();
        }
        //passing root and node
        return getHeightRec(parent, node);

    }

    private int getHeightRec(QTreeNode root, QTreeNode node) {
        if (root == null || node == null) {
            return 0; // Return 0 if either root or node is null
        }

        if (root == node) {
            return 0; // Return 1 if the node is the root of the tree
        }

        // Recursively calculate the height from the root to the node
        int parentHeight = getHeightRec(root, node.getParent());
        return parentHeight + 1; // Add 1 for the current node
    }




    public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
        ListNode<QTreeNode> resultList = new ListNode<>(null); // Initialize the result list
        findMatchingRecursive(r, theColor, theLevel, resultList); // Start the recursive traversal
        int count = countNodes(resultList.getNext()); // Count the number of nodes in the result list
        return new Duple(resultList.getNext(), count); // Return the result list and the count
    }

    private void findMatchingRecursive(QTreeNode node, int theColor, int theLevel, ListNode<QTreeNode> resultList) {
        if (node == null) {
            return; // Base case: reached leaf node or null node
        }

        // Check if the current node matches the criteria
        int nodeHeight = getHeight(node);
        if ((theLevel == 0 || nodeHeight == theLevel) && Gui.similarColor(node.getColor(), theColor)) {
            // Append the current node to the result list
            ListNode<QTreeNode> newNode = new ListNode<>(node);
            ListNode<QTreeNode> current = resultList;
            while (current.getNext() != null) {
                current = current.getNext(); // Traverse to the last node
            }
            current.setNext(newNode); // Append the new node
        }

        // Recursively traverse children nodes
        for (int i = 0; i < 4; i++) {
            if(!node.isLeaf()) {
                node.setChild(node.getChild(i), i);
                node.getChild(i).setParent(node);
                findMatchingRecursive(node.getChild(i), theColor, theLevel, resultList);
            }
            continue;
        }
    }

    private int countNodes(ListNode<QTreeNode> node) {
        int count = 0;
        while (node != null) {
            count++;
            node = node.getNext();
        }
        return count;
    }

    public QTreeNode findNode(QTreeNode root, int theLevel, int x, int y) {
        if (root == null || theLevel < 0) {
            return null; // Return null if root is null or level is invalid
        }

        // Check if the root node matches the conditions

        //issue is here. problem is parent = null always so getHeight(root) = 0 always
        int nodeHeight = getHeight(root);
        if (root.contains(x, y) && nodeHeight == theLevel) {
            return root;
        }

        // Recursively search for the node in the subtrees
        QTreeNode result = null;
        for (int i = 0; i < 4; i++) {
            if(!root.isLeaf()) {
                root.setChild(root.getChild(i), i);
                root.getChild(i).setParent(root);
                result = findNode(root.getChild(i), theLevel, x, y);

            }
            else{
                continue;
            }
            if (result != null) {
                return result; // Return the result if found in the subtree
            }
        }

        return null; // Return null if no matching node is found
    }
}












