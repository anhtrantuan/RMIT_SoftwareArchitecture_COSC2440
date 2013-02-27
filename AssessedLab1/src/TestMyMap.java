public class TestMyMap {
    public static void main(String args[]) {
//        MyMapArray<String, Double> m = new MyMapArray<String, Double>();
        MyMapBST<String, Double> m = new MyMapBST<String, Double>();
        m.put("Apples", 1.5);
        m.put("Apples", 2.8);
        m.put("Grapes", 2.5);
        m.put("Pears", 2.3);
        System.out.println("Price of Apples is = " + m.get("Apples"));
        m.print();
    }
}