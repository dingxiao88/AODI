package AODI_GPRS;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.regex.Pattern;

//import org.worldbank.transport.tamt.shared.GPRMCRecord;
//import org.worldbank.transport.tamt.shared.TAMTPoint;

public class ParseGPRMC {

        static String FILENAME = "/home/tamt/eclipse/workspace/tamt/test/org/worldbank/transport/tamt/gps/small.DAT";
        static double minBearing = 180;
        static double maxBearing = 180;
        static Pattern pattern = Pattern.compile(",\\s*");
        static Integer utcOffset = 0;
        
        static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        static Calendar cal;
        private static TimeZone offsetTimeZone;
        private static TimeZone originalTimeZone;
        /**
         * @param args
         */
        public static void main(String[] args) {
                
                originalTimeZone = TimeZone.getDefault();
                offsetTimeZone = TimeZone.getTimeZone("GMT+0");
                cal = Calendar.getInstance();
                
                System.out.println("testing...");
                String line = null;
                String line2 = null;
                int numRecords = 0;
                
                try {
                        FileInputStream fin = new FileInputStream(FILENAME);
                        InputStreamReader in = new InputStreamReader(fin);
                        BufferedReader buff = new BufferedReader(in);
                        //Scanner scanner = new Scanner();
                        
                        long start = System.currentTimeMillis();
                        
                        
                        while((line = buff.readLine()) != null)
                        {
                                if( line.indexOf("$GPRMC") != -1)
                                {
                                        // this assumes that GPGGA is the only other captured sentence
                                        // and will not work if another sentence is recorded of if the
                                        // sentence sequence is NOT in the correct order (RMC, GGA)
                                        line2 = buff.readLine();
                                        processLineSet(line, line2);
                                        numRecords++;
                                }
                        }
                        
                        long end = System.currentTimeMillis();
                        long delta = end - start;
                        System.out.println("milliseconds=" + delta);
                        
                        System.out.println("number of records=" + numRecords);
                        //System.out.println("min bearing=" + minBearing);
                        //System.out.println("max bearing=" + maxBearing);
                        
                        
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                
                //showTimeZones();
        }
        
        public static void processLineSet(String gprmc, String gpgga)
        {
                //System.out.println("gprmc=" + gprmc);
                //System.out.println("gpgga" + gpgga);
                
                // set up new point
                TAMTPoint p = new TAMTPoint();
                parseGPRMC(gprmc, p);
                parseGPGGA(gpgga, p);
                
                //System.out.println(p);
                /*
                if( p.getBearing() < minBearing )
                {
                        minBearing = p.getBearing();
                }
                if( p.getBearing() > maxBearing )
                {
                        maxBearing = p.getBearing();
                }
                */
                
        }
        
        public static void parseGPGGA(String line, TAMTPoint p)
        {
                //String[] data = line.split(",\\s*");
                String[] data = pattern.split(line);
                
                //  $GPGGA,123519,4807.038,N,01131.000,E,1,08,0.9,545.4,M,46.9,M,,*47
                
                // altitude
                p.setAltitude(Double.parseDouble(data[9]));
                
                // altitude units
                p.setAltitudeUnits(data[10]);
                
        }
        
        public static void parseGPRMC(String line, TAMTPoint p)
        {
                //String[] data = line.split(",\\s*");
                String[] data = pattern.split(line);
                
                // timestamp from hhmmss, ddmmyy
                System.out.println("line=" + line);
                System.out.println("data[1]=" + data[1]);
                System.out.println("data[9]=" + data[9]);
                Date timestamp = parseDate(data[1], data[9], cal);
                System.out.println(dateFormat.format(timestamp)); // this outputs the UTC date in the offset time zone
                p.setTimestamp(dateFormat.format(timestamp));
                
                // latitude (ddmm.ss), latitude hemisphere (N or S)
                double latitude = parseCoord(data[3], data[4]);
                p.setLatitude(latitude);
                
                // longitude (ddmm.ss), longitude hemisphere (E or W)
                double longitude = parseCoord(data[5], data[6]);
                p.setLongitude(longitude);
                
                // bearing 
                double bearing = Double.parseDouble(data[8]);
                p.setBearing(bearing);
                
                // speed (in knots)
                double speed = Double.parseDouble(data[7]);
                p.setSpeed(speed);
                
        }
        
        public static double parseCoord(String ddmmss, String hemisphere)
        {
                double coord = 0;
                
                // degrees
                double degrees = Double.parseDouble(ddmmss.substring(0, 2));
                
                // minutes (with any leftover decimal seconds)
                double minutes = Double.parseDouble(ddmmss.substring(2, ddmmss.length()));
                
                // calc coord
                coord = degrees + ((minutes*60)/3600);
                
                // if hemisphere is W or S, then make coord negative
                if( hemisphere.equals("W") || hemisphere.equals("S"))
                {
                        // coord = Math.abs(coord) * -1;
                        coord = coord * -1;
                }
                
                return coord;
        }
        
        public static Date parseDate(String hhmmss, String ddmmyy, Calendar cal)
        {
                
                // first two chars of hhmmss are hours
                int hours = Integer.parseInt( hhmmss.substring(0, 2) );
                System.out.println("hours=" + hours);
                
                // next two chars of hhmmss are minutes
                int minutes = Integer.parseInt( hhmmss.substring(2, 4));
                System.out.println("minutes=" + minutes);
                
                // next two chars of hhmmss are seconds
                int seconds = Integer.parseInt( hhmmss.substring(4, 6) );
                System.out.println("seconds=" + seconds);
                
                // first two chars of ddmmyy are day
                int date = Integer.parseInt( ddmmyy.substring(0, 2) );
                System.out.println("minutes=" + minutes);
                
                // next two chars of ddmmyy are month
                int month = Integer.parseInt( ddmmyy.substring(2, 4) );
                System.out.println("month=" + month);
                
                // next two chars of ddmmyy are year
                int year = 100 + Integer.parseInt( ddmmyy.substring(4, 6)); // add 100 to 1900 to get into 21st century
                System.out.println("year=" + year);
                int year1900 = year + 1900;
                System.out.println("year+1900=" + year1900);
                
                // create date
                //Date d = createDate(year, month, date, hours, minutes, seconds);
                //return d;
                // cal.setTimeZone(offsetTimeZone);
                cal.set(year1900, month, date, hours, minutes, seconds);
                
                // change the zone, call for the date, change the zone back
                TimeZone.setDefault(offsetTimeZone);
                Date d = cal.getTime();
                TimeZone.setDefault(originalTimeZone);
                
                return d;
        }
        
        public static Date createDate(int year, 
                        int month, 
                        int date, 
                        int hours, 
                        int minutes, 
                        int seconds)
        {
                Date d = new Date(year, month, date, hours, minutes, seconds);
                
                // local date
                TimeZone local = TimeZone.getDefault();
                Calendar c1 = Calendar.getInstance(local);
                c1.set(year+1900, month, date, hours, minutes, seconds);
                System.out.println("c1=" + dateFormat.format(c1.getTime()));
                System.out.println("c1=" + c1.getTimeZone());
                
                // utc date
                TimeZone utc = TimeZone.getTimeZone("GMT+0");
                Calendar c2 = Calendar.getInstance(utc);
                c2.set(year+1900, month, date, hours, minutes, seconds);
                System.out.println("c2=" + dateFormat.format(c2.getTime()));
                System.out.println("c2=" + c2.getTimeZone());
                
                // comparison offset date to c1 without daylight saving
                TimeZone offset1 = TimeZone.getTimeZone("GMT-5");
                Calendar c3 = Calendar.getInstance(offset1);
                c3.set(year+1900, month, date, hours, minutes, seconds);
                System.out.println("c3=" + dateFormat.format(c3.getTime()));
                System.out.println("c3=" + c3.getTimeZone());
                
                // utc offset day
                String offsetString = "GMT" + Integer.toString(utcOffset);
                TimeZone offset2 = TimeZone.getTimeZone(offsetString);
                Calendar c4 = Calendar.getInstance(offset2);
                c4.set(year+1900, month, date, hours, minutes, seconds);
                System.out.println("c4=" + dateFormat.format(c4.getTime()));
                System.out.println("c4=" + c4.getTimeZone());
                
                return d;
        }
        
        public static void showTimeZones()
        {
                String[] tzs = TimeZone.getAvailableIDs();
                Arrays.sort(tzs);
                for (int i = 0; i < tzs.length; i++) {
                        System.out.println(tzs[i]);
                }
        }

}

