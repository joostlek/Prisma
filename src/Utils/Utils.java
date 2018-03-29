package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {

    private Utils() {

    }

    //deze method is static onderdeel van PrIS omdat hij als hulp methode
    //in veel controllers gebruikt wordt
    //een standaardDatumString heeft formaat YYYY-MM-DD
    public static Calendar standaardDatumStringToCal(String pStadaardDatumString) {
        Calendar lCal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            lCal.setTime(sdf.parse(pStadaardDatumString));
        } catch (ParseException e) {
            e.printStackTrace();
            lCal = null;
        }

        return lCal;
    }

    //deze method is static onderdeel van PrIS omdat hij als hulp methode
    //in veel controllers gebruikt wordt
    //een standaardDatumString heeft formaat YYYY-MM-DD
    public static String calToStandaardDatumString(Calendar pCalendar) {
        int lJaar = pCalendar.get(Calendar.YEAR);
        int lMaand = pCalendar.get(Calendar.MONTH) + 1;
        int lDag = pCalendar.get(Calendar.DAY_OF_MONTH);

        String lMaandStr = Integer.toString(lMaand);
        if (lMaandStr.length() == 1) {
            lMaandStr = "0" + lMaandStr;
        }
        String lDagStr = Integer.toString(lDag);
        if (lDagStr.length() == 1) {
            lDagStr = "0" + lDagStr;
        }
        return Integer.toString(lJaar) + "-" + lMaandStr + "-" + lDagStr;
    }

}
