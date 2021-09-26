package com.datastructure.tree;

public class BinaryTreeDemo {
    public static void main(String[] args) {

        BinaryTree binaryTree = new BinaryTree();
        HeroNode root = new HeroNode(1, "song11");
        HeroNode node2 = new HeroNode(2, "song22");
        HeroNode node3 = new HeroNode(3, "song33");
        HeroNode node4 = new HeroNode(4, "song44");
        HeroNode node5 = new HeroNode(5, "song55");

        //说明，我们先手动创建该二叉树，后面我们学习递归的方式创建二叉树
        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);
        node3.setLeft(node5);

        binaryTree.setRoot(root);

        System.out.println("前序遍历--");
        binaryTree.preOrder();

//        System.out.println("中序遍历--");
//        binaryTree.infixOrder();
//
//        System.out.println("后序遍历--");
//        binaryTree.postOrder();

        //del node
        System.out.println("del node-----------");
        binaryTree.delNode(5);
        binaryTree.preOrder();



//        System.out.println("前序遍历查询---");
//        HeroNode resNode = binaryTree.preOrderSearch(6);
//        if (resNode != null) {
//            System.out.printf("find no=%d name=%s \n", resNode.getNo(), resNode.getName());
//        } else {
//            System.out.printf("did not find no = %d \n", 6);
//        }

//        System.out.println("中序遍历查询---");
//        HeroNode resNode1 = binaryTree.infixOrderSearch(5);
//        if (resNode1 != null) {
//            System.out.printf("find no=%d name=%s \n", resNode1.getNo(), resNode1.getName());
//        } else {
//            System.out.printf("did not find no = %d \n", 5);
//        }


//        System.out.println("后序遍历查询---");
//        HeroNode resNode2 = binaryTree.infixOrderSearch(5);
//        if (resNode2 != null) {
//            System.out.printf("find no=%d name=%s \n", resNode2.getNo(), resNode2.getName());
//        } else {
//            System.out.printf("did not find no = %d \n", 5);
//        }

    }
}

//create binary tree
class BinaryTree {
    private HeroNode root;

    public void setRoot(HeroNode root) {
        this.root = root;
    }


    public void delNode(int no) {
        if (root != null) {
            if (root.getNo() == no) {
                root = null;
            } else {
                root.delNode(no);
            }
        } else {
            System.out.println("binary tree is empty, can not delete");
        }
    }

    //前序遍历
    public void preOrder() {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("binary tree is empty");
        }
    }

    //中序遍历
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("binary tree is empty");
        }
    }

    //后序遍历
    public void postOrder() {
        if (root != null) {
            root.postOrder();
        } else {
            System.out.println("binary tree is empty");
        }
    }

    //前序遍历
    public HeroNode preOrderSearch(int no) {
        if (root != null) {
            return root.preOrderSearch(no);
        } else {
            return null;
        }
    }

    //中序遍历
    public HeroNode infixOrderSearch(int no) {
        if (root != null) {
            return root.infixOrderSearch(no);
        } else {
            return null;
        }
    }

    //后序遍历
    public HeroNode postOrderSearch(int no) {
        if (root != null) {
            return root.postOrderSearch(no);
        } else {
            return null;
        }
    }

}


//create node
class HeroNode {
    private int no;
    private String name;
    private HeroNode left;  //默认null
    private HeroNode right; //默认null

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    //del node
    public void delNode(int no) {
        if (this.left != null && this.left.no == no) {
            this.left = null;
            return;
        }

        if (this.right != null && this.right.no == no) {
            this.right = null;
            return;
        }

        if (this.left != null) {
            this.left.delNode(no);
        }

        if (this.right != null) {
            this.right.delNode(no);
        }

    }


    //编写前序遍历的方法
    public void preOrder() {
        System.out.println(this);  //先输出父节点
        //递归向左子树前序遍历
        if (this.left != null) {
            this.left.preOrder();
        }
        //递归向右子树前序遍历
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    //中序遍历
    public void infixOrder() {
        //递归向左子树中序遍历
        if (this.left != null) {
            this.left.infixOrder();
        }
        //输出父节点
        System.out.println(this);
        //递归向右子树中序遍历
        if (this.right != null) {
            this.right.infixOrder();
        }
    }


    //后序遍历
    public void postOrder() {
        if (this.left != null) {
            this.left.postOrder();
        }
        if (this.right != null) {
            this.right.postOrder();
        }
        System.out.println(this);
    }

    //前序遍历查找

    /**
     * @param no search no
     * @return return node if found, return null if not found
     */
    public HeroNode preOrderSearch(int no) {
        //compare
        if (this.no == no) {
            return this;
        }

        HeroNode resNode = null;

        if (this.left != null) {
            resNode = this.left.preOrderSearch(no);
        }

        if (resNode != null) { //find in left tree
            return resNode;
        }

        if (this.right != null) {
            resNode = this.right.preOrderSearch(no);
        }

        return resNode;

    }


    //中序遍历查找
    public HeroNode infixOrderSearch(int no) {
        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.infixOrderSearch(no);
        }

        if (resNode != null) {
            return resNode;
        }

        if (this.no == no) {
            return this;
        }

        if (this.right != null) {
            resNode = this.right.infixOrderSearch(no);
        }
        return resNode;

    }

    //后序遍历查找
    public HeroNode postOrderSearch(int no) {
        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.postOrderSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }
        if (this.right != null) {
            resNode = this.right.postOrderSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }

        if (this.no == no) {
            return this;
        }
        return resNode;

    }


}
