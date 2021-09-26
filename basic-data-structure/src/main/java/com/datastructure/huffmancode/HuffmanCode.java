package com.datastructure.huffmancode;

import java.io.*;
import java.util.*;

public class HuffmanCode {
    public static void main(String[] args) {
//        String str = "i like like like java do you like a java";
//        byte[] strBytes = str.getBytes();
//
//        byte[] huffmanCodesBytes = huffmanZip(strBytes);
//        System.out.println(Arrays.toString(huffmanCodesBytes));
//        System.out.println(huffmanCodesBytes.length);
//
//        byte[] sourceByte = decode(huffmanCodes, strBytes);
//        System.out.println("原来的字符串=" + new String(sourceByte));

//        String srcFile = "d://src.dmp";
//        String dstFile = "d://dst.zip";
//        zipFile(srcFile, dstFile);
//        System.out.println("压缩文件OK、、、");

        String zipFile = "d://dst.zip";
        String dstFile = "d://src2.bmp";
        unZipFile(zipFile,dstFile);
        System.out.println("unzip success....");

        //System.out.println(strBytes.length);  //40

        //  List<Node> nodes = getNodes(strBytes);
        //System.out.println(nodes);
        // Node huffmanTree = createHuffmanTree(nodes);

//        preOrder(huffmanTree);
//        System.out.println();

        //test if huffman code
        // Map<Byte,String> huffmanCodes  = getCodes(huffmanTree);
//        System.out.println("生成的赫夫曼编码表" + huffmanCodes);

        //   byte[] huffmanZip = zip(strBytes,huffmanCodes);
        //   System.out.println("huffmanCode = "+ Arrays.toString(huffmanZip));  //17

    }

    //编写一个方法，完成对压缩文件的解压

    /**
     * @param zipFile 准备解压的文件
     * @param dstFile 将文件解压到哪个路径
     */
    public static void unZipFile(String zipFile, String dstFile) {
        //定义文件输入流
        InputStream is = null;
        ObjectInputStream ois = null;
        OutputStream os = null;

        try {
            //创建文件输入流
            is = new FileInputStream(zipFile);
            //创建一个和is关联的对象输入流
            ois = new ObjectInputStream(is);
            //读取byte数组 huffmanBytes
            byte[] huffmanBytes = (byte[]) ois.readObject();
            //读取赫夫曼编码表
            Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();

            //解码
            byte[] bytes = decode(huffmanCodes,huffmanBytes);

            //将bytes数组写入目标文件
            os = new FileOutputStream(dstFile);
            os.write(bytes);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                os.close();
                ois.close();
                is.close();
            } catch (Exception e2) {
                System.out.println(e2.getMessage());
            }
        }
    }


    //编写一个方法将文件进行压缩

    /**
     * @param srcFile
     * @param dstFile
     */
    public static void zipFile(String srcFile, String dstFile) {
        //创建文件的输出流
        OutputStream os = null;

        ObjectOutputStream oos = null;
        //创建文件输出流
        FileInputStream is = null;
        try {
            //创建文件的输入流
            is = new FileInputStream(srcFile);
            //创建一个和源文件大小一样的byte[]
            byte[] b = new byte[is.available()];
            //读取文件
            is.read(b);
            //直接对源文件压缩
            byte[] huffmanBytes = huffmanZip(b);
            //创建文件的输出流，存放压缩文件
            os = new FileOutputStream(dstFile);
            //创建一个和文件输出流关联的ObjectOutputStream
            oos = new ObjectOutputStream(os);
            //把赫夫曼编码后的字节数组写入压缩文件
            oos.writeObject(huffmanBytes);
            //这里我们以对象流的方式写入 赫夫曼编码，是为了以后我们恢复源文件时使用
            //注意一定要把赫夫曼编码写入压缩文件
            oos.writeObject(huffmanCodes);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                is.close();
                oos.close();
                os.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }


    //编写一个方法，完成对压缩数据的解码

    /**
     * @param huffmanCodes 赫夫曼编码表map
     * @param huffmanBytes        赫夫曼编码得到的字节数组
     * @return
     */
    private static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
        //1.先得到bytes 对应的 二进制字符串 101010001011...
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < huffmanBytes.length; i++) {
            byte b = huffmanBytes[i];
            boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(!flag, b));
        }

        //把字符串安装指定的赫夫曼编码进行解码
        //把赫夫曼编码表进行调换，因为需要反向查询 a -> 100
        Map<String, Byte> map = new HashMap<String, Byte>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }

        //创建一个集合，存放byte
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < stringBuilder.length(); ) {
            int count = 1;  //小的计数器
            boolean flag = true;
            Byte b = null;

            while (flag) {
                //递增的取出key 1
                String key = stringBuilder.substring(i, i+count);  //i不动， 让count移动
                b = map.get(key);
                if (b == null) {
                    count++;
                } else {
                    flag = false;
                }
            }
            list.add(b);
            i += count;  //i直接移动到count
        }
        //当for 循环结束后，我们list中就存放了所有的字符
        byte b[] = new byte[list.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = list.get(i);
        }
        return b;
    }


    //完成数据的解压
    /*
     * 1. 将huffmanCodesBytes  [-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24, -14, -117, -4, -60, -90, 28]
     * 重新转成赫夫曼编码对应的二进制字符串
     * 2. 赫夫曼编码对应的二进制 转为 赫夫曼编码 字符串
     * */


    /**
     * 参考二进制，原码，反码，补码
     *
     * @param flag 标识是否需要补高位，如果是true表示需要补高位, 如果是false表示不补，如果是最后一个字节，无需补高位
     * @param b    传入byte
     * @return 是该b 对应的二进制的字符串， 按部门返回
     */
    private static String byteToBitString(boolean flag, byte b) {
        int temp = b;
        //如果是正数，我们还存在补高位
        if (flag) {
            temp |= 256;// 按位与256
        }

        String str = Integer.toBinaryString(temp);  //返回的是二进制的补码
        if (flag) {
            return str.substring(str.length() - 8);
        } else {
            return str;
        }

    }


    //使用一个方法，将前面的方法封装起来，便于我们的调用

    /**
     * @param bytes 原始的字符串对应的字节数组
     * @return 经过赫夫曼编码处理过后的字节数组（压缩后的数组）
     */
    private static byte[] huffmanZip(byte[] bytes) {
        //得到节点
        List<Node> nodes = getNodes(bytes);
        //创建赫夫曼树
        Node huffmanTree = createHuffmanTree(nodes);
        //对应赫夫曼编码（根据赫夫曼树）
        Map<Byte, String> huffmanCodes = getCodes(huffmanTree);
        //根据生成的赫夫曼编码
        byte[] huffmanZip = zip(bytes, huffmanCodes);

        return huffmanZip;
    }


    //编写一个方法，将字符串对应的byte[] 数组，通过生成的赫夫曼编码表，返回一个赫夫曼编码压缩后的byte[]

    /**
     * @param bytes        这是原始的字符串对应的byte[]
     * @param huffmanCodes 生成的赫夫曼编码map
     * @return 返回赫夫曼编码处理后的byte[]
     * eg: String content = "i like like" -> byte[] contentbyte = content.getByte
     */
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        //利用huffmanCodes 将bytes 转成 赫夫曼编码对应的字符串
        StringBuilder stringBuilder = new StringBuilder();
        //遍历bytes 数组
        for (byte b : bytes) {
            stringBuilder.append(huffmanCodes.get(b));
        }

        System.out.println("test huffman stirngBuilder" + stringBuilder);

        //将 “10101000....” 转成 byte[]

        //统计返回的byte[]  huffmanCodeBytes 长度
        int len;
        if (stringBuilder.length() % 8 == 0) {
            len = stringBuilder.length() / 8;
        } else {
            len = stringBuilder.length() / 8 + 1;
        }

        //创建 存储压缩后的byte数组
        byte[] huffmanCodeBytes = new byte[len];
        int index = 0;
        for (int i = 0; i < stringBuilder.length(); i += 8) {
            //因为每8位对应一个byte, 所以步长 +8
            String strByte;
            if (i + 8 > stringBuilder.length()) {
                //不够8位
                strByte = stringBuilder.substring(i);
            } else {
                strByte = stringBuilder.substring(i, i + 8);
            }

            //将strByte转成byte 放入huffmanCodeBytes
            huffmanCodeBytes[index] = (byte) Integer.parseInt(strByte, 2);
            index++;

        }

        return huffmanCodeBytes;
    }


    //重载getCodes
    private static Map<Byte, String> getCodes(Node root) {
        if (root == null) {
            return null;
        }
        getCodes(root.left, "0", stringBuilder);
        getCodes(root.right, "1", stringBuilder);

        return huffmanCodes;
    }

    //生成赫夫曼树对应的赫夫曼编码
    /*
     * 1. 将赫夫曼编码表放在Map<Byte,String>形式
     * 2. 在生成赫夫曼编码表示，需要去拼接路径，定义一个StringBuilder 存储某个叶子结点的路径
     * */

    static Map<Byte, String> huffmanCodes = new HashMap<Byte, String>();

    static StringBuilder stringBuilder = new StringBuilder();


    /**
     * feature:  将传入的node节点的所有叶子结点的赫夫曼编码得到，存放到huffmanCodes集合
     *
     * @param node          传入节点
     * @param code          路径： 左子节点0， 右子节点1
     * @param stringBuilder 用于拼接路径
     */
    private static void getCodes(Node node, String code, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        //将code加入到stringBuilder2
        stringBuilder2.append(code);
        if (node != null) { //如果node == null 不处理
            //判断当前node是叶子结点还是非叶子结点
            if (node.data == null) { //非叶子结点
                getCodes(node.left, "0", stringBuilder2);
                getCodes(node.right, "1", stringBuilder2);
            } else {
                huffmanCodes.put(node.data, stringBuilder2.toString());
            }
        }

    }


    //前序遍历
    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("tree empty");
        }

    }

    /**
     * @param bytes 接收一个字节数组
     * @return 返回的是list [Node[data=97,weight=5],Node[data=32,weight=9].....]
     */
    @org.jetbrains.annotations.NotNull
    private static List<Node> getNodes(byte[] bytes) {
        //创建ArrayList
        ArrayList<Node> nodes = new ArrayList<>();

        HashMap<Byte, Integer> counts = new HashMap<>();
        //遍历
        for (byte b : bytes) {
            Integer count = counts.get(b);
            if (count == null) { //map还没有这个字符数据，第一次
                counts.put(b, 1);
            } else {
                counts.put(b, count + 1);
            }
        }
        //把每个键值对转成一个Node对象，并加入到nodes集合中
        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));

        }
        return nodes;

    }

    private static Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            //从小到大
            Collections.sort(nodes);
            //取出第一个棵最小的二叉树
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            Node parent = new Node(null, leftNode.weight + rightNode.weight);
            parent.left = leftNode;
            parent.right = rightNode;

            nodes.remove(leftNode);
            nodes.remove(rightNode);

            nodes.add(parent);

        }
        return nodes.get(0);
    }

}

//create node
class Node implements Comparable<Node> {
    Byte data;  //存放数据（字符）本身 如 'a' -> 97  ' ' -> 32
    int weight;  //权值
    Node left;
    Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }


    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }

    }


    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        //从小到大排列
        return this.weight - o.weight;
    }
}
