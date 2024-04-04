public class QTreeNode {
    private int x, y;   //coordinates of upper left corner

    private int size; //is the size of the quadrant. All quadrants are square; so, the
    //coordinates of the vertices of the quadrant represented by this object are
    //        (x,y), (x+size-1,y), (x,y+size-1), and (x+size-1,y+size-1).
    private int color; //is the average color of the pixels stored in the quadrant represented
    private QTreeNode parent; //is the parent of this QTreeNode object
    private QTreeNode[] children; //stores the children of this QTreeNode object. internal node has 4 children, leaf has none

    public QTreeNode(){
        parent = null;
        children = new QTreeNode[4];

        children[0] = null;
        children[1] = null;
        children[2] = null;
        children[3] = null;

        x = 0;
        y = 0;
        color = 0;
        size = 0;
    }

    public QTreeNode(QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor){
        children = theChildren;
        x = xcoord;
        y = ycoord;
        size = theSize;
        color = theColor;
    }

    public boolean contains(int xcoord, int ycoord){
        //within dimension using size
        if(xcoord >= x && xcoord <= x+size-1 && ycoord >= y && ycoord <= y+size-1){
            return true;
        }
        else{
            return false;
        }
    }

    public int getx(){
        return x;
    }

    public int gety(){
        return y;
    }

    public int getSize(){
        return size;
    }

    public int getColor(){
        return color;
    }

    public QTreeNode getParent(){
        return parent;
    }


    public QTreeNode getChild(int index) throws QTreeException{
        if(children == null || index < 0 || index > 3){
            throw new QTreeException("getChild method throws exception");
        }

        if(children[index] == null){
            return null;
        }



        else{
            return children[index];
        }
    }

    public void setx(int newx){
        x = newx;
    }

    public void sety(int newy){
        y = newy;
    }

    public void setSize(int newSize){
        size = newSize;
    }

    public void setColor(int newColor){
        color = newColor;
    }

    public void setParent(QTreeNode newParent){
        parent = newParent;
    }

    public void setChild(QTreeNode newChild, int index) throws QTreeException{
        if(newChild == null || index < 0 || index > 3){
            throw new QTreeException("setChild throws exception");
        }
        else{
            children[index] = newChild;
        }
    }
    
    public boolean isLeaf(){
        int numNull = 0;
        if(children == null){
            return true;
        }
        if(children.length == 4) {
            for (int i = 0; i < 4; i++) {
                if(children[i] == null){
                    numNull++;
                }
            }
        }

        if(numNull == 4){
            return true;
        }
        else{
            return false;
        }
    }


}
