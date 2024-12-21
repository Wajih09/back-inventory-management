 import java.util.Arrays;
 import java.util.List;
 import java.util.ArrayList;
// import static AutresClasses.ClassC.methodeStaticCalledInSamePackage;
 public class Test extends ab{
     private Test(){}
     private Test(int i){}
     boolean b;
     float f;
     char c;
     static String s;
     public static int stVar=100, intArrayStatic[]/*={-1}*/=new int[5];
     static Integer integerArrayStatic[]/*= new Integer[2]*/;
     static String[] stringArray = new String[3];
     static List<String> list = new ArrayList<>();
     private static boolean heatWave=true;
     public int var = 200;
    void printAll(){
        System.out.println("b="+b);
        System.out.println("f="+f);
        System.out.println("c="+c);
    }
    private void methodeNefaitRien(){return;}
    public static void main(String[] Args){
        System.out.println("\n\n\n\n");
        int x=Args.length, i=0,j, intArrayLocal[]={1};
        double p, b; 
        Integer h, integerArrayLocal[]={2};
        Integer ko[] = new Integer[2];
        boolean heatWave=false;
        h= 4;
        p=0.5;
        b=2;
        float o=(float)(p*b*h);
        String str1=new String(""), arrStr1[] = new String[2];
        str1=str1.concat("Hi");
        //str1=str1+"Hi";
        for (String s:arrStr1){
           // s=s.concat(""+i);
           // i++;
            System.out.println(s);
        }
        Test T = new Test();
        T.printAll();
        Test t1 = new Test();
        Test t2 = new Test();
        Test[] testArray = new Test[3];
        for (Test t:testArray){
             System.out.println(t);
         }
        B classb= new B();
        classb.methodeTest();
        t1.var=300;
        t2.stVar=300;
        String s1 = null;
        String s2 = "";
        String s3 = " ";
        StringBuilder s4 = new StringBuilder(" ");
        StringBuilder s5 = new StringBuilder(5);
        //String s11= s1.concat("first"); fausse !
        String s11= s1+"first";
        String s22= s2.concat("second");
        StringBuilder s43= s4.insert(s4.length(),"ok");
        //s3=s3.trim();
        //intArray[0]=1; fausse !
        //System.out.println("methodeNefaitRien"+T.methodeNefaitRien()); affichage methode ne returne rien => ne compile pas 
        //s.toString(); throws nullpointer car s est null et Arrays.toString(s) ne fonctionne pas avec les String, c dédié au array
        System.out.println("heatWave="+ heatWave);
        s11.toString(); // c pas affichage comme les array mais converstion to String si s11 n'est pas null bien sur
        System.out.println("str1="+str1);
        System.out.println("arrStr1= "+ Arrays.toString(arrStr1));
        System.out.println("ko= "+ ko);
        System.out.println("ko.toString= "+ Arrays.toString(ko));
        System.out.println("x="+x);
        System.out.println("o="+o);
        System.out.println("Args=" + Args);
        System.out.println("Args.toString()=" + Arrays.toString(Args)+Args.length);
        
        System.out.println("intArrayStatic="+intArrayStatic);
        System.out.println("intArrayStatic.toString()=" + Arrays.toString(intArrayStatic)/*+intArray.length*/);
        System.out.println("integerArrayStatic="+integerArrayStatic);
        System.out.println("integerArrayStatic.toString()=" + Arrays.toString(integerArrayStatic)/*+integerArray.length*/); 
        
        System.out.println("intArrayLocal="+intArrayLocal);
        System.out.println("intArrayLocal.toString()=" + Arrays.toString(intArrayLocal)/*+intArray.length*/);
        System.out.println("integerArrayLocal="+integerArrayLocal);
        System.out.println("integerArrayLocal.toString()=" + Arrays.toString(integerArrayLocal)/*+integerArray.length*/); 
        
        System.out.println("stringArray.toString()=" + Arrays.toString(stringArray)+stringArray.length);
        System.out.println("list="+list+list.size());
        System.out.println("t1.var=" + t1.var+"  t1.stVar=" + t2.stVar);
        System.out.println("t2.var=" + t2.var+"  t1.stVar=" + t2.stVar);
        System.out.println("s1="+s1);
        System.out.println("s2="+s2+s2.length());
        System.out.println("s3="+s3+s3.length());
        System.out.println("s4="+s4+s4.length());
        System.out.println("s5="+s5+s5.length());
        System.out.println("s11="+s11+s11.length());
        System.out.println("s22="+s22+s22.length());
        System.out.println("s43="+s43+s43.length());
        System.out.println("str vide equals str null ? "+s2.equals(s1));
        System.out.println("str vide equals strb vide ? "+s2.equals(s4));
        System.out.println("str vide equals strb.toString vide ? "+s2.equals(s4.toString()));
        System.out.println("strg.toString vide equals str null ? "+ (s4.toString()).equals(s1));

    }
}
class B {
    int x;
    private int doStuff(){
        int x= 100;
        return x;
    }
    void methodeTest(){
        x=doStuff(); // il faut toujours appeler une methode dans une autre methode et pas directement dans la classe !
       // methodeStaticCalledInSamePackage();
        System.out.println(x++); 
    }    
}

abstract class ab{
protected static String material = "gold";
//public shine(){};
}