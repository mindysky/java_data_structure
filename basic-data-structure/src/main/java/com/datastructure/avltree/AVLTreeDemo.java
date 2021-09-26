package com.datastructure.avltree;

public class AVLTreeDemo {
    public static void main(String[] args) {
        int[] arr = {4, 3, 6, 5, 7, 8};
        AVLTree avlTree = new AVLTree();
        for (int i : arr) {
            avlTree.add(new Node(i));
        }

        System.out.println("show tree....");
        avlTree.infixOrder();

        System.out.println("没有做平常处理前---");
        System.out.println("tree height" + avlTree.getRoot().height());
        System.out.println("tree left height" + avlTree.getRoot().leftHeight());
        System.out.println("tree right height" + avlTree.getRoot().rightHeight());

    }
}

class AVLTree {
    private Node root;

    public Node getRoot() {
        return root;
    }

    /**
     * @param value expected node
     * @return if find return the node, or return null;
     */
    public Node search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    public Node searchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    /**
     * @param node 传入的结点，当做二叉排序树的根节点
     * @return 返回以node为根节点的二叉排序树的最小节点
     */
    public int delRightTreeMin(Node node) {
        Node target = node;
        while (target.left != null) {
            target = target.left;
        }
        //这时target就指向了最小节点
        //删除最小节点
        delNode(target.value);
        return target.value;
    }

    public void delNode(int value) {
        if (root == null) {
            return;
        } else {
            //先找到要删除的结点
            Node targetNode = search(value);
            if (targetNode == null) {
                return;
            }
            //如果我们发现当前这棵二叉排序树只有最后一个节点
            if (root.left == null && root.right == null) {
                root = null;
                return;
            }

            //去找到targetNode的父节点
            Node parent = searchParent(value);
            //如果要删除的结点是叶子结点
            if (targetNode.left == null && targetNode.right == null) {
                if (parent.left != null && parent.left.value == value) {  //左子节点
                    parent.left = null;
                } else if (parent.right != null && parent.right.value == value) { //右子节点
                    parent.right = null;
                }
            } else if (targetNode.left != null && targetNode.right != null) {
                //删除有两棵子树的结点
                int minVal = delRightTreeMin(targetNode.right);
                targetNode.value = minVal;
            } else {
                //删除只有一棵子树的结点
                if (targetNode.left != null) {

                    if (parent != null) {
                        //如果targetNode是parent的左子节点
                        if (parent.left.value == value) {
                            parent.left = targetNode.left;
                        } else {
                            parent.right = targetNode.left;
                        }
                    } else {
                        root = targetNode.left;
                    }

                } else {
                    if (parent != null) {
                        //如果targetNode是parent的右子节点
                        if (parent.left.value == value) {
                            parent.left = targetNode.right;
                        } else {
                            parent.right = targetNode.right;
                        }
                    } else {
                        root = targetNode.right;
                    }

                }
            }
        }

    }


    //method to add Node
    public void add(Node node) {
        if (root == null) {
            //如果root为空则直接让root指向node
            root = node;
        } else {
            root.add(node);
        }
    }

    //中序遍历
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("tree is empty....");
        }
    }

}


//create Node
class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    //返回左子树的高度
    public int leftHeight() {
        if (left == null) {
            return 0;
        }
        return left.height();
    }

    public int rightHeight() {
        if (right == null) {
            return 0;
        }
        return right.height();
    }

    //返回当前节点的高度， 以该节点为根节点的树的高度
    public int height() {
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    //左旋转方法
    public void leftRotate() {
        Node newNode = new Node(value);
        //把新的结点的左子树设置成当前节点的左子树
        newNode.left = left;
        //把新的结点的右子树设置成过去节点的右子树的左子树
        newNode.right = right.left;
        //把当前节点的值替换成右子节点的值
        value = right.value;
        //把当前节点的右子树设置成当前节点右子树的右子树
        right = right.right;
        //把当前节点的左子树（左子节点） 设置成新的节点
        left = newNode;
    }

    //左旋转方法
    public void rightRotate() {
        Node newNode = new Node(value);
        newNode.right = right;
        newNode.left = left.right;
        value = left.value;
        left = left.left;
        right = newNode;
    }


    /**
     * @param value expected node
     * @return if find return the node, or return null;
     */
    public Node search(int value) {
        if (value == this.value) {  //找到该节点
            return this;
        } else if (value < this.value) {  //如果查找的值小于当前节点，向左子树递归查找
            if (this.left == null) {
                return null;
            }
            return this.left.search(value);
        } else {
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);
        }
    }

    //search parent Node

    /**
     * @param value
     * @return
     */
    public Node searchParent(int value) {
        if ((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)) {
            return this;
        } else {
            //如果查找的值小于当前节点的值，并且当前节点的左子节点不为空
            if (value < this.value && this.left != null) {
                return this.left.searchParent(value); //向左子树递归查找
            } else if (value >= this.value && this.right != null) {
                return this.right.searchParent(value); //向右子树递归查找
            } else {
                return null;  //没有找到父节点
            }
        }
    }


    //添加节点
    public void add(Node node) {
        if (node == null) {
            return;
        }

        //判断传入的结点，和当前子树的根节点的值的关系
        if (node.value < this.value) {
            if (this.left == null) {
                this.left = node;
            } else {
                //递归的向左子树添加节点
                this.left.add(node);
            }
        } else {
            //添加的结点的值大于当前节点的值
            if (this.right == null) {
                this.right = node;
            } else {
                this.right.add(node);
            }
        }

        //当添加完一个节点后，如果右子树的高度比左子树的高度大于1 ，  rightHeigh-leftHeight >1
        if (rightHeight() - leftHeight() > 1) {
            if (right != null && right.rightHeight() < right.leftHeight()) {
                //先对右子树进行旋转
                right.rightRotate();
                leftRotate();
            }else{
                leftRotate();
            }
        }
        //当添加完一个节点后，如果右子树的高度比左子树的高度大于1 ，  rightHeigh-leftHeight >1
        if (rightHeight() - leftHeight() > 1) {
            if (left != null && left.rightHeight() < left.leftHeight()) {
                //先对当前节点的左子树进行左旋转
                left.leftRotate();
                rightRotate();
            }else {
                rightRotate();
            }
        }
    }

    //中序遍历
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);

        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}