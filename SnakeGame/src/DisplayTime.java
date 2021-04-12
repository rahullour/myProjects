public class DisplayTime implements Runnable{

    int sec=0;
    int stop=0;

    public void stop(int i)
    {
        sec=0;
        stop=i;



    }

    @Override
    public void run() {



        try
        {    sec++;
            System.out.println("me"+sec);
            Thread.sleep(1000);

                run();



        }



        catch (Exception e)
        {
            System.out.println("Exception Occured");
        }


    }
    public int gettime()
    {
        return sec;
    }
}