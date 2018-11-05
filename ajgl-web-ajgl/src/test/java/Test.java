import java.util.Calendar;

public class Test {

	public static void main(String[] args) {
		for(String str : getLast12Months()){
			System.out.println(str);
		}
	}

	public static String[] getLast12Months(){  
        String[] last12Months = new String[12];  
          
        Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)); //要先+1,才能把本月的算进去</span>  
        for(int i=0; i<12; i++){  
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1); //逐次往前推1个月  
            last12Months[11-i] = cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1);  
        }  
          
        return last12Months;  
    }  

}
