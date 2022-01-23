public class BinaryTreeTestProgram {
    public static void main(String[] args) {

        BinaryTree first = generateTreeA();
        BinaryTree second = generateTreeA();
        BinaryTree third = generateTreeB();
        BinaryTree fourth = generateTreeC();
        BinaryTree fifth = generateTreeD();

        System.out.println("A  is same as A (should be true): " + first.sameAs(second));
        System.out.println("A  is same as B (should be false): " + first.sameAs(third));
        System.out.println("A  is same as C (should be false): " + first.sameAs(fourth));
        System.out.println("A  is same as D (should be true): " + first.sameAs(fifth));
    }

    public static BinaryTree generateTreeA(){
        BinaryTree 	root;

        root = new BinaryTree(1,
                new BinaryTree(2,
                        new BinaryTree(3,
                                new BinaryTree(4),
                                new BinaryTree(5,
                                        new BinaryTree(6,
                                                new BinaryTree(7),
                                                new BinaryTree(9)),
                                        new BinaryTree(8))),
                        new BinaryTree(10,
                                new BinaryTree(11,
                                        null,
                                        new BinaryTree(12,
                                                null,
                                                new BinaryTree(13))),
                                new BinaryTree(14,
                                        null,
                                        new BinaryTree(15)))),
                new BinaryTree(16,
                        new BinaryTree(17),
                        new BinaryTree(18,
                                new BinaryTree(20,
                                        new BinaryTree(19),
                                        null),
                                new BinaryTree(21))));
        return root;
    }

    public static BinaryTree generateTreeB(){
        BinaryTree 	root;

        root = new BinaryTree(1,
                new BinaryTree(2,
                        new BinaryTree(3,
                                new BinaryTree(4),
                                new BinaryTree(5,
                                        new BinaryTree(6,
                                                new BinaryTree(7),
                                                new BinaryTree(9)),
                                        new BinaryTree(8))),
                        new BinaryTree(10,
                                new BinaryTree(11,
                                        null,
                                        new BinaryTree(12,
                                                null,
                                                new BinaryTree(13))),
                                new BinaryTree(29,
                                        null,
                                        new BinaryTree(15)))),
                new BinaryTree(16,
                        new BinaryTree(17),
                        new BinaryTree(18,
                                new BinaryTree(20,
                                        new BinaryTree(19),
                                        null),
                                new BinaryTree(21))));
        return root;
    }

    public static BinaryTree generateTreeC(){
        BinaryTree 	root;

        root = new BinaryTree(1,
                new BinaryTree(2,
                        new BinaryTree(3,
                                new BinaryTree(4),
                                new BinaryTree(5,
                                        new BinaryTree(6,
                                                new BinaryTree(7),
                                                new BinaryTree(9)),
                                        new BinaryTree(8))),
                        new BinaryTree(10,
                                new BinaryTree(11,
                                        null,
                                        new BinaryTree(12,
                                                null,
                                                new BinaryTree(13))),
                                new BinaryTree(14,
                                        null,
                                        new BinaryTree(15)))),
                new BinaryTree(16,
                        new BinaryTree(17),
                        new BinaryTree(18,
                                new BinaryTree(20,
                                        null,
                                        new BinaryTree(19)),
                                new BinaryTree(21))));
        return root;
    }

    public static BinaryTree generateTreeD(){
        BinaryTree 	root;

        root = new BinaryTree(1,
                new BinaryTree(2,
                        new BinaryTree(3,
                                new BinaryTree(4),
                                new BinaryTree(5,
                                        new BinaryTree(6,
                                                new BinaryTree(7),
                                                new BinaryTree(9)),
                                        new BinaryTree(8))),
                        new BinaryTree(10,
                                new BinaryTree(11,
                                        null,
                                        new BinaryTree(12,
                                                null,
                                                new BinaryTree(13))),
                                new BinaryTree(14,
                                        null,
                                        new BinaryTree(15)))),
                new BinaryTree(16,
                        new BinaryTree(17),
                        new BinaryTree(18,
                                new BinaryTree(20,
                                        new BinaryTree(19),
                                        null),
                                new BinaryTree(21))));
        return root;
    }
}
