package com.datastructure.tree;

public class ThreadedBinaryTree {
    public static void main(String[] args) {
        //测试中序线索二叉树
        HeroNode2 root = new HeroNode2(1, "tom");
        HeroNode2 hero2 = new HeroNode2(3, "jack");
        HeroNode2 hero3 = new HeroNode2(6, "smith");
        HeroNode2 hero4 = new HeroNode2(8, "lily");
        HeroNode2 hero5 = new HeroNode2(10, "lucy");
        HeroNode2 hero6 = new HeroNode2(14, "morgan");

        //二叉树，手动创建
        root.setLeft(hero2);
        root.setRight(hero3);

        hero2.setLeft(hero4);
        hero2.setRight(hero5);

        hero3.setLeft(hero6);

        //测试线索化
        ThreadedBinaryTree2 threadedBinary = new ThreadedBinaryTree2();
        threadedBinary.setRoot(root);
        threadedBinary.threadedNodes();

        //测试： 10号节点
//        HeroNode2 leftNode5 = hero5.getLeft();
//        HeroNode2 rightNode5 = hero5.getRight();
//
//        System.out.println("left+++" + leftNode5);
//        System.out.println("right++++" + rightNode5);

        //当线索化二叉树后，不能再使用原来的遍历方法
        threadedBinary.threadedList();

    }
}


//define thread binary tree
class ThreadedBinaryTree2 {
    private HeroNode2 root;

    //为了实现线索化，需要创建要给指向当前节点的前驱节点的指针
    //
    private HeroNode2 pre = null;

    public void setRoot(HeroNode2 root) {
        this.root = root;
    }

    //重载threadnode
    public void threadedNodes() {
        this.threadedNodes(root);
    }


    //遍历线索化二叉树
    public void threadedList() {
        //定义一个变量，存储当前遍历的结点，从root开始
        HeroNode2 node = root;
        while (node != null) {
            //循环的找到leftType==1 的结点，第一个找到就是8结点
            //后面随着遍历而变化，因为当leftType==1,说明该结点是按照线索化处理后的有效结点
            while (node.getLeftType() == 0) {
                node = node.getLeft();
            }
            //打印当前这个结点
            System.out.println(node);
            //如果当前结点的右指针指向的是后继结点，就一直输出
            while (node.getRightType() == 1) {
                //获取到当前结点的后继结点
                node = node.getRight();
                System.out.println(node);
            }

            //替换这个遍历的结点

            node = node.getRight();

        }

    }


    //编写对二叉树进行中序线索化的方法

    /**
     * @param node 当前需要线索化的节点
     */
    public void threadedNodes(HeroNode2 node) {
        //如果node==null 不能线索化
        if (node == null) {
            return;
        }
        /*
         * 1. 先线索化左子树
         * 2. 线索化当前节点
         * 3. 再线索化右子树
         * */
        //先线索化左子树
        threadedNodes(node.getLeft());

        //处理当前节点的前驱节点
        //8节点的.left = null, 8 节点的 .leftType = 1
        if (node.getLeft() == null) {
            //让当前节点的左指针指向前驱节点
            node.setLeft(pre);
            //修改当前节点的左指针的类型，指向前驱节点
            node.setLeftType(1);

        }
        //处理后继节点
        if (pre != null && pre.getRight() == null) {
            //让前驱节点的右指针指向当前节点
            pre.setRight(node);
            //修改前驱节点的右指针类型
            node.setRightType(1);
        }
        //每处理一个结点后，让当前结点是下一个结点的前驱结点
        pre = node;

        //再线索化右子树
        threadedNodes(node.getRight());


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
    public HeroNode2 preOrderSearch(int no) {
        if (root != null) {
            return root.preOrderSearch(no);
        } else {
            return null;
        }
    }

    //中序遍历
    public HeroNode2 infixOrderSearch(int no) {
        if (root != null) {
            return root.infixOrderSearch(no);
        } else {
            return null;
        }
    }

    //后序遍历
    public HeroNode2 postOrderSearch(int no) {
        if (root != null) {
            return root.postOrderSearch(no);
        } else {
            return null;
        }
    }

}


//create HeroNode
class HeroNode2 {
    private int no;
    private String name;
    private HeroNode2 left;  //默认null
    private HeroNode2 right; //默认null

    /*
     * explanation
     * 1. leftType == 0  point to left tree  leftType==1  point to 前驱节点
     * 2. rightType == 0 表示指向右子树，如果1 表示指向后继节点
     * */

    private int leftType;
    private int rightType;

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    public HeroNode2(int no, String name) {
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

    public HeroNode2 getLeft() {
        return left;
    }

    public void setLeft(HeroNode2 left) {
        this.left = left;
    }

    public HeroNode2 getRight() {
        return right;
    }

    public void setRight(HeroNode2 right) {
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
    public HeroNode2 preOrderSearch(int no) {
        //compare
        if (this.no == no) {
            return this;
        }

        HeroNode2 resNode = null;

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
    public HeroNode2 infixOrderSearch(int no) {
        HeroNode2 resNode = null;
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
    public HeroNode2 postOrderSearch(int no) {
        HeroNode2 resNode = null;
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