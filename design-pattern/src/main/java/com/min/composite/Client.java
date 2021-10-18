package com.min.composite;

public class Client {
    public static void main(String[] args) {
        OrganizationComponent university = new University("Tshinghua", "chinese university");

        OrganizationComponent CS = new College("Computer science", "计算机");
        OrganizationComponent infoTech = new College("Information technology", "信息技术");
        CS.add(new Department("Computer science1", "计算机1"));
        CS.add(new Department("Computer science2", "计算机2"));
        CS.add(new Department("Computer science3", "计算机3"));


        infoTech.add(new Department("info1", "信息工程1"));
        infoTech.add(new Department("info2", "信息工程2"));
        infoTech.add(new Department("info3", "信息工程3"));

        university.add(CS);
        university.add(infoTech);

        university.print();

    }
}
