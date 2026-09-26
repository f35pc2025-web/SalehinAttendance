package ir.salehin.attendance;

import java.util.*;

public final class PersianCalendar {
    private PersianCalendar() {}
    public static String today() { return toJalali(new Date()); }
    public static String toJalali(Date date) {
        Calendar c=Calendar.getInstance(); c.setTime(date);
        int gy=c.get(Calendar.YEAR), gm=c.get(Calendar.MONTH)+1, gd=c.get(Calendar.DAY_OF_MONTH);
        int[] r=convert(gy,gm,gd); return String.format(Locale.US,"%04d/%02d/%02d",r[0],r[1],r[2]);
    }
    public static Date toGregorian(String s) throws Exception {
        String[] p=s.trim().replace('-','/').split("/"); if(p.length!=3) throw new Exception("تاریخ نامعتبر است");
        int jy=Integer.parseInt(p[0]), jm=Integer.parseInt(p[1]), jd=Integer.parseInt(p[2]);
        int[] g=jalaliToGregorian(jy,jm,jd); Calendar c=Calendar.getInstance(); c.clear(); c.set(g[0],g[1]-1,g[2],12,0,0); return c.getTime();
    }
    private static int[] convert(int gy,int gm,int gd){ int gy2=gy-1600, gm2=gm-1, gd2=gd-1; int[] md={31,28,31,30,31,30,31,31,30,31,30,31};
        int gdn=365*gy2+(gy2+3)/4-(gy2+99)/100+(gy2+399)/400; for(int i=0;i<gm2;i++) gdn+=md[i]; if(gm2>1 && ((gy%4==0&&gy%100!=0)||gy%400==0)) gdn++; gdn+=gd2;
        int jdn=gdn-79, j_np=jdn/12053, jdn2=jdn%12053; int jy=979+33*j_np+4*(jdn2/1461); jdn2%=1461; if(jdn2>=366){jy+=(jdn2-1)/365;jdn2=(jdn2-1)%365;}
        int jm=(jdn2<186)?1+jdn2/31:7+(jdn2-186)/30, jd=1+((jdn2<186)?jdn2%31:(jdn2-186)%30); return new int[]{jy,jm,jd}; }
    private static int[] jalaliToGregorian(int jy,int jm,int jd){ int jy2=jy-979; int jdn=365*jy2+(jy2/33)*8+((jy2%33+3)/4); jdn+=(jm<7?(jm-1)*31:(jm-1)*30+6)+(jd-1)+79;
        int gy=1600+400*(jdn/146097); jdn%=146097; boolean leap=true; if(jdn>=36525){jdn--; gy+=100*(jdn/36524); jdn%=36524; if(jdn>=365) jdn++; else leap=false;} gy+=4*(jdn/1461); jdn%=1461; if(jdn>=366){leap=false;jdn--;gy+=jdn/365;jdn%=365;} int[] md={31,28,31,30,31,30,31,31,30,31,30,31}; int gm=1; while(gm<=12){int d=md[gm-1]+((gm==2&&leap)?1:0); if(jdn<d) break; jdn-=d;gm++;} return new int[]{gy,gm,jdn+1}; }
}
