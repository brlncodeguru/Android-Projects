package com.example.mohamed.hw06;

import android.util.Log;
import android.widget.Switch;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Mohamed on 10/16/2016.
 */
public class WeatherParser {

    static public class WeatherPullParser
    {
        static ArrayList<Weather> parseWeather (InputStream in) throws XmlPullParserException, IOException, ParseException {
            XmlPullParser parser= XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in,"UTF-8");
            Weather weather=null;
            ArrayList <Weather>list=new ArrayList<Weather>();
            int event=parser.getEventType();
            while (event!=XmlPullParser.END_DOCUMENT)
            {
                switch (event)
                {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("weatherdata")) {
                            break;
                        }

                        else if (parser.getName().equals("forecast")) {


                        } else if (parser.getName().equals("time")) {
                            weather = new Weather();
                            if (weather != null) {

                                weather.setTime(parser.getAttributeValue(null, "from"));
                            }


                        } else if (parser.getName().equals("temperature")) {
                            weather.setTemperature(parser.getAttributeValue(null,"value"));
                        } else if (parser.getName().equals("pressure")) {
                            weather.setPressure(parser.getAttributeValue(null,"value")+parser.getAttributeValue(null,"unit"));
                        } else if (parser.getName().equals("windSpeed")) {
                            weather.setWindSspeed(parser.getAttributeValue(null,"mps")+parser.getAttributeName(0));
                        } else if (parser.getName().equals("windDirection")) {
                            weather.setWindDirection(parser.getAttributeValue(null,"deg")+" "+parser.getAttributeValue(null,"code"));
                        } else if (parser.getName().equals("humidity")) {
                            weather.setHumidity(parser.getAttributeValue(null,"value")+parser.getAttributeValue(null,"unit"));
                        }
                        else if (parser.getName().equals("clouds")) {
                            weather.setCondition(parser.getAttributeValue(null,"value"));
                        }
                        else if (parser.getName().equals("symbol")) {
                            weather.setWeatherIcon(parser.getAttributeValue(null,"var"));
                        }


                        break;
                    default:
                        break;
                    case XmlPullParser.END_TAG:

                        if (parser.getName().equals("time")) {
                            list.add(weather);
                            //weather=null;

                        }
                        break;



                }

                event=parser.next();

            }
            Log.d("demo2",list.toString());
            return list;



        }

    }
}
