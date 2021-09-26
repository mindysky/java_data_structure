package com.datastructure.binarysorttree;

public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9};
        BinarySortTree binarySortTree = new BinarySortTree();
        //循环的添加节点到二叉排序树
        for (int j : arr) {
            binarySortTree.add(new Node(j));
        }
        System.out.println("show tree...");
        binarySortTree.infixOrder();

        System.out.println("show tree2...");
        binarySortTree.delNode(12);
        binarySortTree.infixOrder();

    }
}

//create binarySorttree
class BinarySortTree {
    private Node root;

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