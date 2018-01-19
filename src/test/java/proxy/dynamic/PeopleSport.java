package proxy.dynamic;

/**
 * Created by steven on 17/8/21.
 */
public class PeopleSport implements ISport{
    public PeopleSport() throws Exception {
        System.out.println("---- init PeopleSport");
    }
    public void run() throws Throwable {
        play();
        System.out.println("i'm running...");
//        throw new Exception("error 1");
//        int a=  1/0;
    }

    public void jump() throws Throwable {
//        int a=1/0;
        run();
        System.out.println("i'm jumping...");
    }

    public void play() throws Throwable{
        System.out.println("i'm playing...");
    }
}
