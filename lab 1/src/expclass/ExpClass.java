package expclass;
public class ExpClass {
    public static double exp(float x, double eps)
    {
        boolean is_negative =false;
        if(x<0)
        {
            is_negative = true;
            x=-x;
        }
        if(x == 0)
            return 1;
        double result = 0, temp = 1;
        eps = Math.pow(10, -(eps+2));
        for(int count = 1; temp> eps; count++)
        {
            result += temp;
            temp*= x/count;
        }
        if(is_negative)
        {
            return 1/result;
        }
        return result;
    }
}
